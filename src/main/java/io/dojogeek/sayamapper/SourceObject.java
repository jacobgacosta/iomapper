package io.dojogeek.sayamapper;

public class SourceObject {

    private Object source;

    public SourceObject(Object source) {
        this.source = source;
    }

    public FlexibleField findMatchingWith(FlexibleField flexibleField) {
        for (FlexibleField declaredField : new InspectableObject(this.source).getDeclaredFields()) {
            if (declaredField.getName().toLowerCase().contains(flexibleField.getName().toLowerCase())) {
                flexibleField = declaredField;
            }
        }

        return flexibleField;
    }

}
