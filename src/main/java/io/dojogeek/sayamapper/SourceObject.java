package io.dojogeek.sayamapper;

public class SourceObject {

    private Object source;

    public SourceObject(Object source) {
        this.source = source;
    }

    public FlexibleField findFieldWithName(String fieldName) {
        FlexibleField flexibleField = null;

        if (fieldName == null) {
            return flexibleField;
        }

        for (FlexibleField declaredField : new InspectableObject(this.source).getDeclaredFields()) {
            if (declaredField.getName().toLowerCase().contains(fieldName.toLowerCase()) ||
                    fieldName.toLowerCase().contains(declaredField.getName())) {
                flexibleField = declaredField;
            }
        }

        return flexibleField;
    }

}
