package com.castillo.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class StringUtils {
    public static final String EMPTY_STRING = "";
    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static String addZerosToLeft(Integer value, Integer amountZeros) {
        return String.format("%0".concat(String.valueOf(amountZeros + 1)).concat("d"), value);
    }
    public static String encodeUrl(String value){
        return   URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static boolean isNullOrEmpty(String str){
        return isNull(str) || str.trim().isEmpty();
    }

    public static void lowerCaseField(Supplier<String> from, Consumer<String> to){
        lowerCaseField(from.get(), to);
    }
    public static void lowerCaseField(String value, Consumer<String> to){
        to.accept(lowerCase(value));
    }
    public static String lowerCase(String value){
        return orEmpty(value).toLowerCase();
    }
    public static void upperCaseField(Supplier<String> from, Consumer<String> to){
        upperCaseField(from.get(), to);
    }
    public static void upperCaseField(String value, Consumer<String> to){
        to.accept(upperCase(value));
    }
    public static String upperCase(String value){
        return orEmpty(value).toUpperCase();
    }

    public static String orEmpty(String value){
        return isNull(value) ? EMPTY_STRING : value;
    }

    public static boolean isNumeric(String value){
        return isNullOrEmpty(value) ? false :  pattern.matcher(value).matches();
    }

}
