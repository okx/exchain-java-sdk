package com.okexchain.utils;

import com.okexchain.msg.common.Pair;
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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    static HttpPost m_httpPost = null;
    static HttpGet m_httpGet = null;
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

    public static String httpGet(String httpUrl) {
        return sendGet(httpUrl, null);
    }


    /**
     * GET请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String httpPost(String url, String data) {
        return sendPost(url, data);
    }


    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println("result = " + result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        }
        return result.toString();
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
        if (m_httpPost == null) {
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

        if (m_httpGet == null) {
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
