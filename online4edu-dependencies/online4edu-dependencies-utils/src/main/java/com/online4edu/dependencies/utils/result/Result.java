package com.online4edu.dependencies.utils.result;

import com.online4edu.dependencies.utils.jackson.JacksonUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 统一返回结果类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 17:17
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Result<T> {

    /** 响应码 */
    private Integer code;

    /** 响应信息 */
    private String message;

    /**
     * 数据
     */
    private T data;

    public Result() {
    }

    public String toJson() {
        return JacksonUtils.toJson(this);
    }
}
