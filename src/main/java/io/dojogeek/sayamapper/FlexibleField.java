package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

public abstract class FlexibleField extends ManagementObject {

    private final static Logger LOGGER = Logger.getLogger(FlexibleField.class.getName());

    protected Field field;
    protected Object parentObject;
    protected boolean isIgnorable = false;
    protected IgnorableList ignorable;

    protected FlexibleField(Field field, Object parentObject) {
        this.field = field;
        this.parentObject = parentObject;
    }

    protected String getName() {
        return this.field.getName();
    }

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

    protected void setValue(Object value) {
        try {
            this.field.setAccessible(true);
            this.field.set(this.parentObject, value);
        } catch (IllegalAccessException | RuntimeException e) {
            LOGGER.info("An error occurred when assigning the value: " + value + " to field: " + this.field + "\n" + e.getMessage());
        }
    }

    protected void setValue(FlexibleField flexibleField) {
        this.setValue(flexibleField.getValue());
    }

    protected void ignore() {
        this.isIgnorable = true;
    }

    protected boolean isIgnorable() {
        return this.isIgnorable;
    }

    public void setIgnorable(IgnorableList ignorable) {
        this.ignorable = ignorable;
    }

    public IgnorableList getIgnorable() {
        return this.ignorable;
    }
}
