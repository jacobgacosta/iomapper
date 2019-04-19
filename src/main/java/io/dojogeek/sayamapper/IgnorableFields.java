package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.List;

/**
 * UnwantedTargetList is a extension of an ArrayList for the ignorable fields.
 *
 * @author norvek
 */
public class IgnorableFields {

    public static String LASTED_DELETED = "";

    private List<IgnorableFieldPathShredder> ignorableFieldPathShredders = new ArrayList<>();

    /**
     * Add the name of the field to fill.
     *
     * @param fieldName the field name.
     * @return a <bold>UnwantedTargetList</bold> instance
     */
    public IgnorableFields ignore(String fieldName) {
        this.ignorableFieldPathShredders.add(new IgnorableFieldPathShredder(fieldName));

        return this;
    }

    public boolean isEmpty() {
        return this.ignorableFieldPathShredders.isEmpty();
    }

    public boolean containsTo(String fieldName) {
        for (IgnorableFieldPathShredder ignorableFieldPathShredder : this.ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.getRootField().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    public void markAsIgnoredTo(String fieldName) {
        LASTED_DELETED = fieldName;

        for (IgnorableFieldPathShredder ignorableFieldPathShredder : this.ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.hasNestedFields() && ignorableFieldPathShredder.getRootField().equals(fieldName)) {
                ignorableFieldPathShredder.removeRootField();
            }
        }
    }

    public boolean hasIgnorableNestedFor(String fieldName) {
        for (IgnorableFieldPathShredder ignorableFieldPathShredder : ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.getRootField().equals(fieldName) && ignorableFieldPathShredder.hasNestedFields()) {
                return true;
            }
        }

        return false;
    }

}
