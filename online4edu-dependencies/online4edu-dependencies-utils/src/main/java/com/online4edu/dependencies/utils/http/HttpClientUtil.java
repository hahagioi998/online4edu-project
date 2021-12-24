package com.online4edu.dependencies.utils.http;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.Map;

public class HttpClientUtil {

    private static volatile boolean init = false;

    private static RequestConfig defaultRequestConfig;

    private static CloseableHttpClient httpClient;

    /**
     * 从连接池获取连接的超时时间
     */
    public static final int CONNECTION_REQUEST_TIMEOUT = 3000;

    /**
     * 客户端和服务器建立连接超时时间 {@link org.apache.http.conn.ConnectTimeoutException}
     */
    public static final int CONNECT_TIMEOUT = 3000;

    /**
     * 成功建立连接后, 客户端从服务器读取数据的超时时间 {@link java.net.SocketTimeoutException}
     */
    public static final int SOCKET_TIMEOUT = 10000;

    /**
     * 默认每路由最大并发数
     */
    public static final int DEFAULT_MAX_PER_ROUTE = 20;

    /**
     * 全局最大并发数
     */
    public static final int MAX_TOTAL = 500;

    /**
     * 定时清理陈旧线程周期
     */
    public static final int VALIDATE_AFTER_INACTIVITY = 60000;

    public static synchronized void init(int connectionRequestTimeout, int connectTimeout, int socketTimeout) {

        if (init) {
            return;
        }

        // 连接池
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        connectionManager.setMaxTotal(MAX_TOTAL);

        defaultRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        init = true;
    }

    public static void requestGet(String url) {
        requestGet(url, null);
    }

    public static void requestGet(String url, Map<String, Object> params) {
        requestGet(url, null, HttpUtil.createCustomRequestConfig(0));
    }

    public static void requestGet(String url, Map<String, Object> params, CustomRequestConfig customRequestConfig) {

        HttpGet httpGet = HttpUtil.createGet(url, params);
        HttpUtil.mergeCustomRequestConfig(httpGet, customRequestConfig, defaultRequestConfig);

        doRequest(url, httpGet);
    }

    public static void requestPostForm(String url, Map<String, Object> params) {
        requestPostForm(url, null, HttpUtil.createCustomRequestConfig(0));
    }

    public static void requestPostForm(String url, Map<String, Object> params, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpUtil.createPostForm(url, params);
        HttpUtil.mergeCustomRequestConfig(httpPost, customRequestConfig, defaultRequestConfig);

        doRequest(url, httpPost);
    }

    public static void doRequest(String url, HttpRequestBase httpRequestBase) {
        try {
            String result = httpClient.execute(HttpHost.create(url), httpRequestBase, new CustomResponseHandler());
            System.out.println(result);
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
            httpRequestBase.releaseConnection();
        }
    }


    public static void main(String[] args) {

        System.out.println(HttpHost.create("https://httpbin.org/get"));
    }

}
