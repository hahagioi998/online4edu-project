package com.online4edu.dependencies.utils.result;

/**
 * 响应码接口
 *
 * <p>如何使用?
 * 定义一个枚举类实现该接口, 并重新 {@link #code()} 和 {@link #message()} 方法.
 *
 * 示例:
 *
 * {@code
 * public enum ExampleErrorCode implements ErrorCode {
 *
 *     // 根据各自服务业务需求在这里定义扩展响应码
 *
 *     // 如用户系统, 用户密码错误
 *     USER_OR_PASSWORD_ERROR(10001, "用户名或密码错误"),
 *
 *     // other
 *     ;
 *
 *     // 下面的代码不需要变化, 直接拷贝即可
 *
 *     private final int code;
 *
 *     private final String message;
 *
 *     ExampleErrorCode(int code, String message) {
 *         this.code = code;
 *         this.message = message;
 *     }
 *
 *     @Override
 *     public int code() {
 *         return code;
 *     }
 *
 *     @Override
 *     public String message() {
 *         return message;
 *     }
 * }
 * }
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/04/23 15:56
 * @see BasicErrorCode
 */
public interface ErrorCode {

    /**
     * 响应码
     *
     * @return 错误码
     */
    int code();

    /**
     * 响应信息
     *
     * @return 错误信息
     */
    String message();
}
