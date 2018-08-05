package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.List;

/**
 * UnwantedTargetList is a extension of an ArrayList for the ignorable fields.
 *
 * @author norvek
 */
public class IgnorableField {

    private List<String> ignorableList = new ArrayList<>();
    private List<FieldPath> fieldPaths = new ArrayList<>();

    /**
     * Add the name of the field to fill.
     *
     * @param fieldName the field name.
     * @return a <bold>UnwantedTargetList</bold> instance
     */
    public IgnorableField ignore(String fieldName) {
        ignorableList.add(fieldName);

        fieldPaths.add(new FieldPath(fieldName));

        return this;
    }

    public boolean hasRootFieldWithName(String fieldName) {
        for (FieldPath fieldPath : fieldPaths) {
            if (fieldPath.getRootField().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasNestedFieldsFor(String fieldName) {
        for (FieldPath fieldPath : fieldPaths) {
            if (fieldPath.getRootField().equals(fieldName) && fieldPath.hasMoreFields()) {
                return true;
            }
        }

        return false;
    }

    public void removeRootFieldsWithName(String fieldName) {
        for (FieldPath fieldPath : fieldPaths) {
            if (fieldPath.getRootField().equals(fieldName)) {
                fieldPath.removeRootField();
            }
        }
    }
}
