package com.online4edu.dependencies.utils.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.online4edu.dependencies.utils.jackson.JacksonUtil;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础工具
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:44
 */
class HttpUtil {

    public static CustomRequestConfig createCustomRequestConfig(int socketTimeout) {
        return new CustomRequestConfig(socketTimeout);
    }

    public static void mergeCustomRequestConfig(HttpRequestBase requestBase, CustomRequestConfig customRequestConfig, RequestConfig defaultRequestConfig) {

        if (customRequestConfig == null) {
            return;
        }

        int timeout;
        HttpHost proxy;

        RequestConfig.Builder builder = RequestConfig.copy(defaultRequestConfig);

        builder.setRedirectsEnabled(customRequestConfig.isEnabledRedirect());

        if ((timeout = customRequestConfig.getConnectionRequestTimeout()) > 0) {
            builder.setConnectionRequestTimeout(timeout);
        }

        if ((timeout = customRequestConfig.getConnectTimeout()) > 0) {
            builder.setConnectTimeout(timeout);
        }

        if ((timeout = customRequestConfig.getSocketTimeout()) > 0) {
            builder.setSocketTimeout(timeout);
        }

        if ((proxy = customRequestConfig.getProxy()) != null) {
            builder.setProxy(proxy);
        }

        requestBase.setConfig(builder.build());

        // Merge Header
        for (Map.Entry<String, Object> entry : customRequestConfig.getHeaders().entrySet()) {
            requestBase.addHeader(entry.getKey(), writeValueAsString(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Create Get Http
     */
    public static HttpGet createGet(String url) {
        return new HttpGet(url);
    }

    /**
     * Create application/x-www-form-urlencoded for Get
     *
     * @param keyValChain key=val data format
     */
    public static HttpGet createGet(String url, String keyValChain) {

        if (url.contains("?")) {
            if (url.charAt(url.length() - 1) != '&') {
                url = url + "&";
            }
        } else {
            url = url + "?";
        }

        return new HttpGet(url + keyValChain);
    }

    public static HttpGet createGet(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return new HttpGet(url);
        }


        try {
            String keyValChain = EntityUtils.toString(new UrlEncodedFormEntity(initParams(params), StandardCharsets.UTF_8));
            return createGet(url, keyValChain);
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }


    /**
     * Create application/x-www-form-urlencoded for post
     *
     * @param keyValChain Key=val data format, multi data separated by &
     */
    public static HttpPost createPostForm(String url, String keyValChain) {
        return createPostEntity(url, keyValChain, ContentType.APPLICATION_FORM_URLENCODED);
    }

    public static HttpPost createPostForm(String url, Map<String, Object> requestBody) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(initParams(requestBody), StandardCharsets.UTF_8));
        return httpPost;
    }

    /**
     * Create application/json for post
     *
     * @param requestJsonBody json data format
     */
    public static HttpPost createPostJson(String url, String requestJsonBody) {
        return createPostEntity(url, requestJsonBody, ContentType.APPLICATION_JSON);
    }

    public static HttpPost createPostJson(String url, Object requestBody) {
        return createPostEntity(url, writeObjAsJson(requestBody), ContentType.APPLICATION_JSON);
    }

    /**
     * Create application/xml for post
     *
     * @param requestXmlBody xml data format
     */
    public static HttpPost createPostXml(String url, String requestXmlBody) {
        return createPostEntity(url, requestXmlBody, ContentType.APPLICATION_XML);
    }

    public static HttpPost createPostXml(String url, Object requestBody) {
        return createPostEntity(url, writeObjAsXml(requestBody), ContentType.APPLICATION_XML);
    }

    /**
     * create post entity
     *
     * @param body        Any data format
     * @param contentType Specifies the body data content-type
     */
    public static HttpPost createPostEntity(String url, String body, ContentType contentType) {

        StringEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
        entity.setContentType(contentType.getMimeType());

        return createPostEntity(url, entity);
    }

    /**
     * create post entity
     */
    public static HttpPost createPostEntity(String url, AbstractHttpEntity httpEntity) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(httpEntity);
        return httpPost;
    }

    private static List<NameValuePair> initParams(Map<String, Object> params) {

        List<NameValuePair> pairList = new ArrayList<>();
        if (params == null) {
            return pairList;
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {

            pairList.add(new BasicNameValuePair(entry.getKey(), writeValueAsString(entry.getKey(), entry.getValue())));

        }

        return pairList;
    }


    /**
     * write {@code value} as string
     */
    private static String writeValueAsString(String key, Object value) {
        try {
            ObjectMapper objectMapper = JacksonUtil.createObjectMapper();
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new HttpException(String.format("Cannot write http request key [%s] to string, It is a binary data?", key), e);
        }
    }

    /**
     * write {@code requestBody} to json
     */
    private static String writeObjAsJson(Object requestBody) {
        try {
            ObjectMapper objectMapper = JacksonUtil.createObjectMapper();
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new HttpException(String.format("Cannot write request body [%s] to json, It is a binary data?", requestBody.getClass().getSimpleName()), e);
        }
    }

    /**
     * write {@code requestBody} to xml
     */
    private static String writeObjAsXml(Object requestBody) {
        try {
            XmlMapper xmlMapper = JacksonUtil.createXmlMapper();
            return xmlMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new HttpException(String.format("Cannot write request body [%s] to xml, It is a binary data?", requestBody.getClass().getSimpleName()), e);
        }
    }
}
