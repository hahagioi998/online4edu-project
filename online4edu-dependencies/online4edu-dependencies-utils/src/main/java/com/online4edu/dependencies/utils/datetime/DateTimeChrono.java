package com.online4edu.dependencies.utils.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.*;

/**
 * 日期类型 - 枚举类
 *
 * @author mingrn97 <br > mingrn97@gmail.com
 * @date 2021/01/31 15:43
 */
public enum DateTimeChrono {

    /** 年份 */
    YEAR("Year", "年份", new Chrono() {

        @Override
        protected Chrono computeInterval() {
            return computeInterval(LocalDate.now());
        }

        @Override
        protected Chrono computeInterval(Temporal benchmark) {
            compute(benchmark, YEAR);
            return this;
        }
    }),

    /** 季度 */
    QUARTER("Quarter", "季度", new Chrono() {

        @Override
        protected Chrono computeInterval() {
            return computeInterval(LocalDate.now());
        }

        @Override
        protected Chrono computeInterval(Temporal benchmark) {
            compute(benchmark, QUARTER);
            return this;
        }
    }),

    /** 月份 */
    MONTH("Month", "月份", new Chrono() {

        @Override
        protected Chrono computeInterval() {
            return computeInterval(LocalDate.now());
        }

        @Override
        protected Chrono computeInterval(Temporal benchmark) {
            compute(benchmark, MONTH);
            return this;
        }
    }),

    /** 周 */
    WEEK("Week", "周", new Chrono() {

        @Override
        protected Chrono computeInterval() {
            return computeInterval(LocalDate.now());
        }

        @Override
        protected Chrono computeInterval(Temporal benchmark) {
            compute(benchmark, WEEK);
            return this;
        }
    }),

    /** 天 */
    DAY("Day", "天", new Chrono() {

        @Override
        protected Chrono computeInterval() {
            return computeInterval(LocalDateTime.now());
        }

        @Override
        protected Chrono computeInterval(Temporal benchmark) {
            compute(benchmark, DAY);
            return this;
        }
    }),

    ;


    private final String type;
    private final String phrase;
    private final Chrono chrono;

    DateTimeChrono(String type, String phrase, Chrono chrono) {
        this.type = type;
        this.phrase = phrase;
        this.chrono = chrono;
    }

    public String type() {
        return type;
    }

    public String phrase() {
        return phrase;
    }

    public Chrono chrono() {
        return chrono;
    }

    public static DateTimeChrono resolve(String type) {
        for (DateTimeChrono dateTimeChrono : values()) {
            if (dateTimeChrono.type.equalsIgnoreCase(type)) {
                return dateTimeChrono;
            }
        }
        return null;
    }

    public static String value(String type) {
        DateTimeChrono dateTimeChrono = resolve(type);
        if (dateTimeChrono == null) {
            throw new IllegalArgumentException("No matching constant for [" + type + "]");
        }
        return dateTimeChrono.phrase;
    }

    // ======================== Chrono Compute ========================

    public abstract static class Chrono {

        /** 上周期开始与结束时间 */
        private String lastIntervalStart;
        private String lastIntervalEnd;

        /** 本周期开始与结束时间 */
        private String intervalStart;
        private String intervalEnd;

        public String lastIntervalStart() {
            return lastIntervalStart;
        }

        public String lastIntervalEnd() {
            return lastIntervalEnd;
        }

        public String intervalStart() {
            return intervalStart;
        }

        public String intervalEnd() {
            return intervalEnd;
        }

        /**
         * 周期计算
         *
         * @return this
         */
        protected abstract Chrono computeInterval();

        /**
         * 周期计算
         *
         * @param benchmark 指定基准时间
         * @return this
         */
        protected abstract Chrono computeInterval(Temporal benchmark);


        /**
         * 计算
         *
         * @param benchmark    指定基准时间
         * @param dateTimeChrono 计算日期类型
         */
        protected void compute(Temporal benchmark, DateTimeChrono dateTimeChrono) {

            LocalDate date;
            LocalDate last;
            switch (dateTimeChrono) {
                case YEAR:
                    date = convert2LocalDate(benchmark);

                    this.intervalStart = date.with(TemporalAdjusters.firstDayOfYear()).toString();
                    this.intervalEnd = date.with(TemporalAdjusters.firstDayOfNextYear()).toString();

                    last = date.minus(1, ChronoUnit.YEARS);
                    this.lastIntervalStart = last.with(TemporalAdjusters.firstDayOfYear()).toString();
                    this.lastIntervalEnd = this.intervalStart;

                    break;
                case QUARTER:
                    date = convert2LocalDate(benchmark);

                    // 当前季度第一个月第一天
                    Month qMonth = date.getMonth().firstMonthOfQuarter();
                    date = LocalDate.of(date.getYear(), qMonth, 1);

                    // 下季度第一天
                    LocalDate nextQ = date.plusMonths(3L);

                    // 上季度第一天
                    LocalDate lastQ = date.minusMonths(3L);

                    this.intervalStart = date.toString();
                    this.intervalEnd = nextQ.toString();

                    this.lastIntervalStart = lastQ.toString();
                    this.lastIntervalEnd = this.intervalStart;

                    break;
                case MONTH:
                    date = convert2LocalDate(benchmark);

                    // 本周期
                    this.intervalStart = date.with(TemporalAdjusters.firstDayOfMonth()).toString();
                    this.intervalEnd = date.with(TemporalAdjusters.firstDayOfNextMonth()).toString();

                    // 上周期
                    last = date.minusMonths(1L);
                    this.lastIntervalStart = last.with(TemporalAdjusters.firstDayOfMonth()).toString();
                    this.lastIntervalEnd = this.intervalStart;

                    break;
                case WEEK:
                    date = convert2LocalDate(benchmark);
                    TemporalField dayOfWeekField = WeekFields.of(DayOfWeek.MONDAY, 1).dayOfWeek();

                    // 本周
                    this.intervalStart = date.with(dayOfWeekField, 1).toString();
                    this.intervalEnd = date.plusWeeks(1L).with(dayOfWeekField, 1).toString();

                    // 上周
                    this.lastIntervalStart = date.minusWeeks(1L).with(dayOfWeekField, 1).toString();
                    this.lastIntervalEnd = this.intervalStart;

                    break;
                case DAY:
                    date = convert2LocalDate(benchmark);

                    // 当前天
                    this.intervalStart = date.toString();
                    this.intervalEnd = date.plusDays(1L).toString();

                    // 上一天
                    this.lastIntervalStart = date.minusDays(1L).toString();
                    this.lastIntervalEnd = this.intervalStart;

                    break;
                default:
                    throw new UnsupportedOperationException("日期类型错误");
            }
        }

        private static LocalDate convert2LocalDate(Temporal benchmark) {
            if (benchmark instanceof LocalDate) {
                return (LocalDate) benchmark;
            } else if (benchmark instanceof LocalDateTime) {
                LocalDateTime dateTime = (LocalDateTime) benchmark;
                return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
            } else {
                throw new UnsupportedOperationException("参数 java.time.temporal.Temporal " +
                        "只支持 java.time.LocalDate 以及 java.time.LocalDateTime");
            }
        }
    }
}
