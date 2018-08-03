package io.dojogeek.sayamapper;

import java.util.HashMap;

/**
 * CustomMapper is a extension of an HashMap for the custom mapping fields.
 *
 * @author norvek
 */
public class CustomMapper extends HashMap<String, String> {

    /**
     * Add a relation source-target.
     *
     * @param sourceField the source field.
     * @param targetField ths target field.
     * @return a CustomMapper
     */
    public CustomMapper relate(String sourceField, String targetField) {
        super.put(sourceField, targetField);

        return this;
    }

    public boolean isElegible(String name) {
        return false;
    }

}
