package dev.iomapper;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomMappings allow you specify the custom mapping between source/target objects.
 *
 * @author Jacob G. Acosta
 */
public class CustomMappings {

    private Map<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMappings = new HashMap<>();

    /**
     * Adds a relation source/target field names.
     * <p>
     * The name of the fields is treated as a path to the candidate field for mapping,
     * where the level of nesting is determined by the number of fields that precede it,
     * separated by the dot operator as would be seen in a string of Java methods. If the field doesn't belong to a
     * nesting the name is specified as it appears on the first level level of the host object.
     *
     * @param sourceFieldPath the source field path.
     * @param targetFieldPath the target field path.
     * @return a CustomMappings
     */
    public CustomMappings relate(String sourceFieldPath, String targetFieldPath) {
        customMappings.put(new CustomizableFieldPathShredder(sourceFieldPath), new CustomizableFieldPathShredder(targetFieldPath));

        return this;
    }

    /**
     * Verifies if the customizable mapping relations has one target field with the specified name.
     * <p>
     * Since the fields added to a custom mapping are treated as a path, the <b>targetRootFieldName</b> will be compared whit
     * the root field.
     * <p>
     * In a field path, the root field is the first name in the chained fields separated by dot operator
     * ex. rootField.firstNestedField.secondNestedField, in this case <b>targetRootFieldName</b> will be compared with <b>rootField</b>.
     * <p>
     * If no nesting exist, the comparison is about the first level field.
     * <p>
     * In this case <b>targetRootFieldName</b> is the root field name, no the full chained path, taking the previous example,
     * <b>rootField</b> is de argument provided for comparision, no the <i>rootField.firstNestedField.secondNestedField</i>
     *
     * @param targetRootFieldName the target root field name.
     * @return boolean boolean
     */
    public boolean hasTargetFor(String targetRootFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (!customMapping.getValue().getRootField().isEmpty() && customMapping.getValue().getRootField().trim().equals(targetRootFieldName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifies if a target field name is present in a group of target fields separated by comma, because
     * the customizable mapping allows mapping a source field to multiple target fields on the same level.
     * <p>
     * Ex. sourceField -> targetField1, targetField2
     * <p>
     * The sourceField field in the previous example is mapped to targetField1 and targetField2 fields.
     * <p>
     * In this case, <b>fieldName</b> is compared to all fields in the comma separated group.
     *
     * @param fieldName the target root field name
     * @return the boolean
     */
    public boolean existInMultipleTargets(String fieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (!customMapping.getValue().getRootField().isEmpty()) {
                String[] multipleFields = customMapping.getValue().getRootField().split(Delimiters.COMMA_SEPARATOR);

                for (String field : multipleFields) {
                    if (field.equals(fieldName)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Gets the source field full path for the specified <b>targetRootFieldName</b>.
     * <p>
     * The <b>targetRootFieldName</b> is the root field name, no the target full path.
     *
     * @param targetRootFieldName the target root field name
     * @return a @see dev.iomapper.CustomizableFieldPathShredder
     */
    public CustomizableFieldPathShredder getSourceFor(String targetRootFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().trim().equals(targetRootFieldName) ||
                customMapping.getValue().getRootField().split(Delimiters.COMMA_SEPARATOR)[0].equals(targetRootFieldName)) {
                return customMapping.getKey();
            }
        }

        return new CustomizableFieldPathShredder(targetRootFieldName);
    }

    /**
     * Gets the target field full path for the specified <b>targetRootFieldName</b>.
     * <p>
     * The <b>targetRootFieldName</b> is the root field name, no the target full path.
     *
     * @param targetRootFieldName the target root field name
     * @return a @see dev.iomapper.CustomizableFieldPathShredder
     */
    public CustomizableFieldPathShredder getTargetWithName(String targetRootFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().trim().equals(targetRootFieldName) ||
                customMapping.getValue().getRootField().split(Delimiters.COMMA_SEPARATOR)[0].equals(targetRootFieldName)) {
                return customMapping.getValue();
            }
        }

        return new CustomizableFieldPathShredder(targetRootFieldName);
    }

    /**
     * Checks if <b>CustomMappings</b> object has customizable fields for mapping
     *
     * @return the boolean
     */
    public boolean isNotEmpty() {
        return !this.customMappings.isEmpty();
    }

}
