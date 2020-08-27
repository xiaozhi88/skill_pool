package com.xiaozhi.office.common;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 处理时间工具类
 *
 * @author SunMing
 * @date 20200331
 * @since v1.0
 */
@Log4j2
public class LocalDateTimeUtils {

    private LocalDateTimeUtils() {
    }

    public static final String DATE_TIME_ZERO = " 00:00:00";
    public static final String DATE_TIME_0 = "000000";
    public static final String DATE_TIME_01 = "yyyy-MM-dd";
    public static final String DATE_TIME_03 = "yyyyMMddHHmmss";
    public static final String DATE_TIME_02 = "yyyyMMdd";
    public static final String DATE_TIME_04 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_05 = "yyyyMM";
    public static final String DATE_TIME_06 = "MMdd";
    public static final String DATE_TIME_07 = "MM-dd";
    public static final String DATE_TIME_08 = "HH:mm:ss";
    public static final String DATE_TIME_09 = "yyyy-MM";
    public static final String DATE_TIME_10 = "yyyy/MM/dd";
    public static final String DATE_TIME_11 = "yyyy年MM月dd日";
    public static final String DATE_TIME_12 = "yyyy年MM月";
    public static final String FIRST_MONTH_DAY_OF_YEAR = "0101";
    public static final String LAST_MONTH_DAY_OF_YEAR = "1231";

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy");

    public static SimpleDateFormat YYMMDD_FORMAT = new SimpleDateFormat(DATE_TIME_01);

    public static SimpleDateFormat YYMMDD_FORMAT_2 = new SimpleDateFormat(DATE_TIME_02);

    public static SimpleDateFormat YYMM_FORMAT = new SimpleDateFormat(DATE_TIME_05);

    /**
     * 检查是否为2月29日
     *
     * @param forecastTime 预报时间
     * @return 返回 boolean 描述此返回参数
     * @author LeoRmAo
     * @date 20190821 17:32:34
     * @since v1.0
     */
    public static boolean check229(LocalDateTime forecastTime) {
        return forecastTime.getMonthValue() == 2 && forecastTime.getDayOfMonth() == 29;
    }

    /**
     * localDateTime转换为date
     *
     * @param localDateTime 描述此参数
     * @return 返回 date 描述此返回参数
     * @author LeoRmAo
     * @date 20180920 11:23:40
     * @since v1.0
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate转换为Date
     *
     * @param localDate 描述此参数
     * @return 返回 date 描述此返回参数
     * @author sunMing
     * @date 20190726
     * @since v1.0
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String localDateToString(LocalDate localDate, String dateTimeFormatterStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatterStr);
        return dateTimeFormatter.format(localDate);
    }

    /**
     * 将date转为localDateTime
     *
     * @param date 描述此参数
     * @return 返回 local date time 描述此返回参数
     * @author LeoRmAo
     * @date 20181115 10:47:52
     * @since v1.0
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime
                .ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * str转LocalDate,指定日期格式
     *
     * @param localDateStr 日期字符串
     * @param formatter    指定日期格式
     * @author zhongyong
     * @since v1.0
     */
    public static LocalDate parseStringToLocalDate(String localDateStr, String formatter) {
        return LocalDate.parse(localDateStr, DateTimeFormatter.ofPattern(formatter));
    }

    public static Date parseString(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            log.warn("invalid data,dateStr={}", dateStr);
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            log.warn("invalid format,format={}", format);
            return null;
        }
        DateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(dateStr);
            if (!dateStr.equals(df.format(date))) {
                date = null;
            }
        } catch (ParseException e) {
            log.error("fail to parse date", e);
        }
        return date;
    }


    /**
     * 时间转为字符串
     *
     * @param localDateTime     描述此参数
     * @param dateTimeFormatter 描述此参数
     * @return 返回 string 描述此返回参数
     * @author LeoRmAo
     * @date 20190905 19:21:29
     * @since v1.0
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 获取月日目录名，例如0101
     *
     * @param localDateTime 描述此参数
     * @return 返回 month day directory name
     * @author LeoRmAo
     * @date 20181115 10:57:28
     * @since v1.0
     */
    public static String getMonthDayDirectoryName(LocalDateTime localDateTime) {
        return monthDaySupplementZeroLessThanTen(localDateTime.getMonth().getValue()) +
                monthDaySupplementZeroLessThanTen(localDateTime.getDayOfMonth());
    }

    /**
     * 对小于10的数字进行补0，主要用于月和日的目录名获取
     *
     * @param number 描述此参数
     * @return 返回 string 描述此返回参数
     * @author LeoRmAo
     * @date 20181115 10:56:26
     * @since v1.0
     */
    public static String monthDaySupplementZeroLessThanTen(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }

    /**
     * String转localDateTime
     *
     * @return 返回 local date time 描述此返回参数
     * @author 周恒
     * @date 20181122 10:39:01
     * @since v1.0
     */
    public static LocalDateTime stringToLocalDateTime(String timeStr, String dtfStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dtfStr);
        return LocalDateTime.parse(timeStr, dateTimeFormatter);
    }

    /**
     * String转localDateTime
     *
     * @return 返回 local date time 描述此返回参数
     * @author 周恒
     * @date 20181122 10:39:01
     * @since v1.0
     */
    public static LocalDate stringToLocalDate(String timeStr, String dtfStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dtfStr);
        return LocalDate.parse(timeStr, dateTimeFormatter);
    }

    /**
     * localDateTime转String
     *
     * @return 返回 string 描述此返回参数
     * @author 周恒
     * @date 20181122 10:42:25
     * @since v1.0
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String dateTimeFormatterStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatterStr);
        return dateTimeFormatter.format(localDateTime);
    }


    /**
     * 将传入的时间转为周一
     *
     * @return 返回 local date time 描述此返回参数
     * @author LeoRmAo
     * @date 20181129 17:57:58
     * @since v1.0
     */
    public static LocalDateTime getMonday(LocalDateTime localDateTime) {
        int value = localDateTime.getDayOfWeek().getValue();
        return localDateTime.minusDays(value - 1);
    }


    /**
     * 传入的日期，返回MMdd字符串
     *
     * @author 周恒
     * @date 20181130 15:28:43
     * @since v1.0
     */
    public static String monthAndDaySupplementZero(LocalDateTime localDateTime) {
        return monthDaySupplementZeroLessThanTen(localDateTime.getMonthValue())
                + monthDaySupplementZeroLessThanTen(localDateTime.getDayOfMonth());
    }


    /**
     * 获取当月最后一天的日期字符串
     *
     * @author 周恒
     * @date 20190321 14:05:33
     * @since v1.0
     */
    public static String getMonthLastDayNumber(LocalDateTime localDateTime) {
        return monthDaySupplementZeroLessThanTen(localDateTime.plusMonths(1).withDayOfMonth(1).minusDays(1).getDayOfMonth());
    }

    /**
     * 检验是否为这个月的最后一天
     *
     * @return 返回 boolean 描述此返回参数
     * @author 周恒
     * @date 20190510 10:32:31
     * @since v1.0
     */
    public static boolean isLastDayOfTheMonth(LocalDateTime localDateTime) {
        int month = localDateTime.getMonthValue();
        return month != localDateTime.plusDays(1).getMonthValue();
    }


    /**
     * 开始时间小于或等于 结束时间
     *
     * @param start 描述此参数
     * @param end   描述此参数
     * @return 返回 boolean 描述此返回参数
     * @author 周恒
     * @date 20181105 11:00:15
     * @since v1.0
     */
    public static boolean lessThanOrEqualTime(LocalDateTime start, LocalDateTime end) {
        if (start.isEqual(end)) {
            return true;
        }
        return end.isAfter(start);
    }


    /**
     * 获取两个时间之间天数的差异
     *
     * @param observeTime  起报时间
     * @param forecastTime 描述此参数
     * @return 返回 data forecast index
     * @author LeoRmAo
     * @date 20190627 11:15:57
     * @since v1.0
     */
    public static long getDaysBetween(LocalDateTime observeTime, LocalDateTime forecastTime) {
        //将年份调至1983年防止闰年索引问题
        int leapYear = forecastTime.getYear() - observeTime.getYear();
        return forecastTime.withYear(SystemConstant.INDEX_DATE_CONVERTER_YEAR + leapYear).toLocalDate().toEpochDay() -
                observeTime.withYear(SystemConstant.INDEX_DATE_CONVERTER_YEAR).toLocalDate().toEpochDay();
    }

    /**
     * 获取两个时间之间月数的差异
     *
     * @param observeTime  描述此参数
     * @param forecastTime 描述此参数
     * @return 返回 int 描述此返回参数
     * @author LeoRmAo
     * @date 20190715 14:05:34
     * @since v1.0
     */
    public static int getMonthBetween(LocalDateTime observeTime, LocalDateTime forecastTime) {
        //起报时间大于预报时间，抛出异常
        if (observeTime.isAfter(forecastTime)) {
            throw new BaseException("开始时间，大于结束时间");
        }
        int startYear = observeTime.getYear();
        int endYear = forecastTime.getYear();
        int startMonth = observeTime.getMonthValue();
        int endMonth = forecastTime.getMonthValue();
        if (startYear == endYear) {
            return endMonth - startMonth;
        } else {
            return (endYear - startYear - 1) * 12 + (12 - startMonth) + endMonth;
        }
    }

    /**
     * 获取日期范围内所有的日期，包括开始日期和结束日期
     *
     * @param startDate 描述此参数
     * @param endDate   描述此参数
     * @return 返回 local date between
     * @author sunMing
     * @date 20190731
     * @since v1.0
     */
    public static List<LocalDateTime> getLocalDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                .collect(Collectors.toList());
    }

    /**
     * 获取日期范围内所有的日期，包括开始日期和结束日期
     *
     * @param startDate 描述此参数
     * @param endDate   描述此参数
     * @return 返回 local date between
     * @author sunMing
     * @date 20190731
     * @since v1.0
     */
    public static List<LocalDateTime> getMonthLyLocalDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return Stream.iterate(startDate, date -> date.plusMonths(1))
                .limit(ChronoUnit.MONTHS.between(startDate, endDate) + 1)
                .collect(Collectors.toList());
    }

    /**
     * 检验是否为这个月的第一天
     *
     * @param localDateTime 描述此参数
     * @return 返回 boolean 描述此返回参数
     * @author zhangjc
     * @date 20191108
     * @since v1.0
     */
    public static boolean isFirstDayOfTheMonth(LocalDateTime localDateTime) {
        int month = localDateTime.getMonthValue();
        return month != localDateTime.minusDays(1).getMonthValue();
    }

    /**
     * 年月日转LocalDateTime
     *
     * @param year  描述此参数
     * @param month 描述此参数
     * @param day   描述此参数
     * @return 返回 local date time 描述此返回参数
     * @author SunMing
     * @date 20200331
     * @since v1.0
     */
    public static LocalDateTime yearMonthDayToLocalDateTime(String year, String month, String day) {
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0, 0);
    }

    /**
     * 判断预报时间是否比最新观测时间早
     *
     * @param forecastDate 描述此参数
     * @param observeDate  描述此参数
     * @return 返回 boolean 描述此返回参数
     * @author sunMing
     * @date 20191227
     * @since v1.0
     */
    public static boolean isForecastDateBeforeObserveDate(LocalDateTime forecastDate, LocalDateTime observeDate) {
        return observeDate != null && forecastDate.isBefore(observeDate);
    }
}
