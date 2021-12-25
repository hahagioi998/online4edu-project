package com.online4edu.dependencies.utils.http;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 客户端工具
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:43
 */
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

    public static String requestGet(String url) {
        return requestGet(url, null);
    }

    public static String requestGet(String url, Map<String, Object> params) {
        return requestGet(url, params, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestGet(String url, Map<String, Object> params, CustomRequestConfig customRequestConfig) {

        HttpGet httpGet = HttpWrapper.createGet(url, params);
        return doRequest(url, httpGet, customRequestConfig);
    }

    public static String requestPostForm(String url, Map<String, Object> params) {
        return requestPostForm(url, params, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostForm(String url, Map<String, Object> params, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostForm(url, params);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostJson(String url, String json) {
        return requestPostJson(url, json, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostJson(String url, String json, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostJson(url, json);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostJson(String url, Object obj) {
        return requestPostJson(url, obj, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostJson(String url, Object obj, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostJson(url, obj);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostXml(String url, String xml) {
        return requestPostXml(url, xml, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostXml(String url, String xml, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostXml(url, xml);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostXml(String url, Object obj) {
        return requestPostXml(url, obj, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostXml(String url, Object obj, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostXml(url, obj);
        return doRequest(url, httpPost, customRequestConfig);
    }


    public static String requestPostFile(String url, File file) {
        return requestPostFile(url, file, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, File file, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, file);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, File file, ContentType contentType) {
        return requestPostFile(url, file, contentType, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, File file, ContentType contentType, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, file, contentType);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String filename, String fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, File fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, InputStream fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, byte[] fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, String fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, File fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, InputStream fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, byte[] fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String name, String filename, String fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String name, String filename, File fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String name, String filename, InputStream fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String name, String filename, byte[] fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(url, httpPost, customRequestConfig);
    }

    /**
     * Batch File Uploading
     * <p>
     * The map object stores multiple files to be uploaded. Key is the
     * name of the file, value is the file data corresponding to the file
     * name, which can be {@link InputStream} and {@link File} and {@link String} and byte[]
     */
    public static String requestPostFile(String url, Map<String, Object> content) {
        return requestPostFile(url, "file", content);
    }

    public static String requestPostFile(String url, String name, Map<String, Object> content) {
        return requestPostFile(url, name, content, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String name, Map<String, Object> content, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, content);
        return doRequest(url, httpPost, customRequestConfig);
    }

    public static void requestDownloadFile(String url, String writeName) {
        requestDownloadFile(url, writeName, HttpWrapper.createCustomRequestConfig(0));
    }

    public static void requestDownloadFile(String url, String writeName, CustomRequestConfig customRequestConfig) {
        requestDownloadFile(Collections.singletonList(url), writeName, HttpWrapper.createCustomRequestConfig(0));
    }

    public static void requestDownloadFile(List<String> urlList, String writeName) {
        requestDownloadFile(urlList, writeName, HttpWrapper.createCustomRequestConfig(0));
    }

    public static void requestDownloadFile(List<String> urlList, String writeName, CustomRequestConfig customRequestConfig) {
        if (CollectionUtils.isEmpty(urlList)) {
            throw new HttpException("the file url is empty");
        }

        createDownloadPath(writeName);

        try (FileOutputStream outputStream = new FileOutputStream(writeName)) {
            for (String url : urlList) {
                doDownload(url, outputStream, customRequestConfig);
            }
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    public static String doRequest(String url, HttpRequestBase httpRequestBase, CustomRequestConfig customRequestConfig) {
        try {
            if (!init) {
                init(CONNECTION_REQUEST_TIMEOUT, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
            }
            // Merge Custom Request Config
            HttpWrapper.mergeCustomRequestConfig(httpRequestBase, customRequestConfig, defaultRequestConfig);
            return httpClient.execute(HttpHost.create(url), httpRequestBase, new TxtResponseHandler());
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
            httpRequestBase.releaseConnection();
        }
    }

    public static void doRequest(String url, CustomRequestConfig customRequestConfig, Consumer<InputStream> consumer) {

        HttpGet httpGet = new HttpGet(url);
        try {
            if (!init) {
                init(CONNECTION_REQUEST_TIMEOUT, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
            }
            // Merge Custom Request Config
            HttpWrapper.mergeCustomRequestConfig(httpGet, customRequestConfig, defaultRequestConfig);
            InputStream inputStream = httpClient.execute(HttpHost.create(url), httpGet, new InputStreamResponseHandler());
            consumer.accept(inputStream);
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
            httpGet.releaseConnection();
        }
    }


    public static void doDownload(String url, FileOutputStream outputStream, CustomRequestConfig customRequestConfig) {
        doRequest(url, customRequestConfig, inputStream -> {
            try {
                int length;
                byte[] tmp = new byte[1024];
                while ((length = inputStream.read(tmp)) != -1) {
                    outputStream.write(tmp, 0, length);
                }
            } catch (IOException e) {
                throw new HttpException("write file exception", e);
            }
        });
    }

    private static void createDownloadPath(String downloadPath) {
        try {
            File file = new File(downloadPath);
            if (file.exists()) {
                return;
            }
            if (file.isDirectory()) {
                FileUtils.forceMkdir(file);
            } else {
                FileUtils.forceMkdir(file.getParentFile());
            }
        } catch (Exception e) {
            throw new HttpException(String.format("cannot create dir %s", downloadPath), e);
        }
    }

    public static void main(String[] args) {

        System.out.println(requestGet("http://baidu.com"));

        requestDownloadFile("http://git-media.knowledge.ituknown.cn/%E4%B8%AD%E6%96%87%E4%B9%B1%E7%A0%81/git-status%20%E4%B9%B1%E7%A0%81-1639887120rGTyu1GBhT.png", "/Users/mingrn97/Desktop/down/1.mp4");
    }

}
