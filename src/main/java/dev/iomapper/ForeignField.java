package dev.iomapper;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * JavaField is a wrapper of a foreign property.
 *
 * @author norvek
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
     * Sets the value to property.
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
