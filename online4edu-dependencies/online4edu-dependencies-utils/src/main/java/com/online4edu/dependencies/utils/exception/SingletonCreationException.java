package com.online4edu.dependencies.utils.exception;

/**
 * 单例类创建异常
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/04/23 16:24
 */
public class SingletonCreationException extends ServiceException {

    public SingletonCreationException(String message) {
        super(message);
    }
}
