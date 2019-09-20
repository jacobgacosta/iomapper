package dev.iomapper;

/**
 * SourceWrapper is a wrapper to handle easily the operations on the source object.
 *
 * @author Jacob G. Acosta
 */
public class SourceWrapper {

    private Object source;

    /**
     * SourceWrapper constructor.
     *
     * @param source the source object instance.
     */
    public SourceWrapper(Object source) {
        this.source = source;
    }

    /**
     * Gets if exist, a field matching <b>fieldName</b> inside the source object.
     *
     * @param fieldName the field name.
     * @return a <b>FlexibleField</b>
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

    /**
     * Gets the class name of the wrapped object.
     *
     * @return the class name.
     */
    public String getClassName() {
        String[] fullNameClass = this.source.getClass().getName().split(Delimiters.DOT_SEPARATOR);

        return fullNameClass[fullNameClass.length - 1];
    }

}
