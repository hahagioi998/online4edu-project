package com.online4edu.dependencies.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

class CustomResponseHandler implements ResponseHandler<String> {

    private static final Logger logger = Logger.getLogger(CustomResponseHandler.class.getSimpleName());

    /**
     * Read the entity from the response body and pass it to the entity handler
     * method if the response was successful (a 2xx status code). If no response
     * body exists, this returns null. If the response was unsuccessful (&gt;= 300
     * status code), throws an {@link HttpResponseException}.
     */
    @Override
    public String handleResponse(final HttpResponse response)
            throws HttpResponseException, IOException {

        final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();


        if (statusLine.getStatusCode() == HttpStatus.SC_OK || statusLine.getStatusCode() == HttpStatus.SC_NO_CONTENT) {
            if (statusLine.getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                logger.log(Level.INFO, "Http Response Success, But Not Found Content.");
            }
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } else {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
    }

}
