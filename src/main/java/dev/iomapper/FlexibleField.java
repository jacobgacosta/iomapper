package dev.iomapper;

import dev.iomapper.exceptions.NullValueException;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * FlexibleField is a wrapper of a object property.
 *
 * @author norvek
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
     * Gets the name of property.
     */
    protected String getName() {
        return this.field.getName();
    }

    /**
     * Gets the value of property.
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
     * Set the value to property.
     *
     * @param value an object.
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
     * Set the value to property.
     *
     * @param flexibleField a flexible field.
     */
    protected void setValue(FlexibleField flexibleField) {
        if (flexibleField.getValue() == null) {
            throw new NullValueException("The value should not be null.");
        }

        this.setValue(flexibleField.getValue());
    }

    /**
     * Sets the ignorable list for the nested fields of this field.
     *
     * @param ignorableFields the ignorable list.
     */
    public void setIgnorableFields(IgnorableFields ignorableFields) {
        this.ignorableNestedFields = ignorableFields;
    }

    /**
     * Sets the custom mapper for the nested fields of this field.
     *
     * @param customMappings the custom mapper.
     */
    public void setCustomMappings(CustomMappings customMappings) {
        this.customMappings = customMappings;
    }

    public Class<?> getType() {
        return this.field.getType();
    }

}
