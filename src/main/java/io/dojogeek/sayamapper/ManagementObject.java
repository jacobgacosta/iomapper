package io.dojogeek.sayamapper;

import java.util.List;
import java.util.logging.Logger;

public abstract class ManagementObject {

    private final static Logger LOGGER = Logger.getLogger(ManagementObject.class.getName());

    protected void merge(SourceObject sourceObject, Object target, IgnorableList ignorableList, CustomMapper customMapper) {
        this.merge(sourceObject, new InspectableObject(target).getDeclaredFieldsIgnoring(ignorableList), customMapper);
    }

    private void merge(SourceObject sourceObject, List<FlexibleField> flexibleFieldList, CustomMapper customMapper) {
        flexibleFieldList.forEach(field -> {
            if (field.isIgnorable()) {
                return;
            }

            if (customMapper != null) {
                CustomTarget customTarget = customMapper.getTargetFor(field);

                CustomSource customSource = customMapper.getSourceFor(customTarget);

                FlexibleField sourceMatchedField = sourceObject.findFieldWithName(customSource.getRootField());

                if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                    field.setValue(sourceMatchedField);
                }
            }

            FlexibleField sourceMatchedField = sourceObject.findFieldWithName(field.getName());

            if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                field.setValue(sourceMatchedField);
            }
        });
    }

}
