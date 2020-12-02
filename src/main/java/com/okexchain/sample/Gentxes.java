package com.okexchain.sample;

import com.okexchain.msg.MsgCreateValidator;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.Utils;

public class Gentxes {

    public static void gentx(String[] params, int idx) {

        MsgCreateValidator msg = new MsgCreateValidator();
        msg.init(params[0],"0","0");

        Message messages = msg.produceMsg(
                params[1], params[2],
                params[3], params[4],
                params[5], "10000.00000000");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"", "200000", "v"+String.valueOf(idx));
            msg.persist("unsigned." + String.valueOf(idx) +".json", unsignedTx.toString());
            System.out.println("======= unsignedTx json =========");
            System.out.println(unsignedTx.toString());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }


    public static void main(String[] args) {

        String[][] validators = {
                {"03425b43cbe9e7e50bd9a1c114d59d73cf28ef0ac7fcdb8c86ac39b1641cdd60dd",
                        "okexchainvalconspub1zcjduepq4zcj4e2m6h6tm76ucrvkn5drxa6j2qt5s8qgf0ajwa6kn2pdgksq5lvlcl",
                        "OKEx",  "https://www.okex.com", "https://static.okex.com/cdn/assets/imgs/MjAxOTQ/5CAB3C0086E2F1135A3428116E435B8A.png", "The World's Leading One-Stop Crypto Exchange",},

                {"02a286cfc19239850191f8c94aa6ee4ced75716895536330db4883d6040fc15aed",
                        "okexchainvalconspub1zcjduepqy6tlu8zak7vj20cpalng3e2hadv8gadwyk6zr2wr4chnr08at3mqc0d899",
                        "OKLink",  "https://www.oklink.com", "https://static.bafang.com/cdn/assets/imgs/MjAyMDM/238A87F2CCB1F1B2184CB21DC55D0EA0.png", "World-Leading Blockchain Explorer",},

                {"029dddedc1abe0c7581040437f7226461bcd91b022c3cd2e6aed4e1dcf7f175591",
                        "okexchainvalconspub1zcjduepq0ezwe9ev35v9ht370h3wkhqm33a3sc2ndwa0s2e7hj6zuw68qdnqcsfu0t",
                        "OKEx PooL",  "https://www.okex.com/pool", "identity", "The World's Leading Blockchain Digital Asset Mining Platform",},

                {"026c09a0de87e4eb4ae10e88af466a25499970e7408a9971ecb14c50f59081e474",
                        "okexchainvalconspub1zcjduepqqaes4htn3vsan8c9c8hs90w2vyzk0d8hlxwupawxz4rhp6srg0ts0cvc4k",
                        "OKEx DEX",  "https://www.okex.com/dex-test", "identity", "OKEx DEX Borderless decentralized trading platform",},
        };

        for (int i = 0; i < validators.length; i++) {
            System.out.printf("\n================== gentx index[%d] ====================\n", i);
            gentx(validators[i], i);
        }

    }
}
