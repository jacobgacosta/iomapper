package io.dojogeek.sayamapper;

public class SourceObject {

    private Object source;

    public SourceObject(Object source) {
        this.source = source;
    }

    public FlexibleField findMatchingFor(String fieldName) {
        FlexibleField flexibleField = null;

        for (FlexibleField declaredField : new InspectableObject(this.source).getDeclaredFields()) {
            if (declaredField.getName().toLowerCase().contains(fieldName.toLowerCase())) {
                flexibleField = declaredField;
            }
        }

        return flexibleField;
    }

}
