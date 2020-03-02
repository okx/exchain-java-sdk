package sample;

import com.alibaba.fastjson.JSONObject;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.AccountInfo;
import com.okchain.types.RequestPlaceOrderParams;
import com.okchain.types.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubmitTransactions {
    private static String url_rpc = "http://3.13.150.20:26657";
    private static String privateKey =
            "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";
    private static String mnemo =
            "total lottery arena when pudding best candy until army spoil drill pool";


    /**
     * Submit a transfer transaction to OKChain.
     * @throws IOException
     */
    public static void submitTransferTransaction() throws NullPointerException, IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        AccountInfo account = client.getAccountInfo(privateKey);
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "send memo";
        List<Token> amountList = new ArrayList<>();
        Token amount = new Token("1.00000000", "tokt");
        amountList.add(amount);
        JSONObject ret = client.sendSendTransaction(account, to, amountList, memo);
        System.out.println(ret);
    }

    /**
     * Submit a placeOrder transaction to OKChain.
     * @throws IOException
     */
    public static void submitPlaceOrderTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        AccountInfo account = client.getAccountInfo(privateKey);
        String side = "BUY";
        String product = "tbtc_tusdk";
        String price = "1.10000000";
        String quantity = "1.22000000";
        String memo = "new order memo";
        RequestPlaceOrderParams param = new RequestPlaceOrderParams(price, product, quantity, side);
        JSONObject ret = client.sendPlaceOrderTransaction(account, param, memo);
        System.out.println(ret);
    }

    /**
     * Submit a cancelOrder transaction to OKChain.
     * @param orderId You can get orderId when exec submitPlaceOrderTransaction.
     * @throws IOException
     */
    public static void submitCancelOrderTransaction(String orderId) throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        AccountInfo account = client.getAccountInfo(privateKey);
        String memo = "cancel order memo";
        JSONObject ret = client.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(ret);
    }

    /**
     * Get AccountInfo with privateKey or mnemonic.
     * This function will query the information of account from OKChain.
     */
    public static void getAccountInfo() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        AccountInfo account = client.getAccountInfo(privateKey);
        System.out.println(account.toString());
        AccountInfo accountFromMnemonic = client.getAccountInfoFromMnemonic(mnemo);
        System.out.println(accountFromMnemonic.toString());
    }

    public static void main(String[] args) throws NullPointerException, IOException {
        SubmitTransactions.submitPlaceOrderTransaction();
    }
}
