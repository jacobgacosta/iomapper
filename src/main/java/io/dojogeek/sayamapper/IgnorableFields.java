package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.List;

/**
 * UnwantedTargetList is a extension of an ArrayList for the ignorable fields.
 *
 * @author norvek
 */
public class IgnorableFields {

    private List<String> ignorableList = new ArrayList<>();
    private List<IgnorableFieldPathShredder> ignorableFieldPathShredders = new ArrayList<>();

    /**
     * Add the name of the field to fill.
     *
     * @param fieldName the field name.
     * @return a <bold>UnwantedTargetList</bold> instance
     */
    public IgnorableFields ignore(String fieldName) {
        ignorableList.add(fieldName);

        ignorableFieldPathShredders.add(new IgnorableFieldPathShredder(fieldName));

        return this;
    }

    public boolean hasPresentTo(String fieldName) {
        for (IgnorableFieldPathShredder ignorableFieldPathShredder : ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.getRootField().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasNestedFieldsFor(String fieldName) {
        for (IgnorableFieldPathShredder ignorableFieldPathShredder : ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.getRootField().equals(fieldName) && ignorableFieldPathShredder.hasNestedFields()) {
                return true;
            }
        }

        return false;
    }

    public void removeParentFieldWithName(String fieldName) {
        for (IgnorableFieldPathShredder ignorableFieldPathShredder : ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.getRootField().equals(fieldName)) {
                ignorableFieldPathShredder.removeRootField();
            }
        }
    }

    public boolean isEmpty() {
        return ignorableList.isEmpty();
    }

}
