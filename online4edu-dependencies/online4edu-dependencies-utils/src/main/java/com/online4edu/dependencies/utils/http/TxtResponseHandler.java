package com.online4edu.dependencies.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 响应消息作为 TXT 处理
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:42
 */
class TxtResponseHandler extends AbstractResponseHandler<String> {

    @Override
    public String handleEntity(HttpEntity entity) throws IOException {

        try {
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } finally {
            EntityUtils.consume(entity);
        }
    }
}
