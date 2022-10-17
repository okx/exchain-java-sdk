package com.okc.staking;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.MsgWithdrawRewardEnabledProposal;
import com.okexchain.msg.gov.MsgWithdrawRewardEnabledProposalValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgWithdrawRewardEnabledProposalTest {
    //https://www.oklink.com/zh-cn/okc-test/tx/0x0AE7BBA7FB7DDE6739C752DA84A4EB8D04A37CB5483DB17B5FD54D2A78B74E82 setEnabled=true
    //https://www.oklink.com/zh-cn/okc-test/tx/0x5DA946483FA05029EF0B3512BC98C20FA077C1A30BF73AAD1D7B874DCC326451 setEnabled=false
    @Test
    public void testWithdrawRewardEnabledProposal(){
        MsgWithdrawRewardEnabledProposal msg=new MsgWithdrawRewardEnabledProposal();
        EnvInstance.getEnvTestNet();
        msg.initMnemonic("");

        MsgWithdrawRewardEnabledProposalValue proposalValue=new MsgWithdrawRewardEnabledProposalValue();
        proposalValue.setTitle("test WithdrawRewardEnabledProposal");
        proposalValue.setDescription("desc");
        proposalValue.setEnabled(false);


        Message message=msg.produceMsgWithdrawRewardEnabledProposal(proposalValue,"okt","10.000000000000000000");
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
