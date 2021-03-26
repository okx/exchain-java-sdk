package com.okexchain.msg;

/**
 * @author shaoyun.zhan
 * @date 2021/3/26
 * <p>
 * 描述：
 */
public class TestGov {

//    @Test
//    public void testContractBlockedListProposal() throws IOException {
//        EnvBase env = EnvInstance.getEnv();
//        env.setChainID("okexchainevm-8");
//        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
//        env.setDenom("okt");
//
////        {"codespace":"sdk","code":4,"gas_used":"67065","gas_wanted":"2000000","raw_log":"unauthorized: signature verification failed; verify correct account sequence and chain-id, sign msg:{\"account_number\":\"2\",\"chain_id\":\"okexchainevm-8\",\"fee\":{\"amount\":[{\"amount\":\"0.030000000000000000\",\"denom\":\"okt\"}],\"gas\":\"2000000\"},\"memo\":\"\",\"msgs\":[{\"type\":\"okexchain/gov/MsgSubmitProposal\",\"value\":{\"content\":{\"type\":\"okexchain/evm/ManageContractBlockedListProposal\",\"value\":{\"contract_addresses\":[\"okexchain1hw4r48aww06ldrfeuq2v438ujnl6alsz0685a0\",\"okexchain1qj5c07sm6jetjz8f509qtrxgh4psxkv32x0qas\"],\"description\":\"String description\",\"is_added\":true,\"title\":\"String title\"}},\"initial_deposit\":[{\"amount\":\"100.000000000000000000\",\"denom\":\"okt\"}],\"proposer\":\"okexchain1qpel9c5wlrc30efaskqfgzrda7h3sd745rcxeh\"}}],\"sequence\":\"9\"}","height":"0","txhash":"8240A3B7734DEB2878BD629CEB2426E04E1D4E96C3A3E7E87CB1933DD2FD0A49"}
//
//
//        MsgContractBlockedListProposal msg = new MsgContractBlockedListProposal();
//        msg.init(new PrivateKey("75dee45fc7b2dd69ec22dc6a825a2d982aee4ca2edd42c53ced0912173c4a788".toUpperCase()));
//
//        String[] contractAddresses = new String[]{"okexchain1hw4r48aww06ldrfeuq2v438ujnl6alsz0685a0","okexchain1qj5c07sm6jetjz8f509qtrxgh4psxkv32x0qas"};
//
//        Message messages = msg.produceContractDeploymentWhitelistProposal(
//                "String title",
//                "String description",
//                contractAddresses,
//                true,
//                "100.000000000000000000"
//        );
//
//        JSONObject res = msg.submit(messages, "0.03", "2000000", "");
//        System.out.println(res.toJSONString());
//        try {
//            boolean succeed = msg.isTxSucceed(res);
//            System.out.println("tx " + (succeed ? "succeed": "failed"));
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }
//
//    @Test
//    public void testContractBlockedListProposalValue() throws IOException {
//        EnvBase env = EnvInstance.getEnv();
//        env.setChainID("okexchainevm-8");
//        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
//        env.setDenom("okt");
//
//
//        MsgContractDeploymentWhitelistProposal msg = new MsgContractDeploymentWhitelistProposal();
//        msg.init(new PrivateKey("75dee45fc7b2dd69ec22dc6a825a2d982aee4ca2edd42c53ced0912173c4a788".toUpperCase()));
//
//        String[] distributorAddresses = new String[]{"okexchain1hw4r48aww06ldrfeuq2v438ujnl6alsz0685a0","okexchain1qj5c07sm6jetjz8f509qtrxgh4psxkv32x0qas"};
//
//        Message messages = msg.produceContractDeploymentWhitelistProposal(
//                "String title",
//                "String description",
//                distributorAddresses,
//                true,
//                "100.000000000000000000"
//        );
//
//        JSONObject res = msg.submit(messages, "0.03", "2000000", "");
//        System.out.println(res.toJSONString());
//        try {
//            boolean succeed = msg.isTxSucceed(res);
//            System.out.println("tx " + (succeed ? "succeed": "failed"));
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }

}
