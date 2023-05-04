package com.okbchain.test;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Treasures;
import com.okbchain.msg.gov.MsgContractManageTreasuresProposal;
import com.okbchain.utils.crypto.PrivateKey;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MsgContractManageTreasuresProposalTest {

    @Test
    public void testMsgContractManageTreasuresProposal(){
        EnvInstance.getEnvTestNet();
        MsgContractManageTreasuresProposal msg=new MsgContractManageTreasuresProposal();
        msg.init(new PrivateKey(""));
        List<Treasures> treasuresList=new ArrayList<>();
        Treasures treasures=new Treasures();
        treasures.setAddress("ex1au3yf6kahc2p67sxfeuscsqwhrdtcjglxxhnyy");
        treasures.setProportion("0.050000000000000000");
        treasuresList.add(treasures);
        Message messages=msg.produceContractManageTreasuresProposal(
                "title",
                "des",
                treasuresList,
                true,
                "okt",
                "0.050000000000000000"
        );

        JSONObject res = msg.submit(messages, "0.03", "2000000", "");
        System.out.println(res.toJSONString());
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
