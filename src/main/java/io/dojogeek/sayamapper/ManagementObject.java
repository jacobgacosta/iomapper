package io.dojogeek.sayamapper;

import java.util.List;

public abstract class ManagementObject {

    abstract InspectableObject getInspectableObject();

    protected void populateFrom(SourceObject sourceObject, IgnorableList ignorableList) {
        this.merge(sourceObject, this.getDeclaredFieldsIgnoring(ignorableList));
    }

    protected void populateFrom(SourceObject sourceObject, String ignoreFields) {
        this.merge(sourceObject, this.getDeclaredFieldsIgnoring(ignoreFields));
    }

    protected List<FlexibleField> getDeclaredFields() {
        return this.getInspectableObject().getDeclaredFields();
    }

    protected List<FlexibleField> getDeclaredFieldsIgnoring(IgnorableList ignorableList) {
        return this.getInspectableObject().getDeclaredFieldsIgnoring(ignorableList);
    }

    protected List<FlexibleField> getDeclaredFieldsIgnoring(String ignoreFields) {
        return this.getInspectableObject().getDeclaredFieldsIgnoring(ignoreFields);
    }

    protected FlexibleField findMatchingFieldWithName(String fieldName) {
        FlexibleField flexibleField = null;

        for (FlexibleField declaredField : this.getDeclaredFields()) {
            if (declaredField.getName().toLowerCase().contains(fieldName) ||
                    fieldName.toLowerCase().contains(declaredField.getName())) {
                flexibleField = declaredField;
            }
        }

        return flexibleField;
    }

    private void merge(SourceObject sourceObject, List<FlexibleField> targetFields) {
        targetFields.forEach(targetField -> {
            if (targetField.isIgnorable()) {
                return;
            }

            FlexibleField sourceMatchedField = sourceObject.findMatchingFieldWithName(targetField.getName());

            if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                targetField.setValue(sourceMatchedField);
            }
        });
    }

}
