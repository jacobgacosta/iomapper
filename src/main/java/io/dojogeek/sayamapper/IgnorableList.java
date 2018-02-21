package io.dojogeek.sayamapper;

import java.util.ArrayList;

public class IgnorableList extends ArrayList<String> {

    private static final int SINGLE_FIELD = 1;

    public IgnorableList ignore(String fieldName) {
        super.add(fieldName);

        return this;
    }

    public boolean hasFieldNamed(String field) {
        for (String fieldName : this) {
            if (field.split("\\.").length == SINGLE_FIELD &&
                    (fieldName.equals(field) || fieldName.contains(field))) {
                return true;
            }
        }

        return false;
    }

    public String findNestedIgnorableFor(String name) {
        String firstField = "";
        String nestedFields = "";

        for (String fieldName : this) {
            if (fieldName.split("\\.").length > SINGLE_FIELD) {
                firstField = fieldName.substring(0, fieldName.indexOf("."));
            }


            if (firstField.equals(name) || firstField.contains(name)) {
                nestedFields = fieldName.substring(fieldName.indexOf("."));
            }
        }

        return nestedFields;
    }
}
