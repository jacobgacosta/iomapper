package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class JavaField extends FlexibleField {

    private final static Logger LOGGER = Logger.getLogger(JavaField.class.getName());

    public JavaField(Field field, Object declaringObject) {
        super(field, declaringObject);
    }

    @Override
    protected void setValue(FlexibleField flexibleField) {
        if (flexibleField == null || flexibleField.getValue() == null) {
            return;
        }

        if (flexibleField instanceof JavaField) {
            super.setValue(flexibleField);
            return;
        }

        this.merge(flexibleField, this, this.customMappings);
    }

}
