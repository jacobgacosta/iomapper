package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Map {

    private Object origin;
    private Class destination;
    private Ignorable ignorable;
    private CustomMapper customRelations;

    private Map() {
    }

    public static Map from(Object source) {
        Map map = new Map();
        map.origin = source;
        return map;
    }

    public Map to(Class clazz) {
        this.destination = clazz;
        return this;
    }

    public <T> T build() {
        return this.fillDestinationObjectWith(this.origin);
    }

    private <T> T fillDestinationObjectWith(Object origin) {
        T destinationInstance = this.createInstanceFor(this.destination);

        java.util.Map<String, Object> originMap = this.getPropertiesAndValuesFrom(origin);

        if (this.ignorable != null) {
            this.excludedFieldsFor(originMap);
        }

        originMap.forEach((key, value) -> {
            try {
                Field destinationField = destinationInstance.getClass().getDeclaredField(key);

                isCustomizable(destinationField);

                if (destinationField != null) {
                    destinationField.setAccessible(true);
                    destinationField.set(destinationInstance, value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return destinationInstance;
    }

    private void setValue(Field field) {
    }

    private boolean isCustomizable(Field field) {
        if (this.customRelations != null
                && this.existDestinationFieldInCustomRelations(field.getName())) {
        }

        return false;
    }

    private boolean existDestinationFieldInCustomRelations(String fieldName) {
        Relate relate = new Relate();
        this.customRelations.map(relate);

        boolean existInCustomMapping = false;

        for (java.util.Map.Entry<String, String> entry : relate.entrySet()) {
            return Pattern.matches("^" + fieldName + "(\\.\\w+$)?", entry.getValue());
        }

        return existInCustomMapping;
    }

    private void excludedFieldsFor(java.util.Map sourceMap) {
        List<String> fieldsToIgnore = new ArrayList<>();
        this.ignorable.fillWithFieldsToIgnore(fieldsToIgnore);

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

    private java.util.Map<String, Object> getPropertiesAndValuesFrom(Object instance) {
        return Arrays.asList(instance.getClass().getDeclaredFields()).stream()
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

    public Map ignoreFieldsFromSource(Ignorable ignorable) {
        this.ignorable = ignorable;
        return this;
    }

    public Map customRelate(CustomMapper customMapper) {
        this.customRelations = customMapper;
        return this;
    }

}