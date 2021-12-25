package com.online4edu.dependencies.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Different from {@link DownloadResponseHandler}, this class is used to the
 * corresponding message flow into a text string. Such as the clear know
 * response message for a JSON string or HTML, XML data.
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:42
 */
class PlainTxtResponseHandler extends AbstractResponseHandler<String> {

    @Override
    public String handleEntity(HttpEntity entity) throws IOException {

        try {
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } finally {
            EntityUtils.consume(entity);
        }
    }
}
