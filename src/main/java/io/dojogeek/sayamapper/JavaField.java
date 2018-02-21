package io.dojogeek.sayamapper;

import java.lang.reflect.Field;

public class JavaField extends FlexibleField {

    public JavaField(Field field, Object declaringObject) {
        super(field, declaringObject);
    }

    @Override
    protected void setValue(FlexibleField flexibleField) {
        if (this.getClass().getTypeName().equals(flexibleField.getClass().getTypeName())) {
            super.setValue(flexibleField);
            return;
        }

        super.populateFrom(new SourceObject(flexibleField.getValue()), this.nestedFieldsToIgnore);
    }

    @Override
    protected InspectableObject getInspectableObject() {
        return new InspectableObject(declaringObject);
    }

}
