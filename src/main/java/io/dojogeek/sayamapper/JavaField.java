package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * JavaField is a wrapper of a java property.
 *
 * @author norvek
 */
public class JavaField extends FlexibleField {

    private final static Logger LOGGER = Logger.getLogger(JavaField.class.getName());

    /**
     * JavaField constructor.
     *
     * @param field            the field.
     * @param declaringObject  the reference object that hosts the field.
     */
    public JavaField(Field field, Object declaringObject) {
        super(field, declaringObject);
    }

    /**
     * Set the value to property.
     *
     * @param flexibleField  a flexible field.
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

        this.merge(flexibleField, this, this.ignorableNestedFields, this.customMappings);
    }

}
