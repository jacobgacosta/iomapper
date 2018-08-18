package io.dojogeek.sayamapper;

/**
 * SourceObject is a wrapper object of the source instance.
 *
 * @author norvek
 */
public class SourceObject {

    private Object source;

    /**
     * SourceObject constructor.
     *
     * @param source the source instance.
     */
    public SourceObject(Object source) {
        this.source = source;
    }

    /**
     * Finds a matching field into the source instance.
     *
     * @param fieldName the field name.
     * @return a <bold>FlexibleField</bold>
     */
    public FlexibleField getMatchingFieldFor(String fieldName) {
        FlexibleField flexibleField = null;

        for (FlexibleField declaredField : new InspectableObject(this.source).getDeclaredFields()) {
            if (declaredField.getName().toLowerCase().equals(fieldName.toLowerCase())) {
                flexibleField = declaredField;
            }

            if (flexibleField == null && declaredField.getName().toLowerCase().contains(fieldName.toLowerCase())) {
                flexibleField = declaredField;
            }
        }

        return flexibleField;
    }

}
