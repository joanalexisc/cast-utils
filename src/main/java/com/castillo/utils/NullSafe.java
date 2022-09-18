package com.castillo.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NullSafe<T> {
    public static NullSafe<?> nullValue = new NullSafe<>(null);

    public static <T> T orGet(T value, Supplier<T> getDefault) {
        return Objects.requireNonNullElseGet(value, getDefault);
    }

    public static <T> T or(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static <T> NullSafe<T> of(T obj){
        return new NullSafe<T>(obj);
    }

    private final T value;

    public NullSafe(T value){
        this.value = value;
    }

    public <R> NullSafe<R> get(Function<T,R> function){
        return value != null ? NullSafe.of(function.apply(value)) : (NullSafe<R>) this;
    }

    public T value(){
        return this.value;
    }

    public T or(T defaultValue){
        return NullSafe.or(this.value, defaultValue);
    }

    public boolean isNull(){
        return this.value == null;
    }

    public <V> Stream<V> stream(Class<V> ...innerType){
        Stream<V> stream = Stream.empty();
        if(!isNull()){
            if(this.value instanceof Collection){
                stream = ((Collection<V>) this.value).stream();
            }else{
                stream = Stream.of((V)value);
            }
        }
        return stream;
    }

}
