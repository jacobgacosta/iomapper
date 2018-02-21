package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InspectableObject {

    private static final int SINGLE_FIELD = 1;

    private Object object;

    public InspectableObject(Object object) {
        this.object = object;
    }

    public List<FlexibleField> getDeclaredFields() {
        List<FlexibleField> fields = new ArrayList<>();

        Stream.of(this.object.getClass().getDeclaredFields()).forEach(field -> {
            if (field.getType().isPrimitive() ||
                    field.getType().isAssignableFrom(Byte.class) ||
                    field.getType().isAssignableFrom(Short.class) ||
                    field.getType().isAssignableFrom(Integer.class) ||
                    field.getType().isAssignableFrom(Long.class) ||
                    field.getType().isAssignableFrom(Float.class) ||
                    field.getType().isAssignableFrom(Double.class) ||
                    field.getType().isAssignableFrom(String.class)) {
                fields.add(new JavaField(field, this.object));
                return;
            }

            fields.add(new ForeignField(field, this.object));
        });

        return fields;
    }

    public List<FlexibleField> getDeclaredFieldsIgnoring(IgnorableList ignorableList) {
        List<FlexibleField> flexibleFields = this.getDeclaredFields();

        if (ignorableList == null) {
            return flexibleFields;
        }

        flexibleFields.forEach(flexibleField -> {
            if (ignorableList.hasFieldNamed(flexibleField.getName())) {
                flexibleField.ignore();
                return;
            }

            String nestedFields = ignorableList.findNestedIgnorableFor(flexibleField.getName());

            if (!nestedFields.isEmpty()) {
                flexibleField.setNestedFieldsToIgnore(nestedFields);
            }
        });

        return flexibleFields;
    }

    public List<FlexibleField> getDeclaredFieldsIgnoring(String fieldsToIgnore) {
        List<FlexibleField> flexibleFields = this.getDeclaredFields();

        if (fieldsToIgnore == null || fieldsToIgnore.isEmpty()) {
            return flexibleFields;
        }

        flexibleFields.forEach(flexibleField -> {
            if (fieldsToIgnore.split("\\.").length == SINGLE_FIELD &&
                    fieldsToIgnore.equals(flexibleField.getName()) &&
                    fieldsToIgnore.contains(flexibleField.getName())) {
                flexibleField.ignore();
            }

            String firstField = fieldsToIgnore.substring(0, fieldsToIgnore.indexOf("."));

            if (firstField.equals(flexibleField.getName()) || firstField.contains(flexibleField.getName())) {
                flexibleField.setNestedFieldsToIgnore(fieldsToIgnore.substring(fieldsToIgnore.indexOf(".")));
            }

        });

        return flexibleFields;
    }

}
