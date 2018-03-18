package io.dojogeek.sayamapper;

import java.util.List;

public abstract class ManagementObject {

    protected void merge(SourceObject sourceObject, Object target) {
        this.merge(sourceObject, new InspectableObject(target).getDeclaredFields());
    }

    protected void merge(SourceObject sourceObject, Object target, IgnorableList ignorableList, CustomMapper customMapper) {
        this.merge(sourceObject, new InspectableObject(target).getDeclaredFieldsIgnoring(ignorableList), customMapper);
    }

    private void merge(SourceObject sourceObject, List<FlexibleField> flexibleFieldList, CustomMapper customMapper) {
        flexibleFieldList.forEach(field -> {
            if (field.isIgnorable()) {
                return;
            }

            if (!customMapper.isEmpty()) {
                String sourceField = customMapper.getSourceFor(field.getName());

                if (!sourceField.isEmpty()) {
                    FlexibleField sourceMatchedField = sourceObject.findMatchingFieldWithName(sourceField);

                    if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                        field.setValue(sourceMatchedField);
                    }
                }
            }

            FlexibleField sourceMatchedField = sourceObject.findMatchingFieldWithName(field.getName());

            if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                field.setValue(sourceMatchedField);
            }
        });
    }

}
