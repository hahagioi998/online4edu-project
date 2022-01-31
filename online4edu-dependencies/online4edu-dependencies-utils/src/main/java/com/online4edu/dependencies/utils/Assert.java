package com.online4edu.dependencies.utils;

/**
 * 断言
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2022/01/31 21:24
 */
public class Assert {

    public static void isTrue(boolean condition) {
        isTrue(condition, "");
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
