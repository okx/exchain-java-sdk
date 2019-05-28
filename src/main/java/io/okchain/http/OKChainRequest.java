package io.okchain.http;

import com.github.kevinsawicki.http.HttpRequest;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
//    private static String httpGet(String url) {
//        System.out.println("GET: "+url);
//        try {
//            HttpRequest request =  HttpRequest.get(url);
//            String res = request.body();
//            return res;
//        } catch (HttpRequest.HttpRequestException exception) {
//            exception.printStackTrace();
//        }
//        return "";
//    }
//    private  static String httpPost(String url,String data) {
//        System.out.println("POST: "+url);
//        try {
//            HttpRequest request =  HttpRequest.post(url).send(data.getBytes());
//            String res = request.body();
//            return res;
//        } catch (HttpRequest.HttpRequestException exception) {
//            exception.printStackTrace();
//        }
//        return "";
//    }

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

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));

        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        // 释放链接
        response.close();

        return result;
    }

    /**
     * post请求传输json数据
     *
     * @param url
     * @param json
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    private static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        // 释放链接
        response.close();

        return result;
    }

    /**
     * get请求传输数据
     *
     * @param url
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    private static String sendGetData(String url, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/json");
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        // 释放链接
        response.close();

        return result;
    }



}
