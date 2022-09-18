package com.castillo.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtils {
    public static final String US_DATE_PATTERN = "MM/dd/yyyy";
    public static final String DR_DATE_PATTERN = "dd/MM/yyyy";
    public static final String US_DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm:ss";
    public static final String DR_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    public static String formatLocalDate(LocalDate date, String format){
        return getFormatter(format).format(date);
    }

    public static DateTimeFormatter getFormatter(String format){
        return DateTimeFormatter.ofPattern(format);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String format){
        return getFormatter(format).format(localDateTime);
    }

    public static LocalDate parseLocalDate(String str, String format){
        return LocalDate.parse(str, getFormatter(format));
    }

    public static LocalDateTime parseLocalDateTime(String str, String format){
        return LocalDateTime.parse(str, getFormatter(format));
    }
}
