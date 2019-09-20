package dev.iomapper;

import dev.iomapper.exceptions.NullValueException;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * <b>FlexibleField</b> helps to manipulate the <b>Field</b> objects.
 * <p>
 * Also keeps the list of @see dev.iomapper.CustomMappings and @see dev.iomapper.IgnorableFields applicable to
 * the wrapped field.
 *
 * @author Jacob G. Acosta
 */
public abstract class FlexibleField extends MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(FlexibleField.class.getName());

    protected Field field;
    protected CustomMappings customMappings = new CustomMappings();
    protected IgnorableFields ignorableNestedFields = new IgnorableFields();
    private Object parentObject;

    /**
     * FlexibleField constructor.
     *
     * @param field        the field.
     * @param parentObject the reference object that hosts the field.
     */
    protected FlexibleField(Field field, Object parentObject) {
        this.field = field;
        this.parentObject = parentObject;
    }

    /**
     * Gets the name of the wrapped field.
     *
     * @return the name
     */
    protected String getName() {
        return this.field.getName();
    }

    /**
     * Gets the value of the wrapped field.
     *
     * @return the value
     */
    protected Object getValue() {
        Object value = null;

        try {
            this.field.setAccessible(true);
            value = this.field.get(this.parentObject);
        } catch (IllegalAccessException e) {
            LOGGER.info("An error occurred when retrieving the value for: " + this.field + "\n" + e.getMessage());
        }

        return value;
    }

    /**
     * Sets a value directly to the wrapped field.
     *
     * @param value the value.
     */
    protected void setValue(Object value) {
        if (value == null) {
            throw new NullValueException("The value should not be null.");
        }

        try {
            this.field.setAccessible(true);
            this.field.set(this.parentObject, value);
        } catch (IllegalAccessException e) {
            LOGGER.info("An error occurred when assigning the value: " + value + " to field: " + this.field + "\n");
        } catch (IllegalArgumentException e) {
            LOGGER.info("An error occurred when assigning the value: " + value + " to field: " + this.field + "\n");
        }
    }

    /**
     * Sets a flexible field that contains the value to set for.
     *
     * @param flexibleField the flexible field.
     */
    protected void setValue(FlexibleField flexibleField) {
        if (flexibleField.getValue() == null) {
            throw new NullValueException("The value should not be null.");
        }

        this.setValue(flexibleField.getValue());
    }

    /**
     * Sets the ignorable list applicable for the wrapped field.
     *
     * @param ignorableFields the ignorable list.
     */
    public void setIgnorableFields(IgnorableFields ignorableFields) {
        this.ignorableNestedFields = ignorableFields;
    }

    /**
     * Sets the custom mappings applicable for the wrapped field.
     *
     * @param customMappings the custom mapper.
     */
    public void setCustomMappings(CustomMappings customMappings) {
        this.customMappings = customMappings;
    }


    /**
     * Gets the type of the wrapped field.
     *
     * @return the type
     */
    public Class<?> getType() {
        return this.field.getType();
    }

}
