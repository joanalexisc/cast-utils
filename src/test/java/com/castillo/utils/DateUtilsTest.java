package com.castillo.utils;

import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateUtilsTest {

    @Test
    void formatLocalDate() {
        LocalDate date = LocalDate.of(1988, Month.AUGUST, 17);
        String formattedDate = DateUtils.formatLocalDate(date, DateUtils.DR_DATE_PATTERN);
        assertEquals("17/08/1988", formattedDate);
        formattedDate = DateUtils.formatLocalDate(date, DateUtils.US_DATE_PATTERN);
        assertEquals("08/17/1988", formattedDate);
    }

    @Test
    void getFormatter() {
        DateTimeFormatter formatter = DateUtils.getFormatter(DateUtils.US_DATE_PATTERN);
        assertNotNull(formatter);
    }

    @Test
    void formatLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(1988,Month.AUGUST,17,8,30,15);
        String formattedDate = DateUtils.formatLocalDateTime(localDateTime, DateUtils.DR_DATE_TIME_PATTERN);
        assertEquals("17/08/1988 08:30:15", formattedDate);
        formattedDate = DateUtils.formatLocalDateTime(localDateTime, DateUtils.US_DATE_TIME_PATTERN);
        assertEquals("08/17/1988 08:30:15", formattedDate);
    }

    @Test
    void parseLocalDate() {
        LocalDate date = LocalDate.of(1988, Month.AUGUST, 17);
        LocalDate date2 = DateUtils.parseLocalDate("17/08/1988",DateUtils.DR_DATE_PATTERN);
        assertEquals(date, date2);
    }

    @Test
    void parseLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(1988,Month.AUGUST,17,8,30,15);
        LocalDateTime localDateTime2 = DateUtils.parseLocalDateTime("17/08/1988 08:30:15", DateUtils.DR_DATE_TIME_PATTERN);
        assertEquals(localDateTime, localDateTime2);
    }
}