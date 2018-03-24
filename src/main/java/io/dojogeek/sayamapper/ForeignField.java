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
        if (flexibleField == null || flexibleField.getValue() == null) {
            return;
        }

        this.setValue(instance);

        this.merge(new SourceObject(flexibleField.getValue()), instance, this.ignorableNestedFields, this.customMappings);
    }

}
