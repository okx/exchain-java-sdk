package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.env.LocalEnv;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.msg.*;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.common.Signature;

public class Staking {

    public static void main(String[] args) {
        testUnjail();
//        testBindProxy();
//        testEditValidator();
//        testMsgDeposit();
//        testMsgWithdrawStaking();
//        testMsgAddShares();
//        testRegProxy();
    }

    static void testBindProxy() {
//        String mnemonic = "puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer";
//        String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
//        System.out.println(prikey);

        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));
        PrivateKey key = new PrivateKey("769CE08B6AAD66A579A1925F9D208570296EB505CC7819D7E6D7A0388EB4FD51");

        MsgBindProxy msg = new MsgBindProxy();
        msg.init(key.getAddress(), key.getPubKey());

        String delegator_address = "okexchain1tscazctcr3jvtdx4harrzm75xltlccrr36cw54";
        String proxy_address = "okexchain1pt7xrmxul7sx54ml44lvv403r06clrdkgmvr9g";

        Message messages = msg.produceMsg(delegator_address, proxy_address);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testEditValidator(){
        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
        MsgEditValidator msg = new MsgEditValidator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg("1","1","1","1", "okexchainvaloper1pt7xrmxul7sx54ml44lvv403r06clrdkfluue2");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgDeposit() {
        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));
        PrivateKey key = new PrivateKey("769CE08B6AAD66A579A1925F9D208570296EB505CC7819D7E6D7A0388EB4FD51");
        MsgDeposit msg = new MsgDeposit();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg("tokt", "10.000000000000000000", "okexchain1tscazctcr3jvtdx4harrzm75xltlccrr36cw54");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgWithdrawStaking() {
        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
        MsgWithdrawStaking msg = new MsgWithdrawStaking();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg("tokt", "10.00000000", "okexchain1pt7xrmxul7sx54ml44lvv403r06clrdkgmvr9g");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgAddShares() {
        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
        MsgAddShares msg = new MsgAddShares();
        msg.init(key.getAddress(), key.getPubKey());

        String [] validators = {"okexchainvaloper1pt7xrmxul7sx54ml44lvv403r06clrdkfluue2"};
        Message messages = msg.produceMsg("okexchain1pt7xrmxul7sx54ml44lvv403r06clrdkgmvr9g", validators);


        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testRegProxy() {
        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
        MsgRegProxy msg = new MsgRegProxy();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg("okexchain1pt7xrmxul7sx54ml44lvv403r06clrdkgmvr9g", true);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testUnjail() {
        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
        MsgUnjail msg = new MsgUnjail();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg("okexchainvaloper1pt7xrmxul7sx54ml44lvv403r06clrdkfluue2");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
