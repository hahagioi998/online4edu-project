package com.online4edu.dependencies.utils.http;

/**
 * 异常
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:43
 */
public class HttpException extends RuntimeException {

    public HttpException() {
        super();
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
