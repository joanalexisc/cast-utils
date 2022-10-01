package com.castillo.utils;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class ObjectUtils {
    private ObjectUtils() {

    }

    public static void throwIfNull(Object value, Supplier<? extends RuntimeException> exceptionSupplier){
        if(value == null){
            throw  exceptionSupplier.get();
        }
    }

    public static <T> boolean consume(Supplier<T> supplier, Consumer<T> consumer){
        return consume(supplier.get(), consumer);
    }

    public static <T> boolean consume(T value, Consumer<T> consumer){
        boolean consumed = value != null;
        if(consumed){
            consumer.accept(value);
        }
        return consumed;
    }

    public static <T> T setValueOrGetDefault(Supplier<T> getter, Consumer<T> setter, Supplier<T> supplier){
        return setValueOrDefault(getter, setter, supplier.get());
    }

    public static <T> T setValueOrDefault(Supplier<T> getter, Consumer<T> setter, T defaultValue){
        T internalValue = getter.get();
        if(internalValue == null){
            setter.accept(defaultValue);
            internalValue = defaultValue;
        }
        return  internalValue;
    }

    public static <T,R> Supplier<R> supply(T value, Function<T,R> function){
        return ()-> function.apply(value);
    }

    public static <T,U,R> Supplier<R> supply(T value, U value2, BiFunction<T,U,R> biFunction){
        return ()-> biFunction.apply(value, value2);
    }

    public static boolean areNotNull(Object... objects) {
        return Stream.of(objects).allMatch(Objects::nonNull);
    }

    public static <T> T or(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
    public static  <T> T or(boolean value, T trueValue, T falseValue){
        return value ? trueValue : falseValue;
    }

    public static  <T> T orGet(boolean value, Supplier<T> trueSupplier, Supplier<T> falseSupplier){
        return value ? trueSupplier.get() : falseSupplier.get();
    }
}
