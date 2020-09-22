package io.cosmos.common;

import io.cosmos.types.Pair;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    static HttpPost m_httpPost = null;
    static HttpGet  m_httpGet = null;
    static CloseableHttpClient m_httpClient = HttpClients.createDefault();

    public static String httpGet(String url, ArrayList<Pair> pairs) {
        String params = "";
        if (pairs != null) {
            for (int i = 0; i < pairs.size(); i++) {
                String v = pairs.get(i).getValue();
                if (v == null || v.equals("")) {
                    pairs.remove(i);
                    i--;
                }
            }
            if (!pairs.isEmpty()) {
                params = pairs.get(0).getKey() + "=" + pairs.get(0).getValue();
                for (int i = 1; i < pairs.size(); i++) {
                    params = params + "&" + pairs.get(i).getKey() + "=" + pairs.get(i).getValue();
                }
            }

        }
        if (params.equals("")) return httpGet(url);
        return httpGet(url + "?" + params);
    }

    public static String httpGetDisable(String url) {
        //System.out.println("get:" + url);
        try {
            String res = sendGetData(url, "");
            return res;
        } catch (ClientProtocolException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    public static String httpGet(String httpUrl){
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();


        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();// 正常访问

            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");

            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (SocketException e) {
            System.out.println("Connection timed out: connect");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String httpPost(String url, String data) {
        //System.out.println("post: " + url);
        //System.out.println("data: " + data);
        try {
            String res = sendPostDataByJson(url, data, "");
            return res;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public static String sendPostDataByMap(String url, Map<String, String> map, String encoding) throws ClientProtocolException, IOException {
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

//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        response.close();

        return result;
    }


    public static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(m_httpPost == null){
            m_httpPost = new HttpPost(url);
        }
        HttpPost httpPost = m_httpPost;

        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);

//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        response.close();

        return result;
    }


    private static String sendGetData(String url, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        CloseableHttpClient httpClient = m_httpClient;

        if(m_httpGet == null){
            m_httpGet = new HttpGet(url);
        }
        HttpGet httpGet = m_httpGet;
        httpGet.addHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpGet);

//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
        result = EntityUtils.toString(response.getEntity(), "utf-8");
    //    response.close();

        return result;
    }


}
