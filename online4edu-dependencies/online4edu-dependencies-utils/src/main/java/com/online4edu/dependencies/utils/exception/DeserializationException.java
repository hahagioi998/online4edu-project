package com.online4edu.dependencies.utils.exception;

import java.lang.reflect.Type;

/**
 * 反序列化异常
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/04/23 16:21
 */
public class DeserializationException extends ServiceException {


    private static final long serialVersionUID = -6514424812979501022L;

    private static final String DEFAULT_MSG = "edu deserialize failed. ";

    private static final String MSG_FOR_SPECIFIED_CLASS = "edu deserialize for class [%s] failed. ";

    private Class<?> targetClass;

    public DeserializationException() {
        super();
    }

    public DeserializationException(Class<?> targetClass) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()));
        this.targetClass = targetClass;
    }

    public DeserializationException(Type targetType) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()));
    }

    public DeserializationException(Throwable throwable) {
        super(DEFAULT_MSG, throwable);
    }

    public DeserializationException(Class<?> targetClass, Throwable throwable) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()), throwable);
        this.targetClass = targetClass;
    }

    public DeserializationException(Type targetType, Throwable throwable) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()), throwable);
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}
