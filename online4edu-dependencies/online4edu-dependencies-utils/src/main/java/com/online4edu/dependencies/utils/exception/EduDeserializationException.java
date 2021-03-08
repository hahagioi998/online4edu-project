package com.online4edu.dependencies.utils.exception;

import java.lang.reflect.Type;

/**
 * Nacos deserialization exception.
 *
 * @author yangyi
 */
public class EduDeserializationException extends EduRuntimeException {


    private static final long serialVersionUID = -6514424812979501022L;

    private static final String DEFAULT_MSG = "edu deserialize failed. ";

    private static final String MSG_FOR_SPECIFIED_CLASS = "edu deserialize for class [%s] failed. ";

    private Class<?> targetClass;
    
    public EduDeserializationException() {
        super();
    }
    
    public EduDeserializationException(Class<?> targetClass) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()));
        this.targetClass = targetClass;
    }
    
    public EduDeserializationException(Type targetType) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()));
    }
    
    public EduDeserializationException(Throwable throwable) {
        super(DEFAULT_MSG, throwable);
    }
    
    public EduDeserializationException(Class<?> targetClass, Throwable throwable) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()), throwable);
        this.targetClass = targetClass;
    }
    
    public EduDeserializationException(Type targetType, Throwable throwable) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()), throwable);
    }
    
    public Class<?> getTargetClass() {
        return targetClass;
    }
}