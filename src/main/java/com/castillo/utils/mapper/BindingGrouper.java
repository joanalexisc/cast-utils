package com.castillo.utils.mapper;
@FunctionalInterface
public interface BindingGrouper {
    void addToGroup(String ...groups);
}
