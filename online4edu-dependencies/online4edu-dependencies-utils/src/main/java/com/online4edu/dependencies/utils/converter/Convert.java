package com.online4edu.dependencies.utils.converter;

import java.io.Serializable;

/**
 * 普通实体父类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/07 17:14
 */
public class Convert implements Serializable {

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz 转换承目标类型
     * @return 目标类型 T
     */
    public <T> T convert(Class<T> clazz) {
        return BeanConverter.convert(clazz, this);
    }
}
