package com.online4edu.dependencies.utils.exception;

/**
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/08 15:45
 */
public class EduSerializationException extends EduRuntimeException {

    private static final long serialVersionUID = 6475435719302659535L;

    private static final String DEFAULT_MSG = "Edu Serialize failed. ";

    private static final String MSG_FOR_SPECIFIED_CLASS = "Edu serialize for class [%s] failed. ";

    private Class<?> serializedClass;
    
    public EduSerializationException() {
        super();
    }
    
    public EduSerializationException(Class<?> serializedClass) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()));
        this.serializedClass = serializedClass;
    }
    
    public EduSerializationException(Throwable throwable) {
        super(DEFAULT_MSG, throwable);
    }
    
    public EduSerializationException(Class<?> serializedClass, Throwable throwable) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()), throwable);
        this.serializedClass = serializedClass;
    }
    
    public Class<?> getSerializedClass() {
        return serializedClass;
    }
}