package com.online4edu.dependencies.utils.result;

/**
 * 基础响应码
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/04/23 15:58
 */
enum BasicErrorCode implements ErrorCode {

    /**
     * 失败状态码
     */
    FAILURE(-1, "Failure"),

    /**
     * 成功状态码
     */
    SUCCESS(0, "Success"),

    /**
     * 参数不能为空状态码
     */
    PARAMS_CANT_NOT_EMPTY(10, "请求参数缺失"),

    /**
     * 4xx 无效请求状态码
     */
    ILLEGAL_REQUEST(100, "无效的请求"),

    /**
     * 5xx 错误状态码
     */
    INTERNAL_SERVER_ERR(101, "请求内部错误"),

    /**
     * 业务异常状态码
     */
    SERVICE_EXCEPTION(1000, "业务异常"),

    /**
     * 无操作权限状态码
     */
    NO_AUTHORIZED(1001, "无操作权限"),
    ;


    private final int code;

    private final String message;

    BasicErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
