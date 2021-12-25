package com.online4edu.dependencies.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * File download response handler
 * <p>
 * This class is used only for file download, Because the class will be in the
 * internal processing IO flow, not the data output to the outside. So if you
 * want the I/O output to the specified location can use {@link OutputStream}
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:42
 */
class DownloadResponseHandler extends AbstractResponseHandler<Void> {

    private final OutputStream outputStream;

    public DownloadResponseHandler(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public Void handleEntity(HttpEntity entity) throws IOException {

        try {
            final InputStream in = entity.getContent();
            if (in.available() > 0) {
                doWrite(in, outputStream);
            }
        } finally {
            EntityUtils.consume(entity);
        }
        return null;
    }

    static void doWrite(InputStream in, OutputStream out) {
        try {
            int length;
            byte[] tmp = new byte[1024];
            while ((length = in.read(tmp)) != -1) {
                out.write(tmp, 0, length);
            }
            out.flush();
        } catch (IOException e) {
            throw new HttpException("write file exception", e);
        }
    }
}
