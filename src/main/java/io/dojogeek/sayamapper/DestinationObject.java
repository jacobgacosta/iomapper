package io.dojogeek.sayamapper;

import java.lang.reflect.Field;

public class DestinationObject<T> {

    private T destination;
    private Inspectable origin;
    private java.util.Map<String, Object> originFieldsAndValuesMap;
    private Ignorable toIgnore;
    private CustomMapper customRelations;

    public DestinationObject(Class destination) {
        this.destination = this.createInstance(destination);
    }

    public <T> T getFilledObject() {
        this.prepareObject();

        return (T) this.destination;
    }

    public void fillWith(Inspectable origin) {
        this.origin = origin;
        this.originFieldsAndValuesMap = origin.getPropertiesAndValuesMap();
    }

    public void ignore(Ignorable ignorable) {
        this.toIgnore = ignorable;
    }

    public void customMapping(CustomMapper customRelations) {
        this.customRelations = customRelations;
    }

    private void prepareObject() {
        this.ignoreFieldsForMapping();
        this.populate();
        this.applyCustomMapping();
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

    private DestinationObject populate() {
        originFieldsAndValuesMap.forEach((originFieldName, originValue) -> {
            this.merge(this.getFieldIfExist(originFieldName), originValue);
        });

        return this;
    }

    private void ignoreFieldsForMapping() {
        if (this.toIgnore == null) {
            return;
        }

        FieldList fieldsToIgnore = this.toIgnore.ignore(new FieldList());
        fieldsToIgnore.forEach(field -> originFieldsAndValuesMap.remove(field));
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
