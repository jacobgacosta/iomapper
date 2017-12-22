package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {

    private Object source;
    private Class target;
    private Ignorable ignorableSource;
    private Ignorable ignorableTarget;

    private Mapper() {
    }

    public static Mapper from(Object source) {
        Mapper mapper = new Mapper();
        mapper.source = source;
        return mapper;
    }

    public Mapper to(Class clazz) {
        this.target = clazz;
        return this;
    }

    public <T> T build() {
        return this.fillTargetWith(this.source);
    }

    private <T> T fillTargetWith(Object source) {
        T targetInstance = this.createInstanceFor(this.target);

        Map<String, Object> sourceMap = this.getPropertiesAndValuesFrom(source.getClass(), source);

        this.removeExcludedFields(this.ignorableSource, sourceMap);

        sourceMap.forEach((key, value) -> {
            try {
                Field field = targetInstance.getClass().getDeclaredField(key);

                if (field != null) {
                    field.setAccessible(true);
                    field.set(targetInstance, value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return targetInstance;
    }

    private void removeExcludedFields(Ignorable ignorable, Map<String, Object> sourceMap) {
        List<String> fieldsToIgnore = new ArrayList<>();
        ignorable.fillWithFieldsToIgnore(fieldsToIgnore);

        fieldsToIgnore.stream().forEach(field -> {
            sourceMap.remove(field);
        });
    }

    private <T> T createInstanceFor(Class target) {
        T object = null;

        try {
            object = (T) target.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return object;
    }

    private Map<String, Object> getPropertiesAndValuesFrom(Class<?> clazz, Object instance) {
        return Arrays.asList(clazz.getDeclaredFields()).stream()
                .collect(Collectors.toMap(field -> field.getName(), field -> {
                    field.setAccessible(true);

                    Object value = null;

                    try {
                        value = field.get(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    return value;
                }));
    }

    public Mapper ignoreFromSource(Ignorable ignorable) {
        this.ignorableSource = ignorable;
        return this;
    }

    public Mapper ignoreFromTarget(Ignorable ignorable) {
        this.ignorableTarget = ignorable;
        return this;
    }

}