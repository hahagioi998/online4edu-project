package com.online4edu.dependencies.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * HTTP 响应消息作为 IO 处理
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:42
 */
class InputStreamResponseHandler extends AbstractResponseHandler<InputStream> {

    @Override
    public InputStream handleEntity(HttpEntity entity) throws IOException {

        try {
            return entity.getContent();
        } finally {
            EntityUtils.consume(entity);
        }
    }
}
