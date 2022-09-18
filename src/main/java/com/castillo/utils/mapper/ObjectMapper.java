package com.castillo.utils.mapper;

import jdk.dynalink.linker.support.Guards;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ObjectMapper<S, T> {
    public static final String DEFAULT_GROUP = "DEFAULT";
    private final List<Mapping> mappings = new ArrayList<>();
    private final Map<String, List<Mapping>> mappingGroups = new HashMap<>();
    private final BiConsumer<String, Mapping> groupRegister = this::addToGroup;
    private Supplier<T> targetFactory;

    public static void group(List<String> groupNames, BindingGrouper... bindingGroupers) {
        groupNames.forEach(groupName -> group(groupName, bindingGroupers));
    }

    public static void group(String groupName, BindingGrouper... bindingGroupers) {
        Stream.of(bindingGroupers).forEach(bindingGrouper -> bindingGrouper.addToGroup(groupName));
    }

    public ObjectMapper(Supplier<T> targetFactory) {
        this.targetFactory = targetFactory;
    }

    private BindingGrouper addMapping(Mapping mapping) {
        this.mappings.add(mapping);
        return mapping::addGroup;
    }

    public <V> BindingGrouper addBinding(Function<S, V> getter, BiConsumer<T, V> setter) {
        return addMapping(new Mapping(getter, setter, Function.identity(), groupRegister));
    }

    public <V, V2> BindingGrouper addBinding(Function<S, V> getter, BiConsumer<T, V2> setter, Function<V, V2> map) {
        return addMapping(new Mapping(getter, setter, map, groupRegister));
    }

    public <V, V2> BindingGrouper addBinding(Function<S, V> getter, BiConsumer<T, V2> setter, ObjectMapper<V, V2> mapper) {
        return addBinding(getter, setter, mapper, null);
    }

    public <V, V2> BindingGrouper addBinding(Function<S, V> getter, BiConsumer<T, V2> setter, ObjectMapper<V, V2> mapper, String subGroup) {
        Function<V, V2> map = isNull(subGroup) ? mapper::map : (value) -> mapper.map(value, subGroup);
        return addMapping(new Mapping(getter, setter, map, groupRegister));
    }

    private void addToGroup(String key, Mapping<?, ?> mapping) {
        mappingGroups.computeIfAbsent(key, (v) -> new ArrayList()).add(mapping);
    }

    public T map(S source) {
        return this.map(source, null, null);
    }

    public T map(S source, String group) {
        return this.map(source, null, group);
    }

    public T map(S source, T target) {
        return this.map(source, target, null);
    }

    private List<Mapping> getMappings(String group) {
        return isNull(group) ?
                this.mappingGroups.getOrDefault(DEFAULT_GROUP, this.mappings)
                : this.mappingGroups.getOrDefault(group, Collections.EMPTY_LIST);
    }

    public T map(S source, T target, String group) {
        T auxTarget = null;

        if (nonNull(source)) {
            final T finalTarget = isNull(target) ? targetFactory.get() : target;
            this.getMappings(group).forEach(mapping -> mapping.map(source, finalTarget));
            auxTarget = finalTarget;
        }
        return auxTarget;
    }

    private class Mapping<V, V2> {
        private final Function<S, V> getter;
        private final BiConsumer<T, V2> setter;
        private final Function<V, V2> map;
        private final BiConsumer<String, Mapping<?, ?>> groupRegister;

        public Mapping(Function<S, V> getter, BiConsumer<T, V2> setter, Function<V, V2> map, BiConsumer<String, Mapping<?, ?>> groupRegister) {
            this.getter = getter;
            this.setter = setter;
            this.map = map;
            this.groupRegister = groupRegister;
        }

        public void map(S source, T target) {
            V value = getter.apply(source);
            V2 value2 = map.apply(value);
            setter.accept(target, value2);
        }

        public void addGroup(String... groups) {
            Arrays.asList(groups).forEach(group -> groupRegister.accept(group, this));
        }
    }
}
