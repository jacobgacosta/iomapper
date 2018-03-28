package io.dojogeek.sayamapper;

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
    private Object parentObject;
    protected CustomMapper customMappings;
    protected UnwantedTargetList ignorableNestedFields;

    /**
     * FlexibleField constructor.
     *
     * @param field         the field.
     * @param parentObject  the reference object that hosts the field.
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
     * @param flexibleField  a flexible field.
     */
    protected void setValue(FlexibleField flexibleField) {
        this.setValue(flexibleField.getValue());
    }

    /**
     * Set the value to property.
     *
     * @param value  an object.
     */
    protected void setValue(Object value) {
        try {
            this.field.setAccessible(true);
            this.field.set(this.parentObject, value);
        } catch (IllegalAccessException | RuntimeException e) {
            LOGGER.info("An error occurred when assigning the value: " + value + " to field: " + this.field + "\n" + e.getMessage());
        }
    }

    /**
     * Sets the ignorable list for the nested fields of this field.
     *
     * @param unwantedTargetList  the ignorable list.
     */
    public void setIgnorableFields(UnwantedTargetList unwantedTargetList) {
        this.ignorableNestedFields = unwantedTargetList;
    }

    /**
     * Sets the custom mapper for the nested fields of this field.
     *
     * @param customMappings  the custom mapper.
     */
    public void setCustomMappings(CustomMapper customMappings) {
        this.customMappings = customMappings;
    }

}
