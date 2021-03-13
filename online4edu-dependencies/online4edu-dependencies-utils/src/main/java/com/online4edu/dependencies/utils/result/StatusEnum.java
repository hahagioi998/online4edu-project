package com.online4edu.dependencies.utils.result;

/**
 * 全局异常状态码枚举
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 17:22
 */
public enum StatusEnum {

    /**
     * 失败状态码
     */
    FAILURE(StatusCode.REQUEST_FAIL, "Failure"),

    /**
     * 成功状态码
     */
    SUCCESS(StatusCode.REQUEST_SUCCESS, "Success"),

    /**
     * 无效请求状态码
     */
    ILLEGAL_REQUEST(StatusCode.REQUEST_ILLEGAL, "无效的请求"),

    /**
     * 未登录状态码
     */
    NOT_LOGIN(StatusCode.REQUEST_NOT_LOGIN, "用户为扥估"),

    /**
     * 登录用户名或密码错误状态码
     */
    USER_OR_PASSWORD_ERROR(StatusCode.REQUEST_USER_OR_PASSWORD_ERR, "用户名或密码错误"),

    /**
     * 密码最大失败次数上限状态码
     */
    PASSWORD_ERROR_LIMIT(StatusCode.REQUEST_PASSWORD_ERROR_LIMIT, "密码错误次数达到上限"),

    /**
     * 参数不能为空状态码
     */
    RESULT_CODE_PARAMS_CANT_NOT_EMPTY(StatusCode.REQUEST_PARAMS_CANT_NOT_EMPTY, "请求参数缺失"),

    /**
     * 无操作权限状态码
     */
    NO_AUTHORIZED(StatusCode.REQUEST_NO_AUTHORIZED, "无操作权限"),


    /**
     * 500 错误状态码
     */
    INTERNAL_SERVER_ERR(StatusCode.REQUEST_INTERNAL_SERVER_ERR, "请求内部错误"),

    /**
     * 业务异常状态码
     */
    SERVICE_EXCEPTION(StatusCode.REQUEST_SERVICE_EXCEPTION, "业务异常"),

    ;

    private final Integer code;

    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public final Integer code() {
        return code;
    }

    public final String message() {
        return message;
    }
}