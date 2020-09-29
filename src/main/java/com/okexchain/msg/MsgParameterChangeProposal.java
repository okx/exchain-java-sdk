package com.okexchain.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgParamChangeValue;
import com.okexchain.msg.types.MsgParameterChangeProposalValue;
import com.okexchain.msg.types.MsgParameterChangeProposalWrapperValue;
import com.okexchain.msg.types.MsgSubmitProposalValue;
import com.okexchain.msg.common.Token;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MsgParameterChangeProposal extends MsgBase {
    public MsgParameterChangeProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public static void main(String[] args) throws JsonProcessingException {
        MsgParameterChangeProposal msg = new MsgParameterChangeProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

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

        msg.submit(messages, "0.01000000", "200000", "OKExChain change parameter proposal!");
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

        // proposal wrapper
        MsgParameterChangeProposalWrapperValue wrapper = new MsgParameterChangeProposalWrapperValue();
        wrapper.setProposal(proposal);
        wrapper.setHeight(height);

        Message<MsgParameterChangeProposalWrapperValue> wrapperMsg = new Message<>();
        wrapperMsg.setType("okexchain/params/ParameterChangeProposal");
        wrapperMsg.setValue(wrapper);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom("okt");
        deposit.setAmount(amountDeposit);
        depositList.add(deposit);

        MsgSubmitProposalValue value = new MsgSubmitProposalValue();
        value.setContent(wrapperMsg);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
