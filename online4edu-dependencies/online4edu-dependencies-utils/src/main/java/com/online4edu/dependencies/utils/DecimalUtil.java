package com.online4edu.dependencies.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;

public class DecimalUtil {

    public static BigDecimal[] weightsApportion(BigDecimal pie, BigDecimal[] proportions) {
        return weightsApportion(pie, proportions, true);
    }

    public static BigDecimal[] weightsApportion(BigDecimal pie, BigDecimal[] proportions, boolean retainDecimal) {

        assert pie.compareTo(BigDecimal.ZERO) >= 0 && proportions != null && proportions.length > 0;

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
    public static BigDecimal add(BigDecimal... numerical) {
        return Arrays.stream(numerical).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Subtract multiple numbers.
     *
     * @param minuend     If the value is NULL it will be filled with ZERO.
     * @param subtraction The number we need to subtract
     * @return VAL
     */
    public static BigDecimal subtract(BigDecimal minuend, BigDecimal... subtraction) {
        BigDecimal val = Optional.ofNullable(minuend).orElse(BigDecimal.ZERO);
        return val.subtract(add(subtraction));
    }

    public static BigDecimal multiply(BigDecimal... decimals) {
        return Arrays.stream(decimals).reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    public static BigDecimal multiply(int scale, BigDecimal... decimals) {
        return multiply(decimals).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(int scale, RoundingMode mode, BigDecimal... decimals) {
        return multiply(decimals).setScale(scale, mode);
    }


    public static void main(String[] args) {
    }
}
