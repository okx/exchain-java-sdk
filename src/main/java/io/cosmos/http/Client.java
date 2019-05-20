package io.cosmos.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;
import io.cosmos.common.Configuration;
import io.cosmos.common.Utils;
import io.cosmos.exception.APIException;
import io.cosmos.exception.CosmosException;
import io.cosmos.exception.ConfigurationException;
import io.cosmos.exception.JSONException;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * The Client object contains all information necessary to
 * perform an HTTP request against a remote API. Typically,
 * an application will have a client that makes requests to
 * a Chain Core, and a separate Client that makes requests
 * to an HSM server.
 */
public class Client {
    private String url;
    private String accessToken;
    private OkHttpClient httpClient;

    // Used to create empty, in-memory key stores.
    private static final char[] DEFAULT_KEYSTORE_PASSWORD = "123456".toCharArray();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String version = "dev"; // updated in the static initializer

    public static Logger logger = Logger.getLogger(Client.class);

    private static class BuildProperties {
        public String version;
    }

    static {
        InputStream in = Client.class.getClassLoader().getResourceAsStream("properties.json");
        if (in != null) {
            InputStreamReader inr = new InputStreamReader(in);
            version = Utils.serializer.fromJson(inr, BuildProperties.class).version;
        }
    }

    public static Client generateClient() throws CosmosException {

        String coreURL = Configuration.getValue("cosmos.api.url");
        String accessToken = Configuration.getValue("client.access.token");

        if (coreURL == null || coreURL.isEmpty()) {
            coreURL = "http://47.91.254.104:8888";
        }

        if (coreURL.endsWith("/")) {
            //split the last char "/"
            coreURL = coreURL.substring(0, coreURL.length()-1);
            logger.info("check the coreURL is right.");
        }

        return new Client(coreURL, accessToken);
    }

    public Client(Builder builder) throws ConfigurationException {
        if (builder.url.endsWith("/")) {
            //split the last char "/"
            builder.url = builder.url.substring(0, builder.url.length()-1);
        }
        this.url = builder.url;
        this.accessToken = builder.accessToken;
        this.httpClient = buildHttpClient(builder);
    }

    /**
     * Create a new http Client object using the default development host URL.
     */
    public Client() throws CosmosException {
        this(new Builder());
    }

    /**
     * Create a new http Client object
     *
     * @param url the URL of the Chain Core or HSM
     */
    public Client(String url) throws CosmosException {
        this(new Builder().setUrl(url));
    }

    /**
     * Create a new http Client object
     *
     * @param url         the URL of the Chain Core or HSM
     * @param accessToken a Client API access token
     */
    public Client(String url, String accessToken) throws CosmosException {
        this(new Builder().setUrl(url).setAccessToken(accessToken));
    }

    /**
     * Perform a single HTTP POST request against the API for a specific action.
     *
     * @param action The requested API action
     * @param body   Body payload sent to the API as JSON
     * @param tClass Type of object to be deserialized from the response JSON
     * @return the result of the post request
     * @throws CosmosException
     */
    public <T> T request(String action, Object body, final Type tClass) throws CosmosException {
        ResponseCreator<T> rc =
                new ResponseCreator<T>() {
                    public T create(Response response, Gson deserializer) throws IOException, CosmosException {
                        JsonElement root = new JsonParser().parse(response.body().charStream());
                        JsonElement status = root.getAsJsonObject().get("status");
                        JsonElement data = root.getAsJsonObject().get("data");
                        if (status != null && status.toString().contains("fail")) {
                            throw new CosmosException(root.getAsJsonObject().get("msg").toString());
                        } else if (data != null) {
                            return deserializer.fromJson(data, tClass);
                        } else {
                            return deserializer.fromJson(response.body().charStream(), tClass);
                        }
                    }
                };
        return post(action, body, rc);
    }

    /**
     * Perform a single HTTP POST request against the API for a specific action,
     * ignoring the body of the response.
     *
     * @param action The requested API action
     * @param body   Body payload sent to the API as JSON
     * @throws CosmosException
     */
    public void request(String action, Object body) throws CosmosException {
        ResponseCreator<Object> rc =
                new ResponseCreator<Object>() {
                    public Object create(Response response, Gson deserializer) throws IOException, CosmosException {
                        JsonElement root = new JsonParser().parse(response.body().charStream());
                        JsonElement status = root.getAsJsonObject().get("status");
                        JsonElement data = root.getAsJsonObject().get("data");
                        if (status != null && status.toString().contains("fail")) {
                            throw new CosmosException(root.getAsJsonObject().get("msg").toString());
                        }
                        return null;
                    }
                };
        post(action, body, rc);
    }

    /**
     * return the value of named as key from json
     *
     * @param action
     * @param body
     * @param key
     * @param tClass
     * @param <T>
     * @return
     * @throws CosmosException
     */
    public <T> T requestGet(String action, Object body, final String key, final Type tClass)
            throws CosmosException {
        ResponseCreator<T> rc = new ResponseCreator<T>() {
            public T create(Response response, Gson deserializer) throws IOException,
                    CosmosException {
                JsonElement root = new JsonParser().parse(response.body().charStream());
                JsonElement status = root.getAsJsonObject().get("status");
                JsonElement data = root.getAsJsonObject().get("data");

                if (status != null && status.toString().contains("fail"))
                    throw new CosmosException(root.getAsJsonObject().get("msg").toString());
                else if (data != null)
                    return deserializer.fromJson(data.getAsJsonObject().get(key), tClass);
                else
                    return deserializer.fromJson(response.body().charStream(), tClass);
            }
        };
        return post(action, body, rc);
    }

    /**
     * Perform a single HTTP POST request against the API for a specific action.
     * Use this method if you want batch semantics, i.e., the endpoint response
     * is an array of valid objects interleaved with arrays, once corresponding to
     * each input object.
     *
     * @param action The requested API action
     * @param body   Body payload sent to the API as JSON
     * @param tClass Type of object to be deserialized from the response JSON
     * @return the result of the post request
     * @throws CosmosException
     */
    /*
    public <T> T requestBatch(String action, Object body, final Type tClass) throws CosmosException {
        ResponseCreator<T> rc =
                new ResponseCreator<T>() {
                    public T create(Response response, Gson deserializer) throws IOException, CosmosException {
                        JsonElement root = new JsonParser().parse(response.body().charStream());
                        JsonElement status = root.getAsJsonObject().get("status");
                        JsonElement data = root.getAsJsonObject().get("data");
                        if (status != null && status.toString().contains("fail")) {
                            throw new CosmosException(root.getAsJsonObject().get("msg").toString());
                        } else if (data != null) {
                            return deserializer.fromJson(data, tClass);
                        } else {
                            return deserializer.fromJson(response.body().charStream(), tClass);
                        }
                    }
                };
        //把object转换为json对象数组（有点难）

        //根据数组的大小循环调用post()方法

        //重写create()接口方法，对成功和失败做不同的处理

        //调用BatchResponse(Map<Integer, T> successes, Map<Integer, APIException> errors)
        //构造方法，最后返回BatchResponse实例对象

        return post(action, body, rc);
    }
    */


    /**
     * Returns true if a client access token stored in the client.
     *
     * @return a boolean
     */
    public boolean hasAccessToken() {
        return this.accessToken != null && !this.accessToken.isEmpty();
    }

    /**
     * Returns the client access token (possibly null).
     *
     * @return the client access token
     */
    public String accessToken() {
        return accessToken;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Pins a public key to the HTTP client.
     *
     * @param provider           certificate provider
     * @param subjPubKeyInfoHash public key hash
     */
    public void pinCertificate(String provider, String subjPubKeyInfoHash) {
        CertificatePinner cp =
                new CertificatePinner.Builder().add(provider, subjPubKeyInfoHash).build();
        this.httpClient.setCertificatePinner(cp);
    }

    /**
     * Sets the default connect timeout for new connections. A value of 0 means no timeout.
     *
     * @param timeout the number of time units for the default timeout
     * @param unit    the unit of time
     */
    public void setConnectTimeout(long timeout, TimeUnit unit) {
        this.httpClient.setConnectTimeout(timeout, unit);
    }

    /**
     * Sets the default read timeout for new connections. A value of 0 means no timeout.
     *
     * @param timeout the number of time units for the default timeout
     * @param unit    the unit of time
     */
    public void setReadTimeout(long timeout, TimeUnit unit) {
        this.httpClient.setReadTimeout(timeout, unit);
    }

    /**
     * Sets the default write timeout for new connections. A value of 0 means no timeout.
     *
     * @param timeout the number of time units for the default timeout
     * @param unit    the unit of time
     */
    public void setWriteTimeout(long timeout, TimeUnit unit) {
        this.httpClient.setWriteTimeout(timeout, unit);
    }

    /**
     * Sets the proxy information for the HTTP client.
     *
     * @param proxy proxy object
     */
    public void setProxy(Proxy proxy) {
        this.httpClient.setProxy(proxy);
    }

    /**
     * Defines an interface for deserializing HTTP responses into objects.
     *
     * @param <T> the type of object to return
     */
    public interface ResponseCreator<T> {
        /**
         * Deserializes an HTTP response into a Java object of type T.
         *
         * @param response     HTTP response object
         * @param deserializer json deserializer
         * @return an object of type T
         * @throws CosmosException
         * @throws IOException
         */
        T create(Response response, Gson deserializer) throws CosmosException, IOException;
    }

    /**
     * Builds and executes an HTTP Post request.
     *
     * @param path        the path to the endpoint
     * @param body        the request body
     * @param respCreator object specifying the response structure
     * @return a response deserialized into type T
     * @throws CosmosException
     */
    private <T> T post(String path, Object body, ResponseCreator<T> respCreator)
            throws CosmosException {

        RequestBody requestBody = RequestBody.create(this.JSON, Utils.serializer.toJson(body));
        Request req;

        CosmosException exception = null;
        URL endpointURL = null;

        try {
            endpointURL = new URL(url + "/" + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Request.Builder builder =
                new Request.Builder()
                        .header("User-Agent", "cosmos-sdk-java/" + version)
                        .url(endpointURL)
                        .method("POST", requestBody);
        if (hasAccessToken()) {
            builder = builder.header("Authorization", buildCredentials());
        }
        req = builder.build();

        Response resp = null;

        T object = null;

        try {
            resp = this.checkError(this.httpClient.newCall(req).execute());
            object = respCreator.create(resp, Utils.serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return object;
    }

    private OkHttpClient buildHttpClient(Builder builder) throws ConfigurationException {
        OkHttpClient httpClient = builder.baseHttpClient.clone();

        try {
            if (builder.trustManagers != null) {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(builder.keyManagers, builder.trustManagers, null);
                httpClient.setSslSocketFactory(sslContext.getSocketFactory());
            }
        } catch (GeneralSecurityException ex) {
            throw new ConfigurationException("Unable to configure TLS", ex);
        }
        if (builder.readTimeoutUnit != null) {
            httpClient.setReadTimeout(builder.readTimeout, builder.readTimeoutUnit);
        }
        if (builder.writeTimeoutUnit != null) {
            httpClient.setWriteTimeout(builder.writeTimeout, builder.writeTimeoutUnit);
        }
        if (builder.connectTimeoutUnit != null) {
            httpClient.setConnectTimeout(builder.connectTimeout, builder.connectTimeoutUnit);
        }
        if (builder.pool != null) {
            httpClient.setConnectionPool(builder.pool);
        }
        if (builder.proxy != null) {
            httpClient.setProxy(builder.proxy);
        }
        if (builder.cp != null) {
            httpClient.setCertificatePinner(builder.cp);
        }

        return httpClient;
    }

    private static final Random randomGenerator = new Random();
    private static final int MAX_RETRIES = 10;
    private static final int RETRY_BASE_DELAY_MILLIS = 40;

    // the max amount of time cored leader election could take
    private static final int RETRY_MAX_DELAY_MILLIS = 15000;

    private static int retryDelayMillis(int retryAttempt) {
        // Calculate the max delay as base * 2 ^ (retryAttempt - 1).
        int max = RETRY_BASE_DELAY_MILLIS * (1 << (retryAttempt - 1));
        max = Math.min(max, RETRY_MAX_DELAY_MILLIS);

        // To incorporate jitter, use a pseudo random delay between [max/2, max] millis.
        return randomGenerator.nextInt(max / 2) + max / 2 + 1;
    }

    private static final int[] RETRIABLE_STATUS_CODES = {
            408, // Request Timeout
            429, // Too Many Requests
            500, // Internal Server Error
            502, // Bad Gateway
            503, // Service Unavailable
            504, // Gateway Timeout
            509, // Bandwidth Limit Exceeded
    };

    private static boolean isRetriableStatusCode(int statusCode) {
        for (int i = 0; i < RETRIABLE_STATUS_CODES.length; i++) {
            if (RETRIABLE_STATUS_CODES[i] == statusCode) {
                return true;
            }
        }
        return false;
    }

    private Response checkError(Response response) throws CosmosException {
        /*
        String rid = response.headers().get("cosmos-Request-ID");
        if (rid == null || rid.length() == 0) {
            // Header field cosmos-Request-ID is set by the backend
            // API server. If this field is set, then we can expect
            // the body to be well-formed JSON. If it's not set,
            // then we are probably talking to a gateway or proxy.
            throw new ConnectivityException(response);
        } */

        if ((response.code() / 100) != 2) {
            try {
                APIException err =
                        Utils.serializer.fromJson(response.body().charStream(), APIException.class);
                if (err.code != null) {
                    //err.requestId = rid;
                    err.statusCode = response.code();
                    throw err;
                }
            } catch (IOException ex) {
                //throw new JSONException("Unable to read body. " + ex.getMessage(), rid);
                throw new JSONException("Unable to read body. ");
            }
        }
        return response;
    }

    private String buildCredentials() {
        String user = "";
        String pass = "";
        if (hasAccessToken()) {
            String[] parts = accessToken.split(":");
            if (parts.length >= 1) {
                user = parts[0];
            }
            if (parts.length >= 2) {
                pass = parts[1];
            }
        }
        return Credentials.basic(user, pass);
    }

    /**
     * Overrides {@link Object#hashCode()}
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int code = this.url.hashCode();
        if (this.hasAccessToken()) {
            code = code * 31 + this.accessToken.hashCode();
        }
        return code;
    }

    /**
     * Overrides {@link Object#equals(Object)}
     *
     * @param o the object to compare
     * @return a boolean specifying equality
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Client)) return false;

        Client other = (Client) o;
        if (!this.url.equalsIgnoreCase(other.url)) {
            return false;
        }
        return Objects.equals(this.accessToken, other.accessToken);
    }

    /**
     * A builder class for creating client objects
     */
    public static class Builder {

        private String url;

        private OkHttpClient baseHttpClient;
        private String accessToken;
        private CertificatePinner cp;
        private KeyManager[] keyManagers;
        private TrustManager[] trustManagers;
        private long connectTimeout;
        private TimeUnit connectTimeoutUnit;
        private long readTimeout;
        private TimeUnit readTimeoutUnit;
        private long writeTimeout;
        private TimeUnit writeTimeoutUnit;
        private Proxy proxy;
        private ConnectionPool pool;

        public Builder() {
            this.baseHttpClient = new OkHttpClient();
            this.baseHttpClient.setFollowRedirects(false);
            this.setDefaults();
        }

        public Builder(Client client) {
            this.baseHttpClient = client.httpClient.clone();
            this.url = client.url;
            this.accessToken = client.accessToken;
        }

        private void setDefaults() {
            this.setReadTimeout(30, TimeUnit.SECONDS);
            this.setWriteTimeout(30, TimeUnit.SECONDS);
            this.setConnectTimeout(30, TimeUnit.SECONDS);
            this.setConnectionPool(50, 2, TimeUnit.MINUTES);
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the access token for the client
         *
         * @param accessToken The access token for the Chain Core or HSM
         */
        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }





        /**
         * Trusts the given CA certs, and no others. Use this if you are running
         * your own CA, or are using a self-signed server certificate.
         *
         * @param is input stream of the certificates to trust, in PEM format.
         */
        public Builder setTrustedCerts(InputStream is) throws ConfigurationException {
            try {
                // Extract certs from PEM-encoded input.
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                Collection<? extends Certificate> certificates =
                        certificateFactory.generateCertificates(is);
                if (certificates.isEmpty()) {
                    throw new IllegalArgumentException("expected non-empty set of trusted certificates");
                }

                // Create a new key store and input the cert.
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, DEFAULT_KEYSTORE_PASSWORD);
                int index = 0;
                for (Certificate certificate : certificates) {
                    String certificateAlias = Integer.toString(index++);
                    keyStore.setCertificateEntry(certificateAlias, certificate);
                }

                // Use key store to build an X509 trust manager.
                KeyManagerFactory keyManagerFactory =
                        KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, DEFAULT_KEYSTORE_PASSWORD);
                TrustManagerFactory trustManagerFactory =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException(
                            "Unexpected default trust managers:" + Arrays.toString(trustManagers));
                }

                this.trustManagers = trustManagers;
                return this;
            } catch (GeneralSecurityException | IOException ex) {
                throw new ConfigurationException("Unable to configure trusted CA certs", ex);
            }
        }

        /**
         * Trusts the given CA certs, and no others. Use this if you are running
         * your own CA, or are using a self-signed server certificate.
         *
         * @param path The path of a file containing certificates to trust, in PEM format.
         */
        public Builder setTrustedCerts(String path) throws ConfigurationException {
            try (InputStream is = new FileInputStream(path)) {
                return setTrustedCerts(is);
            } catch (IOException ex) {
                throw new ConfigurationException("Unable to configure trusted CA certs", ex);
            }
        }

        /**
         * Sets the certificate pinner for the client
         *
         * @param provider           certificate provider
         * @param subjPubKeyInfoHash public key hash
         */
        public Builder pinCertificate(String provider, String subjPubKeyInfoHash) {
            this.cp = new CertificatePinner.Builder().add(provider, subjPubKeyInfoHash).build();
            return this;
        }

        /**
         * Sets the connect timeout for the client
         *
         * @param timeout the number of time units for the default timeout
         * @param unit    the unit of time
         */
        public Builder setConnectTimeout(long timeout, TimeUnit unit) {
            this.connectTimeout = timeout;
            this.connectTimeoutUnit = unit;
            return this;
        }

        /**
         * Sets the read timeout for the client
         *
         * @param timeout the number of time units for the default timeout
         * @param unit    the unit of time
         */
        public Builder setReadTimeout(long timeout, TimeUnit unit) {
            this.readTimeout = timeout;
            this.readTimeoutUnit = unit;
            return this;
        }

        /**
         * Sets the write timeout for the client
         *
         * @param timeout the number of time units for the default timeout
         * @param unit    the unit of time
         */
        public Builder setWriteTimeout(long timeout, TimeUnit unit) {
            this.writeTimeout = timeout;
            this.writeTimeoutUnit = unit;
            return this;
        }

        /**
         * Sets the proxy for the client
         *
         * @param proxy
         */
        public Builder setProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        /**
         * Sets the connection pool for the client
         *
         * @param maxIdle the maximum number of idle http connections in the pool
         * @param timeout the number of time units until an idle http connection in the pool is closed
         * @param unit    the unit of time
         */
        public Builder setConnectionPool(int maxIdle, long timeout, TimeUnit unit) {
            this.pool = new ConnectionPool(maxIdle, unit.toMillis(timeout));
            return this;
        }

        /**
         * Builds a client with all of the provided parameters.
         */
        public Client build() throws ConfigurationException {
            return new Client(this);
        }
    }
}
