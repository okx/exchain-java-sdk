package io.okchain.http;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.okchain.client.OKChainClient;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OKChainRequest {
    public static String GetAccount(OKChainClient client) {
        String url = client.serverUrl+Url.accountUrlPath+client.userAddress;
        return httpGet(url);
    }
    public  static String GetSequance(OKChainClient client) {
        String res = GetAccount(client);
        System.out.println(res);
        JsonParser parse =new JsonParser();
        try {
            JsonObject json=(JsonObject) parse.parse(res);
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
        JsonParser parse =new JsonParser();
        try {
            JsonObject json=(JsonObject) parse.parse(res);
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
        try{
            String res = sendGetData(url,"");
            return res;
        }catch (ClientProtocolException e){
            return e.getMessage();
        }catch (IOException e){
            return e.getMessage();
        }

    }

    private static String httpPost(String url,String data) {
        try{
            String res = sendPostDataByJson(url,data,"");
            return res;
        }catch (ClientProtocolException e){
            return e.getMessage();
        }catch (IOException e){
            return e.getMessage();
        }

    }

    private static String sendPostDataByMap(String url, Map<String, String> map, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));

        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        CloseableHttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        response.close();

        return result;
    }


    private static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        response.close();

        return result;
    }


    private static String sendGetData(String url, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        response.close();

        return result;
    }



}
