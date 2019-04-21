package io.dojogeek.sayamapper;

import io.dojogeek.sayamapper.exceptions.NullValueException;

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
        if (flexibleField == null || flexibleField.getValue() == null) {
            return;
        }

        try {
            Object instance = this.field.getType().newInstance();

            this.merge(instance, flexibleField);
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.field.getType() + "\n" + e.getMessage());
        }
    }

    /**
     * Merge an object instance.
     *
     * @param instance      an object instance.
     * @param flexibleField a flexible field.
     */
    private void merge(Object instance, FlexibleField flexibleField) {
        this.setValue(instance);

        this.merge(new SourceWrapper(flexibleField.getValue()), instance, this.ignorableNestedFields, this.customMappings);
    }

}
