package com.online4edu.dependencies.utils.random;

import com.online4edu.dependencies.utils.Assert;
import org.apache.commons.text.RandomStringGenerator;

/**
 * 随机字符/数生成工具类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2022/01/31 20:55
 * @see RandomStringGenerator
 * @see org.apache.commons.lang3.RandomStringUtils
 */
public final class RandomUtil {

    private RandomUtil() {
    }

    public static String randomNumeric() {
        return randomNumeric(1);
    }

    /**
     * 随机生成指定长度字符串(数字)
     *
     * @param length 指定长度
     * @return 随机字符
     */
    public static String randomNumeric(final int length) {
        Assert.isTrue(length > 0, "'length' less than or equal to zero");
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '9').build();

        return generator.generate(length);
    }

    public static String randomAlphabetic() {
        return randomAlphabetic(1);
    }

    /**
     * 随机生成指定长度字符串(字母)
     *
     * @param length 指定长度
     * @return 随机字符
     */
    public static String randomAlphabetic(final int length) {
        Assert.isTrue(length > 0, "'length' less than or equal to zero");
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'a', 'z'}, {'A', 'Z'}})
                .build();

        return generator.generate(length);
    }

    public static String randomAlphanumeric() {
        return randomAlphanumeric(1);
    }

    /**
     * 随机生成指定长度字符串(字母/数字)
     *
     * @param length 指定长度
     * @return 随机字符
     */
    public static String randomAlphanumeric(final int length) {
        Assert.isTrue(length > 0, "'length' less than or equal to zero");
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(new char[][]{{'0', '9'}, {'a', 'z'}, {'A', 'Z'}})
                .build();

        return generator.generate(length);
    }

    public static String random() {
        return random(1);
    }

    /**
     * 随机生成指定长度字符串(字母/数字/符号)
     *
     * @param length 指定长度
     * @return 随机字符
     */
    public static String random(final int length) {
        Assert.isTrue(length > 0, "'length' less than or equal to zero");
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(33, 126) // ASCII
                .build();

        return generator.generate(length);
    }

    /**
     * 指定字符, 随机生成指定长度字符串
     *
     * @param length 指定长度
     * @param chars  自定义字符
     * @return 随机字符
     */
    public static String random(final int length, final char... chars) {
        Assert.isTrue(length > 0, "'length' less than or equal to zero");
        Assert.isTrue(chars != null && chars.length > 0, "The specified random character is empty");
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .selectFrom(chars)
                .build();

        return generator.generate(length);
    }
}
