package io.dojogeek.sayamapper;

import java.util.List;
import java.util.logging.Logger;

public abstract class ManagementObject {

    private final static Logger LOGGER = Logger.getLogger(ManagementObject.class.getName());

    protected void populateFrom(SourceObject sourceObject) {
        this.getDeclaredFields().forEach(targetField -> {
            FlexibleField sourceMatchedField = sourceObject.findMatchingFieldWithName(targetField.getName());

            if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                targetField.setValue(sourceMatchedField);
            }
        });
    }

    protected List<FlexibleField> getDeclaredFields() {
        return this.getReferenceToManageableObject().getDeclaredFields();
    }

    protected FlexibleField findMatchingFieldWithName(String fieldName) {
        FlexibleField flexibleField = null;

        for (FlexibleField declaredField : this.getReferenceToManageableObject().getDeclaredFields()) {
            if (declaredField.getName().toLowerCase().contains(fieldName) ||
                    fieldName.toLowerCase().contains(declaredField.getName())) {
                flexibleField = declaredField;
            }
        }

        return flexibleField;
    }

    abstract InspectableObject getReferenceToManageableObject();

}
