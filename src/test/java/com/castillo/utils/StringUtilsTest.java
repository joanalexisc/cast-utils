package com.castillo.utils;

import com.castillo.utils.pojos.TestObj;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {


    @Test
    void addZerosToLeft() {
        assertEquals("007", StringUtils.addZerosToLeft(7,2));
    }

    @Test
    void encodeUrl() {
        /* &/abc\33 */
        assertEquals("%26%2Fabc%5C33", StringUtils.encodeUrl("&/abc\\33"));
    }

    @Test
    void isNullOrEmpty() {

        String emptyValue = "";
        String emptySpaces = "  ";
        String notEmpty= "ABC";

        assertTrue(StringUtils.isNullOrEmpty(null));
        assertTrue(StringUtils.isNullOrEmpty(emptyValue));
        assertTrue(StringUtils.isNullOrEmpty(emptySpaces));
        assertFalse(StringUtils.isNullOrEmpty(notEmpty));
    }

    @Test
    void lowerCaseField() {
        TestObj testObj = new TestObj();
        assertNull(testObj.getStrValue());
        StringUtils.lowerCaseField("ABC", testObj::setStrValue);
        assertEquals("abc", testObj.getStrValue());
        testObj.setStrValue("XYZ");
        StringUtils.lowerCaseField(testObj::getStrValue, testObj::setStrValue);
        assertEquals("xyz", testObj.getStrValue());
    }

    @Test
    void lowerCase() {
        assertEquals("", StringUtils.lowerCase(null));
        assertEquals("abc", StringUtils.lowerCase("ABC"));
    }

    @Test
    void upperCaseField() {
        TestObj testObj = new TestObj();
        assertNull(testObj.getStrValue());
        StringUtils.upperCaseField("abc", testObj::setStrValue);
        assertEquals("ABC", testObj.getStrValue());
        testObj.setStrValue("xyz");
        StringUtils.upperCaseField(testObj::getStrValue, testObj::setStrValue);
        assertEquals("XYZ", testObj.getStrValue());
    }

    @Test
    void upperCase() {
        assertEquals("", StringUtils.upperCase(null));
        assertEquals("ABC", StringUtils.upperCase("abc"));
    }

    @Test
    void orEmpty(){
        assertEquals("", StringUtils.orEmpty(null));
        assertEquals("ABC", StringUtils.orEmpty("ABC"));
    }

    @Test
    void isNumeric() {
        assertTrue(StringUtils.isNumeric("123"));
        assertTrue(StringUtils.isNumeric("-123"));
        assertTrue(StringUtils.isNumeric("123.01"));
        assertTrue(StringUtils.isNumeric("-123.33"));
        assertFalse(StringUtils.isNumeric("123.33.33"));
    }
}