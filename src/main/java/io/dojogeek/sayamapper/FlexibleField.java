package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public abstract class FlexibleField extends ManagementObject {

    private final static Logger LOGGER = Logger.getLogger(FlexibleField.class.getName());

    protected Field field;
    protected Object declaringObject;
    protected boolean imIgnorable = false;
    protected String nestedFieldsToIgnore = "";

    protected FlexibleField(Field field, Object declaringObject) {
        this.field = field;
        this.declaringObject = declaringObject;
    }

    protected String getName() {
        return this.field.getName();
    }

    protected Object getValue() {
        Object value = null;

        try {
            this.field.setAccessible(true);
            value = this.field.get(this.declaringObject);
        } catch (IllegalAccessException e) {
            LOGGER.info("An error occurred when retrieving the value for: " + this.field + "\n" + e.getMessage());
        }

        return value;
    }

    protected void setValue(FlexibleField flexibleField) {
        try {
            this.field.setAccessible(true);
            this.field.set(this.declaringObject, flexibleField.getValue());
        } catch (IllegalAccessException | RuntimeException e) {
            LOGGER.info("An error occurred when assigning the value: " + flexibleField.getValue() + " to field: " + this.field + "\n" + e.getMessage());
        }
    }

    protected void ignore() {
        this.imIgnorable = true;
    }

    protected boolean isIgnorable() {
        return this.imIgnorable;
    }

    protected void setNestedFieldsToIgnore(String nestedFields) {
        this.nestedFieldsToIgnore = nestedFields;
    }

}
