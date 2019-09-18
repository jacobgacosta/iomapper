package dev.iomapper;

import java.util.ArrayList;
import java.util.List;

/**
 * IgnorableFields allows you add fields for ignore in mapping operation.
 *
 * @author Jacob G. Acosta
 */
public class IgnorableFields {

    public static String LASTED_DELETED = "";

    private List<IgnorableFieldPathShredder> ignorableFieldPathShredders = new ArrayList<>();

    /**
     * Adds the ignorable field.
     * <p>
     * The <b>fieldPath</b> value is treated as a path, is possible to ignore nested fields using the dot operator,
     * ex. rootField.firstNestedField.secondNestedField
     *
     * @param fieldPath the field path.
     * @return a <b>IgnorableFields</b> instance
     */
    public IgnorableFields ignore(String fieldPath) {
        this.ignorableFieldPathShredders.add(new IgnorableFieldPathShredder(fieldPath));

        return this;
    }

    /**
     * Check if the ignorable list is empty.
     *
     * @return a boolean
     */
    public boolean isEmpty() {
        return this.ignorableFieldPathShredders.isEmpty();
    }

    /**
     * Verifies if the ignorable list contains a root field matching <b>fieldName</b>.
     * <p>
     * The root field is the first name in the chained fields separated by dot operator.
     *
     * @param fieldName the field name
     * @return the boolean
     */
    public boolean containsTo(String fieldName) {
        for (IgnorableFieldPathShredder ignorableFieldPathShredder : this.ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.getRootField().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Remove the root field in a field path.
     * <p>
     * If the root field is the only one without nested fields, it's preserved in the list.
     *
     * @param fieldName the field name to remove
     */
    public void removeRootFieldWithName(String fieldName) {
        LASTED_DELETED = fieldName;

        for (IgnorableFieldPathShredder ignorableFieldPathShredder : this.ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.hasNestedFields() && ignorableFieldPathShredder.getRootField().equals(fieldName)) {
                ignorableFieldPathShredder.removeRootField();
            }
        }
    }

    /**
     * Checks if a field with the name equal to <b>fieldName</b> exist in the list.
     *
     * <b>fieldName</b> is compared with the root field in the field path added to ignorable list.
     *
     * @param fieldName the field name
     * @return the boolean
     */
    public boolean hasIgnorableNestedFor(String fieldName) {
        for (IgnorableFieldPathShredder ignorableFieldPathShredder : ignorableFieldPathShredders) {
            if (ignorableFieldPathShredder.getRootField().equals(fieldName) && ignorableFieldPathShredder.hasNestedFields()) {
                return true;
            }
        }

        return false;
    }

}
