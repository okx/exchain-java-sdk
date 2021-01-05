package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.tx.Response;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MsgParameterChangeProposal extends MsgBase {
    public MsgParameterChangeProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public static void main(String[] args) throws Exception {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        System.out.println(new Response().getRawLog());

        MsgParameterChangeProposal msg = new MsgParameterChangeProposal();
        msg.initMnemonic("giggle sibling fun arrow elevator spoon blood grocery laugh tortoise culture tool");

        Message messages = msg.produceParameterChangeProposalMsg(
                "param change of mint deflation_rate",
                "change deflation_rate from 0.5 to 0.6",
                "staking",
                "MaxValidators",
                "",
                "121",
                "6000",
                "100.00000000"
        );

        JSONObject result = msg.submit(messages, "0.05000000", "500000", "OKExChain change parameter proposal!");
        boolean succeed = msg.isTxSucceed(result);
        System.out.println("submit proposal: " + (succeed ? "succeed" : "failed"));
    }

//    @SneakyThrows
    public Message produceParameterChangeProposalMsg(
            String title,
            String description,
            String subspace,
            String key,
            String subKey,
            String toValue,
            String height,
            String amountDeposit
    ) throws JsonProcessingException {

        // change
        JSONArray changesList = new JSONArray();

        MsgParamChangeValue change = new MsgParamChangeValue();
        change.setKey(key);
        change.setSubKey(subKey);
        change.setSubspace(subspace);
        change.setValue(toValue);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String res_value = mapper.writeValueAsString(change);
        LinkedHashMap<String, Object> jsonMap = JSON.parseObject(res_value, LinkedHashMap.class, Feature.OrderedField);
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.putAll(jsonMap);

        changesList.add(jsonObject);

        // proposal
        MsgParameterChangeProposalValue proposal = new MsgParameterChangeProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setChanges(changesList);

        // proposal wrappedProposal
        MsgParameterChangeProposalWrapperValue wrappedProposal = new MsgParameterChangeProposalWrapperValue();
        wrappedProposal.setProposal(proposal);
        wrappedProposal.setHeight(height);

        Content<MsgParameterChangeProposalWrapperValue> content = new Content<>();
        content.setType("okexchain/params/ParameterChangeProposal");
        content.setValue(wrappedProposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(EnvInstance.getEnv().GetDenom());
        deposit.setAmount(Utils.NewDecString(amountDeposit));
        depositList.add(deposit);

        MsgSubmitProposalValue<Content<MsgParameterChangeProposalWrapperValue>> value = new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgParameterChangeProposalWrapperValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
