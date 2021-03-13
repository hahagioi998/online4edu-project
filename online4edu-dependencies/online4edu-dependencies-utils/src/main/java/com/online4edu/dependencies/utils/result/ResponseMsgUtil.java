package com.online4edu.dependencies.utils.result;

/**
 * 消息统一响应工具类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 17:20
 */
public class ResponseMsgUtil {

    private ResponseMsgUtil() {
    }

    /**
     * 统一返回结果
     */
    public static <T> Result<T> builderResponse(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }


    /**
     * 请求失败
     */
    public static <T> Result<T> failure() {
        return failure(StatusEnum.FAILURE.message());
    }

    /**
     * 请求失败
     */
    public static <T> Result<T> failure(String msg) {
        return builderResponse(StatusEnum.FAILURE.code(), msg, null);
    }


    /**
     * 请求成功
     */
    public static <T> Result<T> success() {
        return success(StatusEnum.SUCCESS.message(), null);
    }

    /**
     * 请求成功
     */
    public static <T> Result<T> success(String msg) {
        return success(msg, null);
    }

    /**
     * 请求成功
     */
    public static <T> Result<T> success(T data) {
        return success(StatusEnum.SUCCESS.message(), data);
    }

    /**
     * 请求成功
     */
    public static <T> Result<T> success(String msg, T data) {
        return builderResponse(StatusEnum.SUCCESS.code(), msg, data);
    }

    /**
     * 无效请求
     */
    public static <T> Result<T> illegalRequest() {
        return illegalRequest(StatusEnum.ILLEGAL_REQUEST.message());
    }

    /**
     * 无效请求
     */
    public static <T> Result<T> illegalRequest(String msg) {
        return builderResponse(StatusEnum.ILLEGAL_REQUEST.code(), msg, null);
    }

    /**
     * 未登录
     */
    public static <T> Result<T> notLogin() {
        return notLogin(StatusEnum.NOT_LOGIN.message());
    }

    /**
     * 未登录
     */
    public static <T> Result<T> notLogin(String meg) {
        return builderResponse(StatusEnum.NOT_LOGIN.code(), meg, null);
    }

    /**
     * 登录用户名或密码错误
     */
    public static <T> Result<T> loginUserOrPasswordError() {
        return builderResponse(StatusEnum.USER_OR_PASSWORD_ERROR.code(), StatusEnum.USER_OR_PASSWORD_ERROR.message(), null);
    }


    /**
     * 密码错误次数已达上限
     */
    public static <T> Result<T> passwordErrorLimit() {
        return builderResponse(StatusEnum.PASSWORD_ERROR_LIMIT.code(), StatusEnum.PASSWORD_ERROR_LIMIT.message(), null);
    }

    /**
     * 无操作权限
     */
    public static <T> Result<T> noAuthorized() {
        return noAuthorized(StatusEnum.NO_AUTHORIZED.message());
    }

    /**
     * 无操作权限
     */
    public static <T> Result<T> noAuthorized(String msg) {
        return builderResponse(StatusEnum.NO_AUTHORIZED.code(), msg, null);
    }

    /**
     * 500 错误
     */
    public static <T> Result<T> internalServerErr() {
        return internalServerErr(StatusEnum.INTERNAL_SERVER_ERR.message());
    }

    /**
     * 500 错误
     */
    public static <T> Result<T> internalServerErr(String msg) {
        return builderResponse(StatusEnum.INTERNAL_SERVER_ERR.code(), msg, null);
    }

    /**
     * 业务异常
     */
    public static <T> Result<T> serviceException() {
        return builderResponse(StatusEnum.SERVICE_EXCEPTION.code(), StatusEnum.SERVICE_EXCEPTION.message(), null);
    }
}