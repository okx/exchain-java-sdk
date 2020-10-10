package com.okexchain.sample;

import com.okexchain.msg.MsgCreateValidator;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;

public class Gentxes {

    public static void gentx(String[] params) {

        MsgCreateValidator msg = new MsgCreateValidator();
        msg.init(params[0],"0","0");

        Message messages = msg.produceMsg(
                params[1], params[2],
                params[3], params[4],
                params[5], "10000.00000000");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"", "200000", "");
            System.out.println("======= unsignedTx json =========");
            System.out.println(unsignedTx.toString());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }


    public static void main(String[] args) {

        String[][] validators = {
                {"03d9d7b471f537aaeeeadfbebf863f0a1e4c518fd4f5954b189bfb6079c52881f1",
                        "okexchainvalconspub1zcjduepqtv2yy90ptjegdm34vfhlq2uw9eu39hjrt98sffj7yghl4s47xv7svt56mk",
                        "moniker",  "website", "identity", "details",},

                {"03d9d7b471f537aaeeeadfbebf863f0a1e4c518fd4f5954b189bfb6079c52881f1",
                        "okexchainvalconspub1zcjduepqtv2yy90ptjegdm34vfhlq2uw9eu39hjrt98sffj7yghl4s47xv7svt56mk",
                        "moniker",  "website", "identity", "details",},

                {"03d9d7b471f537aaeeeadfbebf863f0a1e4c518fd4f5954b189bfb6079c52881f1",
                        "okexchainvalconspub1zcjduepqtv2yy90ptjegdm34vfhlq2uw9eu39hjrt98sffj7yghl4s47xv7svt56mk",
                        "moniker",  "website", "identity", "details",},
        };

        for (int i = 0; i < validators.length; i++) {
            System.out.printf("\n================== gentx index[%d] ====================\n", i);
            gentx(validators[i]);
        }

    }
}
