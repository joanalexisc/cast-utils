package com.castillo.utils;

import com.castillo.utils.pojos.Computer;
import com.castillo.utils.pojos.SoundCard;
import com.castillo.utils.pojos.TestObj;
import com.castillo.utils.pojos.USB;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ObjectUtilsTest {

    @Test
    void or() {
        String value = "test";
        String defaultValue = "default";
        assertEquals(value, ObjectUtils.or(value, defaultValue));
        assertEquals(defaultValue, ObjectUtils.or(null, defaultValue));
    }

    @Test
    void orWithSupplier() {
        List<String> list = null;
        list = ObjectUtils.or(list, ArrayList::new);
        assertNotNull(list);
    }

    @Test
    void orWithBoolean() {
        String trueValue = "Y";
        String falseValue = "N";
        assertEquals(trueValue, ObjectUtils.or(true, trueValue, falseValue));
        assertEquals(falseValue, ObjectUtils.or(false, trueValue, falseValue));
    }

    @Test
    void orGet(){
        Supplier<String> trueSupplier = () -> "Y";
        Supplier<String> falseSupplier = () -> "N";
        assertEquals("Y", ObjectUtils.orGet(true, trueSupplier, falseSupplier));
        assertEquals("N", ObjectUtils.orGet(false, trueSupplier, falseSupplier));
    }

    @Test
    void consumeWithSupplier() {
        Supplier<String> supplier = ()-> "1.0.0";
        USB usb = new USB();
        ObjectUtils.consume(supplier, usb::setVersion);
        assertEquals(supplier.get(), usb.getVersion());
    }


    @Test
    void consumeValue() {
        TestObj testObj = new TestObj();
        assertNull(testObj.getStrValue());
        assertTrue(ObjectUtils.consume("TEST", testObj::setStrValue));
        assertEquals("TEST", testObj.getStrValue());
        String value = null;
        assertFalse(ObjectUtils.consume(value, testObj::setStrValue));
        assertEquals("TEST", testObj.getStrValue());
    }

    @Test
    void getOrSet() {
        Computer computer = new Computer();
        assertNull(computer.getSoundCard());
        SoundCard soundCard = ObjectUtils.setValueOrGetDefault(computer::getSoundCard, computer::setSoundCard, SoundCard::new);
        assertNotNull(computer.getSoundCard());
        assertEquals(soundCard, computer.getSoundCard());
    }


    @Test
    void throwIfNull() {
        assertThrows(RuntimeException.class, ()-> ObjectUtils.throwIfNull(null, RuntimeException::new));
        assertDoesNotThrow(()->ObjectUtils.throwIfNull("", RuntimeException::new));
    }

    @Test
    void supply() {
        AtomicInteger counter = new AtomicInteger(0);
        Function<String, Long> converter = (input) -> {
            counter.incrementAndGet();
            return Long.parseLong(input);
        };
        Supplier<Long> result = ObjectUtils.supply("10",  converter);
        assertEquals(0, counter.get());
        assertEquals(10, result.get());
        assertEquals(1, counter.get());
    }

    @Test
    void supplyTwo() {
        AtomicInteger counter = new AtomicInteger(0);
        BiFunction<Integer, Integer, Integer> sum = (input1, input2) -> {
            counter.incrementAndGet();
            return input1 + input2;
        };
        Supplier<Integer> result = ObjectUtils.supply(5, 5,  sum);
        assertEquals(0, counter.get());
        assertEquals(10, result.get());
        assertEquals(1, counter.get());
    }

    @Test
    void areNotNull() {
        assertTrue(ObjectUtils.areNotNull("",1,"A",2L, 3D));
        assertFalse(ObjectUtils.areNotNull("",1,"A",2L, 3D, null));
    }

    @Test
    void isEmptyCollection() {
        List<String> list = null;
        assertTrue(ObjectUtils.isEmpty(list));
        list = new ArrayList<>();
        assertTrue(ObjectUtils.isEmpty(list));
        list.add("");
        assertFalse(ObjectUtils.isEmpty(list));
    }

    @Test
    void notEmptyCollection() {
        assertTrue(ObjectUtils.notEmpty(Arrays.asList("a","b")));
        assertFalse(ObjectUtils.notEmpty(new ArrayList<>()));
    }

    @Test
    void isEmptyString() {
        String value = null;
        assertTrue(ObjectUtils.isEmpty(value));
        assertTrue(ObjectUtils.isEmpty(""));
        assertTrue(ObjectUtils.isEmpty("    "));
        value = "ABC";
        assertFalse(ObjectUtils.isEmpty(value));
    }

    @Test
    void notEmptyString() {
        assertTrue(ObjectUtils.notEmpty("ABC"));
        assertFalse(ObjectUtils.notEmpty("   "));
    }
}