package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.List;

/**
 * UnwantedTargetList is a extension of an ArrayList for the ignorable fields.
 *
 * @author norvek
 */
public class UnwantedTargetList {

    private List<String> fieldPaths = new ArrayList<>();
    private List<FieldPath> rootFields = new ArrayList<>();

    /**
     * Add the name of the field to fill.
     *
     * @param fieldName the field name.
     * @return a <bold>UnwantedTargetList</bold> instance
     */
    public UnwantedTargetList ignore(String fieldName) {
        fieldPaths.add(fieldName);

        rootFields.add(new FieldPath(fieldName));

        return this;
    }

    public List<FieldPath> get() {
        return this.rootFields;
    }

}
