package dev.iomapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * IgnorableFieldPathShredder split the field path to a field list.
 * <p>
 * Created by Jacob G. Acosta.
 */
public class IgnorableFieldPathShredder {

    private static final int SINGLE_FIELD = 1;

    private List<String> fields = new ArrayList<>();

    /**
     * Sets the field path and split it to be stored in a list.
     *
     * @param fieldPath the field path
     */
    public IgnorableFieldPathShredder(String fieldPath) {
        Arrays
            .asList(fieldPath.split(Delimiters.DOT_SEPARATOR))
            .forEach(field -> fields.add(field));
    }

    /**
     * Gets the root field.
     *
     * @return the root field
     */
    public String getRootField() {
        return fields.get(0);
    }

    /**
     * Checks if a field path contains nested fields.
     * <p>
     * A path with nested fields, it's said to have nested fields if contains the
     * format field1.field2 Names separated by dot operator.
     *
     * @return the boolean
     */
    public boolean hasNestedFields() {
        return fields.size() > SINGLE_FIELD;
    }

    /**
     * Remove the root field in a nested field path.
     */
    public void removeRootField() {
        fields.remove(0);
    }

}
