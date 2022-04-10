package com.online4edu.dependencies.utils.math;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Decimal Util
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2022/04/10 15:49
 */
public class DecimalUtil {

    public static BigDecimal[] weightsApportion(@Nonnull BigDecimal pie, @Nonnull BigDecimal[] proportions) {
        return weightsApportion(pie, proportions, true);
    }

    public static BigDecimal[] weightsApportion(@Nonnull BigDecimal pie, @Nonnull BigDecimal[] proportions, boolean retainDecimal) {

        assert pie.compareTo(BigDecimal.ZERO) >= 0 && proportions.length > 0;

        // DEFAULT: 保留两位小数
        int scale = retainDecimal ? 2 : 0;
        BigDecimal[] weights = new BigDecimal[proportions.length];

        if (pie.compareTo(BigDecimal.ZERO) == 0) {
            Arrays.fill(weights, BigDecimal.ZERO);
            return weights;
        }

        BigDecimal totalProportion = Arrays.stream(proportions).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalProportion.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("分摊权重未设置");
        }

        BigDecimal allocated = BigDecimal.ZERO;

        int lastNotZeroIndex = -1;
        for (int i = 0; i < proportions.length - 1; i++) {


            weights[i] = proportions[i]
                    .divide(totalProportion, MathContext.DECIMAL32) // 保留7位小数
                    .multiply(pie)
                    .setScale(scale, RoundingMode.HALF_UP); // 保留 scale 小数

            allocated = allocated.add(weights[i]);

            if (proportions[i].compareTo(BigDecimal.ZERO) > 0) {
                lastNotZeroIndex = i;
            }
        }

        BigDecimal remain = pie.subtract(allocated);

        // If the last participant is NULL or ZERO, the remaining portion
        // should be allocated to the last non-null or non-zero participant.
        // Otherwise, the remainder is allocated to the last participant.

        if (proportions[proportions.length - 1].compareTo(BigDecimal.ZERO) == 0) {
            weights[lastNotZeroIndex] = weights[lastNotZeroIndex].add(remain);
            weights[proportions.length - 1] = BigDecimal.ZERO;
        } else {
            weights[proportions.length - 1] = remain;
        }

        return weights;
    }


    /**
     * Adding multiple numbers, if {@code numerical} is NULL it will return ZERO
     *
     * @param numerical Multiple added values
     * @return VAL or ZERO
     */
    public static BigDecimal add(@Nonnull BigDecimal... numerical) {
        return Arrays.stream(numerical).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Subtract multiple numbers.
     *
     * @param minuend     If the value is NULL it will be filled with ZERO.
     * @param subtraction The number we need to subtract
     * @return VAL
     */
    public static BigDecimal subtract(@Nullable BigDecimal minuend, @Nonnull BigDecimal... subtraction) {
        BigDecimal val = Optional.ofNullable(minuend).orElse(BigDecimal.ZERO);
        return val.subtract(add(subtraction));
    }

    public static BigDecimal multiply(@Nonnull BigDecimal... decimals) {
        return Arrays.stream(decimals).reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    public static BigDecimal multiply(int scale, @Nonnull BigDecimal... decimals) {
        return multiply(decimals).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(int scale, @Nonnull RoundingMode mode, @Nonnull BigDecimal... decimals) {
        return multiply(decimals).setScale(scale, mode);
    }

    /**
     * 多个数相除, 使用 IEEE 754R 规范(默认保留6位小数)
     *
     * <p>排除 NULL 值</p>
     *
     * @see MathContext#DECIMAL32
     */
    public static BigDecimal divide(@Nonnull BigDecimal... decimals) {
        // Use: IEEE 754R
        return Arrays.stream(decimals).filter(Objects::nonNull).reduce((b1, b2) -> b1.divide(b2, MathContext.DECIMAL32)).orElse(BigDecimal.ZERO);
    }

    /**
     * 多个数相除, 使用 IEEE 754R 规范.
     * <p>
     * 采用四舍五入取舍模式并保留指定位 {@code scale} 小数
     *
     * <p>排除 NULL 值</p>
     *
     * @see MathContext#DECIMAL32
     * @see RoundingMode#HALF_UP
     */
    public static BigDecimal divide(int scale, @Nonnull BigDecimal... decimals) {

        // Use: IEEE 754R
        BigDecimal val = divide(decimals);

        if (val.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        return val.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 多个数相除, 使用 IEEE 754R 规范.
     * <p>
     * 指定取舍模式并保留指定位 {@code scale} 小数
     *
     * <p>排除 NULL 值</p>
     *
     * @see MathContext#DECIMAL32
     * @see RoundingMode
     */
    public static BigDecimal divide(int scale, @Nonnull RoundingMode mode, @Nonnull BigDecimal... decimals) {

        // Use: IEEE 754R
        BigDecimal val = divide(decimals);

        if (val.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        return val.setScale(scale, mode);
    }

    /**
     * 求百分比, 使用 IEEE 754R 规范.
     *
     * <p>
     * molecule/denominator * 100
     * </p>
     *
     * @param molecule    分子
     * @param denominator 分母
     * @return percentage 百分比(保留两位小数)
     */
    public static BigDecimal percentage(@Nonnull BigDecimal molecule, @Nonnull BigDecimal denominator) {

        // Use: IEEE 754R
        return molecule.divide(denominator, MathContext.DECIMAL32)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_DOWN);
    }

    /**
     * 带符号百分比, 如 33.33%
     */
    public static String percentageWithSuffix(@Nonnull BigDecimal molecule, @Nonnull BigDecimal denominator) {

        BigDecimal val = percentage(molecule, denominator);

        DecimalFormat format = new DecimalFormat("##.##");
        format.setPositiveSuffix("%");
        format.setNegativeSuffix("%");

        return format.format(val);
    }

    /**
     * 带符号金额
     * <p>
     * 如人民币 CNY1.00
     */
    public static String moneyWithCurrency(@Nonnull BigDecimal money) {
        return moneyWithCurrency(money, Locale.getDefault());
    }

    /**
     * 带符号金额(指定货币地区)
     */
    public static String moneyWithCurrency(@Nonnull BigDecimal money, @Nullable Locale locale) {

        if (locale == null) {
            locale = Locale.getDefault();
        }

        NumberFormat instance = DecimalFormat.getCurrencyInstance(locale);

        return instance.format(money);
    }

    /**
     * 格式化并指定数值前缀与后缀
     */
    public static String decimalFormat(@Nonnull BigDecimal decimal, @Nullable String prefix, @Nullable String suffix) {

        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setPositivePrefix(prefix);
        decimalFormat.setNegativePrefix(prefix);
        decimalFormat.setPositiveSuffix(suffix);
        decimalFormat.setNegativeSuffix(suffix);

        return decimalFormat.format(decimal);
    }


    public static void main(String[] args) {
        String multiply = moneyWithCurrency(new BigDecimal("1"), null);
        System.out.println(multiply);
    }
}
