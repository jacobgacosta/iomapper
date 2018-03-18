package io.dojogeek.sayamapper;

import java.util.List;

public abstract class ManagementObject {

    protected void merge(SourceObject sourceObject, Object target) {
        this.merge(sourceObject, new InspectableObject(target).getDeclaredFields());
    }

    protected void merge(SourceObject sourceObject, Object target, IgnorableList ignorableList) {
        this.merge(sourceObject, new InspectableObject(target).getDeclaredFieldsIgnoring(ignorableList));
    }

    private void merge(SourceObject sourceObject, List<FlexibleField> flexibleFieldList) {
        flexibleFieldList.forEach(field -> {
            if (field.isIgnorable()) {
                return;
            }

            FlexibleField sourceMatchedField = sourceObject.findMatchingFieldWithName(field.getName());

            if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                field.setValue(sourceMatchedField);
            }
        });
    }

}
