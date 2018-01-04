package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        return this.prepareToFillDestinationObject();
    }

    private <T> T prepareToFillDestinationObject() {
        T destinationObject = this.createInstanceFor(this.destination);

        java.util.Map<String, Object> originMap = this.getPropertiesAndValuesFrom(this.origin);

        if (this.ignorable != null) {
            this.excludedFieldsFor(originMap);
        }

        destinationObject = this.fillDestinationObjectWithOriginMap(originMap, destinationObject);

        if (this.customRelations != null) {
            destinationObject = this.applyCustomMappings(destinationObject);
        }

        return destinationObject;
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

    private void excludedFieldsFor(java.util.Map originMap) {
        List<String> fieldsToIgnore = new ArrayList<>();
        this.ignorable.fillWithFieldsToIgnore(fieldsToIgnore);

        fieldsToIgnore.stream().forEach(field -> {
            originMap.remove(field);
        });
    }

    private <T> T fillDestinationObjectWithOriginMap(java.util.Map<String, Object> originMap,
                                                     T destinationObject) {
        return (T) (new AllocationHandler(originMap, destinationObject)).getPopulatedDestinationObject();
    }

    private <T> T applyCustomMappings(T destinationObject) {
        Relate relate = new Relate();
        this.customRelations.map(relate);

        for (Relate.Entry<String, String> entry : relate.entrySet()) {
            Object value = this.getValueFrom(this.origin, entry.getKey());
            this.setValueTo(destinationObject, value, entry.getValue());
        }

        return destinationObject;
    }

    private void setValueTo(Object destination, Object value, String destinationField) {
        String pathToField = this.clearPathField(destinationField);

        String rootField = pathToField;
        String remainingFields = null;

        if (rootField.contains(".")) {
            rootField = destinationField.substring(0, destinationField.indexOf("."));
            remainingFields = destinationField.substring(rootField.length() + 1);
        }

        try {
            Field declaredField = destination.getClass().getDeclaredField(rootField);
            declaredField.setAccessible(true);
            Object instanceObject = this.createInstanceFor(declaredField.getType());
            if (remainingFields != null) {
                declaredField.set(destination, instanceObject);
                this.setValueTo(instanceObject, value, remainingFields);
            } else {
                declaredField.set(destination, value);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String clearPathField(String destinationField) {
        if (destinationField.trim().startsWith(".")) {
            destinationField = destinationField.substring(1);
        }

        if (destinationField.trim().endsWith(".")) {
            destinationField = destinationField.substring(0, destinationField.length() - 1);
        }

        return destinationField;
    }

    private Object getValueFrom(Object object, String field) {
        for (String fieldName : field.split("\\.")) {
            try {
                Field declaredField = object.getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                object = declaredField.get(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return object;
    }

    private <T> T createInstanceFor(Class clazz) {
        T object = null;

        try {
            object = (T) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return object;
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
