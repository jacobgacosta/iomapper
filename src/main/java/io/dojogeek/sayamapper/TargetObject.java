package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.Optional;

public class DestinationObject<T> {

    private T destination;
    private Inspectable source;
    private Ignorable toIgnore;
    private Customizable customRelations;

    public DestinationObject(Class destination) {
        this.destination = this.createInstance(destination);
    }

    public <T> T getFilledInstance() {
        this.prepare();

        return (T) this.destination;
    }

    public void fillWith(Inspectable source) {
        this.source = source;
    }

    public void ignore(Ignorable ignorable) {
        this.toIgnore = ignorable;
    }

    public void customMapping(Customizable customRelations) {
        this.customRelations = customRelations;
    }

    private void prepare() {
        this.ignoreFieldsForMapping();
        this.populateFrom(this.source);
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

    private void populateFrom(Inspectable sourceObject) {
        sourceObject.getPropertiesAndValues().forEach((sourceField, sourceValue) -> {
            Optional<Field> matchedField = this.searchForFieldsThatMatchWith(sourceField);

            if (matchedField.isPresent()) {
                this.merge(matchedField.get(), sourceValue);
            }
        });
    }

    private Optional<Field> searchForFieldsThatMatchWith(Field field) {
        for (Field declaredField : this.destination.getClass().getDeclaredFields()) {
            if (declaredField.getName().toLowerCase().contains(field.getName())) {
                return Optional.of(declaredField);
            }
        }

        return Optional.empty();
    }

    private void merge(Field field, Object value) {
        if (!field.getType().isAssignableFrom(value.getClass())) {
            this.tryMerge(field, value);
        }

        try {
            field.set(this.destination, value);
        } catch (IllegalAccessException | RuntimeException e) {
            return;
        }
    }

    private void tryMerge(Field field, Object value) {
        DestinationObject destinationObject = new DestinationObject<>(field.getType());
        destinationObject.populateFrom(new SourceObject(value));
    }

    private void ignoreFieldsForMapping() {
        if (this.toIgnore == null) {
            return;
        }

        IgnorableList fieldsToIgnore = this.toIgnore.ignore(new IgnorableList());
        fieldsToIgnore.forEach(field -> this.source.getPropertiesAndValues().remove(field));
    }

    private void applyCustomMapping() {
        if (this.customRelations != null) {
            CustomMapping customMapping = new CustomMapping();
            customRelations.map(customMapping);

            for (CustomMapping.Entry<String, String> entry : customMapping.entrySet()) {
                Object value = this.source.getValueFor(entry.getKey());
                this.setValue(this.destination, value, entry.getValue());
            }
        }
    }

    private void setValue(Object reference, Object value, String destinationField) {
        String pathToField = this.clearPathField(destinationField);

        String rootField = pathToField;
        String remainingFields = null;

        if (rootField.contains(".")) {
            rootField = destinationField.substring(0, destinationField.indexOf("."));
            remainingFields = destinationField.substring(rootField.length() + 1);
        }

        try {
            Field declaredField = reference.getClass().getDeclaredField(rootField);
            declaredField.setAccessible(true);
            Object instanceObject = this.createInstance(declaredField.getType());

            if (remainingFields != null) {
                declaredField.set(reference, instanceObject);
                this.setValue(instanceObject, value, remainingFields);
            } else {
                declaredField.set(reference, value);
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
