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

        this.populateFrom(new SourceObject(flexibleField.getValue()));
    }

    @Override
    protected void populateFrom(SourceObject sourceObject) {
        FlexibleField matchedField = sourceObject.findMatchingFieldWithName(this.getName());

        if (matchedField != null) {
            this.setValue(matchedField);
        }
    }

    @Override
    protected InspectableObject getReferenceToManageableObject() {
        return new InspectableObject(declaringObject);
    }

}
