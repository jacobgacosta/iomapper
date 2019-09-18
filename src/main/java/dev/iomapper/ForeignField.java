package dev.iomapper;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * ForeignField is a wrapper for non-Java fields.
 * <p>
 * A source field or target field can be of this type and be wrapped to help in the custom mappings.
 *
 * @author Jacob G. Acosta
 */
public class ForeignField extends FlexibleField {

    private final static Logger LOGGER = Logger.getLogger(ForeignField.class.getName());

    /**
     * ForeignField constructor.
     *
     * @param field        the field.
     * @param parentObject the reference object that hosts the field.
     */
    public ForeignField(Field field, Object parentObject) {
        super(field, parentObject);
    }

    /**
     * Creates instances for non-Java objects and apply the merge operation to
     * apply the custom mappings.
     *
     * @param flexibleField a flexible field.
     */
    @Override
    protected void setValue(FlexibleField flexibleField) {
        if (flexibleField == null ||
            flexibleField.getValue() == null ||
            flexibleField instanceof JavaField) {
            return;
        }

        try {
            Object instance = super.field.getType().newInstance();

            super.setValue(instance);

            super.merge(new SourceWrapper(flexibleField.getValue()), instance, this.ignorableNestedFields, this.customMappings);
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.field.getType() + "\n" + e.getMessage());
        }
    }

}
