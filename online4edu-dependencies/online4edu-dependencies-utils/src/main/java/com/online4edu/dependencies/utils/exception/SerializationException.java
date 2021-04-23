package com.online4edu.dependencies.utils.exception;

/**
 * 序列化异常
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/08 15:45
 */
public class SerializationException extends ServiceException {

    private static final long serialVersionUID = 6475435719302659535L;

    private static final String DEFAULT_MSG = "Serialize failed. ";

    private static final String MSG_FOR_SPECIFIED_CLASS = "Serialize for class [%s] failed. ";

    private Class<?> serializedClass;

    public SerializationException() {
        super();
    }

    public SerializationException(Class<?> serializedClass) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()));
        this.serializedClass = serializedClass;
    }

    public SerializationException(Throwable throwable) {
        super(DEFAULT_MSG, throwable);
    }

    public SerializationException(Class<?> serializedClass, Throwable throwable) {
        super(String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()), throwable);
        this.serializedClass = serializedClass;
    }

    public Class<?> getSerializedClass() {
        return serializedClass;
    }
}
