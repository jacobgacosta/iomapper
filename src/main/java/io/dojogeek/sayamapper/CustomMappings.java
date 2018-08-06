package io.dojogeek.sayamapper;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomMapper is a extension of an HashMap for the custom mapping fields.
 *
 * @author norvek
 */
public class CustomMapper {

    private FlexibleField customValueTo;
    private Map<ShreddedFieldPath, ShreddedFieldPath> customMappings = new HashMap<>();

    /**
     * Add a relation source-target.
     *
     * @param sourceField the source field.
     * @param targetField ths target field.
     * @return a CustomMapper
     */
    public CustomMapper relate(String sourceField, String targetField) {
        customMappings.put(new ShreddedFieldPath(sourceField), new ShreddedFieldPath(targetField));

        return this;
    }

    public boolean hasCustomizable() {
        return !customMappings.isEmpty();
    }

    public boolean hasRootFieldWithName(String fieldName) {
        for (Map.Entry<ShreddedFieldPath, ShreddedFieldPath> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

}
