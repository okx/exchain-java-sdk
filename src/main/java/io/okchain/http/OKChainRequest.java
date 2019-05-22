package io.okchain.http;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.okchain.client.OKChainClient;

import java.io.FileNotFoundException;

public class OKChainRequest {
    public static String GetAccount(OKChainClient client) {
        String url = client.serverUrl+Url.accountUrlPath+client.userAddress;
        return httpGet(url);
    }
    public  static String GetSequance(OKChainClient client) {
        String res = GetAccount(client);
        System.out.println(res);
        JsonParser parse =new JsonParser();  //创建json解析器
        try {
            JsonObject json=(JsonObject) parse.parse(res);  //创建jsonObject对象
            return json.get("value").getAsJsonObject().get("sequence").getAsString();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }

    public  static String GetAccountNumber(OKChainClient client) {
        String res = GetAccount(client);
        System.out.println(res);
        JsonParser parse =new JsonParser();  //创建json解析器
        try {
            JsonObject json=(JsonObject) parse.parse(res);  //创建jsonObject对象
            return json.get("value").getAsJsonObject().get("account_number").getAsString();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String SendTransaction(OKChainClient client,String data) {
        String url = client.serverUrl+Url.transactionUrlPath;
        return httpPost(url,data);
    }
    private static String httpGet(String url) {
        System.out.println("GET: "+url);
        try {
            HttpRequest request =  HttpRequest.get(url);
            String res = request.body();
            return res;
        } catch (HttpRequest.HttpRequestException exception) {
            exception.printStackTrace();
        }
        return "";
    }
    private  static String httpPost(String url,String data) {
        System.out.println("POST: "+url);
        try {
            HttpRequest request =  HttpRequest.post(url).send(data.getBytes());
            String res = request.body();
            return res;
        } catch (HttpRequest.HttpRequestException exception) {
            exception.printStackTrace();
        }
        return "";
    }
}
