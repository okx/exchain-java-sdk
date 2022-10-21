package com.feesplit;


import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.feesplit.MsgFeeSplitSharesProposal;
import com.okexchain.msg.gov.feesplit.MsgFeeSplitSharesProposalValue;
import com.okexchain.msg.gov.feesplit.Share;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MsgFeeSplitSharesProposalTest {

    //test reslut：https://www.oklink.com/zh-cn/okc-test/tx/C56B4232DFE2188016BCBCC554DF0D690D712062EC912E31A6A9A787F9139A46
    @Test
    public void testMsgClientUpdateProposal(){
        EnvInstance.getEnvTestNet();
        MsgFeeSplitSharesProposal msg=new MsgFeeSplitSharesProposal();
        msg.initMnemonic("");

        MsgFeeSplitSharesProposalValue value=new MsgFeeSplitSharesProposalValue();
        Share share=new Share();

        share.setShare(0.2);
        share.setContractAddr("0xfB4d72C1e96A2eF456C2cEDD2b10ecb20c52F2B2");
        List<Share> shareList=new ArrayList<>();
        shareList.add(share);

        value.setTitle("test");
        value.setDescription("test");
        value.setShareList(shareList);

        Message message=msg.produceMsgFeeSplitSharesProposal(value,"okt", "10.000000000000000000");
        JSONObject res = msg.submit(message, "0.03", "20000000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
