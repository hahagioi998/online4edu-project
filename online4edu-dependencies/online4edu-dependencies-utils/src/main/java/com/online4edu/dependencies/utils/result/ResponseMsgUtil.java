package com.online4edu.dependencies.utils.result;

import com.online4edu.dependencies.utils.exception.SingletonCreationException;

/**
 * 消息统一响应工具类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 17:20
 */
public class ResponseMsgUtil {

    private ResponseMsgUtil() {
        throw new SingletonCreationException("消息统一响应工具类[ResponseMsgUtil]不允许实例化");
    }

    /**
     * 统一返回结果
     */
    private static <T> Result<T> builderResponse(int code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }


    /**
     * 请求失败
     */
    public static <T> Result<T> failure() {
        return failure(BasicErrorCode.FAILURE);
    }

    public static <T> Result<T> failure(String message) {
        return builderResponse(BasicErrorCode.FAILURE.code(), message, null);
    }

    /**
     * 扩展使用
     */
    public static <T> Result<T> failure(ErrorCode errorCode) {
        return builderResponse(errorCode.code(), errorCode.message(), null);
    }


    /**
     * 请求成功
     */
    public static <T> Result<T> success() {
        return success(BasicErrorCode.SUCCESS.message());
    }

    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    public static <T> Result<T> success(T data) {
        return success(BasicErrorCode.SUCCESS.message(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return builderResponse(BasicErrorCode.SUCCESS.code(), message, data);
    }


    /**
     * 请求参数不能为空
     * PARAMS_CANT_NOT_EMPTY
     */
    public static <T> Result<T> paramsCantNotEmpty() {
        return paramsCantNotEmpty(BasicErrorCode.PARAMS_CANT_NOT_EMPTY.message());
    }

    public static <T> Result<T> paramsCantNotEmpty(String message) {
        return builderResponse(BasicErrorCode.PARAMS_CANT_NOT_EMPTY.code(), message, null);
    }


    /**
     * 4xx 无效请求
     */
    public static <T> Result<T> illegalRequest() {
        return illegalRequest(BasicErrorCode.ILLEGAL_REQUEST.message());
    }

    public static <T> Result<T> illegalRequest(String message) {
        return builderResponse(BasicErrorCode.ILLEGAL_REQUEST.code(), message, null);
    }


    /**
     * 5xx 错误
     */
    public static <T> Result<T> internalServerErr() {
        return internalServerErr(BasicErrorCode.INTERNAL_SERVER_ERR.message());
    }

    public static <T> Result<T> internalServerErr(String message) {
        return builderResponse(BasicErrorCode.INTERNAL_SERVER_ERR.code(), message, null);
    }


    /**
     * 业务异常
     */
    public static <T> Result<T> serviceException() {
        return serviceException(BasicErrorCode.SERVICE_EXCEPTION.message());
    }

    public static <T> Result<T> serviceException(String message) {
        return builderResponse(BasicErrorCode.SERVICE_EXCEPTION.code(), message, null);
    }


    /**
     * 无操作权限
     */
    public static <T> Result<T> noAuthorized() {
        return serviceException(BasicErrorCode.NO_AUTHORIZED.message());
    }

    public static <T> Result<T> noAuthorized(String message) {
        return builderResponse(BasicErrorCode.NO_AUTHORIZED.code(), message, null);
    }
}
