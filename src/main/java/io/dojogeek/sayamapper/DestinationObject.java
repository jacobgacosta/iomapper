package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DestinationObject<T> {

    private T destination;
    private Inspectable origin;
    private java.util.Map<String, Object> originMap;
    private Ignorable ignorable;
    private CustomMapper customRelations;

    public DestinationObject(Class destination) {
        this.destination = this.createInstance(destination);
    }

    public <T> T getFilledObject() {
        this.applyExclusions();
        this.populate();
        this.applyCustomMapping();

        return (T) this.destination;
    }

    public void fillWith(Inspectable origin) {
        this.origin = origin;
        this.originMap = origin.getPropertiesAndValuesMap();
    }

    public void setIgnorable(Ignorable ignorable) {
        this.ignorable = ignorable;
    }

    public void setCustomMapping(CustomMapper customRelations) {
        this.customRelations = customRelations;
    }

    private <T> T createInstance(Class clazz) {
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

    private void populate() {
        originMap.forEach((originFieldName, originValue) -> {
            this.merge(this.getFieldIfExist(originFieldName), originValue);
        });
    }

    private void applyExclusions() {
        if (this.ignorable != null) {
            List<String> fieldsToIgnore = new ArrayList<>();
            ignorable.fieldsToIgnore(fieldsToIgnore);

            fieldsToIgnore.stream().forEach(fieldToIgnore -> {
                originMap.remove(fieldToIgnore);
            });
        }
    }

    private void applyCustomMapping() {
        if (this.customRelations != null) {
            CustomMapping customMapping = new CustomMapping();
            customRelations.map(customMapping);

            for (CustomMapping.Entry<String, String> entry : customMapping.entrySet()) {
                Object value = this.origin.getValueFor(entry.getKey());
                this.setValue(this.destination, value, entry.getValue());
            }
        }
    }

    private Field getFieldIfExist(String fieldName) {
        Field field = null;

        try {
            field = this.destination.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return field;
    }

    private void merge(Field field, Object value) {
        try {
            field.set(this.destination, value);
        } catch (IllegalAccessException | RuntimeException e) {
            return;
        }
    }

    private void setValue(Object destination, Object value, String destinationField) {
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
            Object instanceObject = this.createInstance(declaredField.getType());

            if (remainingFields != null) {
                declaredField.set(destination, instanceObject);
                this.setValue(instanceObject, value, remainingFields);
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

}
