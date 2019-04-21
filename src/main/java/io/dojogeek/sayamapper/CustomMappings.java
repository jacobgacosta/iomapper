package io.dojogeek.sayamapper;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomMappings is a extension of an HashMap for the custom mapping fields.
 *
 * @author norvek
 */
public class CustomMappings {

    private Map<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMappings = new HashMap<>();

    /**
     * Add a relation source-target.
     *
     * @param sourceFieldPath the source field.
     * @param targetFieldPath ths target field.
     * @return a CustomMapper
     */
    public CustomMappings relate(String sourceFieldPath, String targetFieldPath) {
        customMappings.put(new CustomizableFieldPathShredder(sourceFieldPath), new CustomizableFieldPathShredder(targetFieldPath));

        return this;
    }

    public boolean hasTargetFor(String targetFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (!customMapping.getValue().getRootField().isEmpty() && customMapping.getValue().getRootField().trim().equals(targetFieldName)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasAnApplicableFunctonFor(String name) {
        String[] split = {};

        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (Determiner.isFunction(customMapping.getKey().getRootField())) {
                split = customMapping.getKey().getRootField().split("\\(|,|\\)");
            }
        }

        for (String value : split) {
            if (value.trim().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasSourceFieldWithName(String name) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getKey().getRootField().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public Map<CustomizableFieldPathShredder, CustomizableFieldPathShredder> getRelations() {
        return customMappings;
    }

    public boolean hasTargetWithName(String targetFieldName) {
        return this.hasTargetFor(targetFieldName);
    }

    public CustomizableFieldPathShredder getSourceFor(String targetFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().trim().equals(targetFieldName)) {
                return customMapping.getKey();
            }
        }

        return new CustomizableFieldPathShredder("");
    }

    public CustomizableFieldPathShredder getTargetWithName(String targetFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().trim().equals(targetFieldName)) {
                return customMapping.getValue();
            }
        }

        return new CustomizableFieldPathShredder("");
    }

    public boolean isEmpty() {
        return this.customMappings.isEmpty();
    }

}
