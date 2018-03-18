package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class ForeignField extends FlexibleField {

    private final static Logger LOGGER = Logger.getLogger(ForeignField.class.getName());

    public ForeignField(Field field, Object parentObject) {
        super(field, parentObject);
    }

    @Override
    protected void setValue(FlexibleField flexibleField) {
        try {
            Object instance = this.field.getType().newInstance();

            this.merge(instance, flexibleField);
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.field.getType() + "\n" + e.getMessage());
        }
    }

    private void merge(Object instance, FlexibleField flexibleField) {
        if (flexibleField instanceof JavaField) {
            FlexibleField sourceMatchedField = new SourceObject(instance).findMatchingFieldWithName(flexibleField.getName());

            if (sourceMatchedField != null && sourceMatchedField.getValue() != null) {
                flexibleField.setValue(sourceMatchedField);
            }

            return;
        }

        this.setValue(instance);

        super.merge(new SourceObject(flexibleField.getValue()), instance, this.getIgnorable(), null);
    }

}
