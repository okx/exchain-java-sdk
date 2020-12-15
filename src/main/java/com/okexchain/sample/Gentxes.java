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
                {"0322e2f6e84f7aaa61fd666cdf3ffb3f9e5cd823f55a107535f4f6809b7b046f3a",
                        "okexchainvalconspub1zcjduepq4zcj4e2m6h6tm76ucrvkn5drxa6j2qt5s8qgf0ajwa6kn2pdgksq5lvlcl",
                        "OKEx",  "https://www.okex.com", "https://static.okex.com/cdn/assets/imgs/MjAxOTQ/5CAB3C0086E2F1135A3428116E435B8A.png", "The World's Leading One-Stop Crypto Exchange",},

                {"030915f4d34a0c102478cc117df1183d145b6afc32897f06ca631b0a52d2665c99",
                        "okexchainvalconspub1zcjduepqy6tlu8zak7vj20cpalng3e2hadv8gadwyk6zr2wr4chnr08at3mqc0d899",
                        "OKLink",  "https://www.oklink.com", "https://static.bafang.com/cdn/assets/imgs/MjAyMDM/238A87F2CCB1F1B2184CB21DC55D0EA0.png", "World-Leading Blockchain Explorer",},

                {"03b0fd45a8d462bff54547b481855198ab4e1a2b7df438e6e570bb8665bba02592",
                        "okexchainvalconspub1zcjduepq0ezwe9ev35v9ht370h3wkhqm33a3sc2ndwa0s2e7hj6zuw68qdnqcsfu0t",
                        "OKEx PooL",  "https://www.okex.com/pool", "identity", "The World's Leading Blockchain Digital Asset Mining Platform",},

                {"02ab87eeb0a0fc7e747b444070fd9d83104077147728f98ebf93f68081b9c4c7d5",
                        "okexchainvalconspub1zcjduepqqaes4htn3vsan8c9c8hs90w2vyzk0d8hlxwupawxz4rhp6srg0ts0cvc4k",
                        "OKEx DEX",  "https://www.okex.com/dex-test", "identity", "OKEx DEX Borderless decentralized trading platform",},
        };

        for (int i = 0; i < validators.length; i++) {
            System.out.printf("\n================== gentx index[%d] ====================\n", i);
            gentx(validators[i], i);
        }

    }
}
