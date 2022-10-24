package com.okc.staking;


import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.MsgRewardTruncatePrecisionProposal;
import com.okexchain.msg.gov.MsgRewardTruncatePrecisionProposalValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgRewardTruncatePrecisionProposalTest {

    //https://www.oklink.com/zh-cn/okc-test/tx/0x4B4FED343AE0106DD79AFFC7B47FB567F86423CB3D758801C4650709043C1923
    @Test
    public void testMsgRewardTruncatePrecisionProposal(){

        MsgRewardTruncatePrecisionProposal msg=new MsgRewardTruncatePrecisionProposal();
        EnvInstance.getEnvTestNet();
        msg.initMnemonic("");
        Message message=msg.produceMsgRewardTruncatePrecisionProposal("test RewardTruncatePrecisionProposal","desc",1,"okt","10.000000000000000000");
        JSONObject res = msg.submit(message, "0.03", "20000000", "");
        System.out.println(res.toJSONString());
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
