package dev.iomapper;

import java.lang.reflect.Field;

/**
 * JavaField is a wrapper of a Java property.
 * <p>
 * A source field or target field can be of this type and be wrapped to help in the custom mappings.
 *
 * @author Jacob G. Acosta
 */
public class JavaField extends FlexibleField {

    /**
     * JavaField constructor.
     *
     * @param field        the field.
     * @param parentObject the reference object that hosts the field.
     */
    public JavaField(Field field, Object parentObject) {
        super(field, parentObject);
    }

    /**
     * This method chooce between set directly a value to a Java field or apply the merge
     * custom mapping operation for nested objects.
     * <p>
     * In the custom mapping, an object
     *
     * @param flexibleField a flexible field.
     */
    @Override
    protected void setValue(FlexibleField flexibleField) {
        if (flexibleField == null || flexibleField.getValue() == null) {
            return;
        }

        if (flexibleField instanceof JavaField) {
            super.setValue(flexibleField);

            return;
        }

        super.mergeCustomMappings(new SourceWrapper(flexibleField.getValue()), this, super.customMappings);
    }

}
