package dev.iomapper;

import dev.iomapper.parser.Function;

/**
 * MergeableObject allows to merge a source object with a target object.
 *
 * @author Jacob G. Acosta
 */
public abstract class MergeableObject {

    /**
     * Inspects the target object and iterate over its fields to compare each one and verify if it applies
     * in a custom mapping or ignorable fields.
     *
     * @param sourceWrapper  the source wrapper object.
     * @param target         the target object.
     * @param ignorable      the ignorable fields object.
     * @param customMappings the custom mappings object.
     */
    protected void merge(SourceWrapper sourceWrapper, Object target, IgnorableFields ignorable, CustomMappings customMappings) {
        new InspectableObject(target)
            .getDeclaredFields()
            .forEach(targetFlexibleField -> {
                String targetFieldName = targetFlexibleField.getName();

                if (!ignorable.isEmpty() && ignorable.containsTo(targetFieldName)) {
                    if (!ignorable.hasIgnorableNestedFor(targetFieldName)) {
                        return;
                    }

                    ignorable.removeRootFieldWithName(targetFieldName);

                    targetFlexibleField.setIgnorableFields(ignorable);
                }

                if ((customMappings.isNotEmpty() && customMappings.hasTargetFor(targetFieldName)) ||
                    customMappings.existInMultipleTargets(targetFieldName)) {
                    this.mergeCustomMappings(sourceWrapper, targetFlexibleField, customMappings);

                    return;
                }

                FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(targetFieldName);

                targetFlexibleField.setValue(sourceField);
            });
    }

    /**
     * Assigns the source values to the target fields defined in the custom mapping, including:
     * <p>
     * - Result of the execution functions
     * - Apply nested functions source fields
     * - Mapping to multiple target fields
     *
     * @param sourceWrapper  the source wrapper object.
     * @param targetField    the flexible field object.
     * @param customMappings the custom mappings object.
     */
    protected void mergeCustomMappings(SourceWrapper sourceWrapper, FlexibleField targetField, CustomMappings customMappings) {
        CustomizableFieldPathShredder sourcePath = customMappings.getSourceFor(targetField.getName());

        CustomizableFieldPathShredder targetPath = customMappings.getTargetWithName(targetField.getName());

        FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(sourcePath.getRootField());

        if (sourceField == null) {
            if ((sourceWrapper.getClassName().toLowerCase().equals(sourcePath.getRootField().toLowerCase()) ||
                sourceWrapper.getClassName().toLowerCase().contains(sourcePath.getRootField().toLowerCase())) &&
                targetField instanceof JavaField) {
                return;
            }
        }

        if (sourceField != null) {
            if (sourcePath.hasNestedFields()) {
                sourcePath.updateRootFieldWithNextField();
            }

            if (targetPath.hasNestedFields()) {
                targetPath.updateRootFieldWithNextField();
            } else {
                if (targetPath.getRootField().contains(Delimiters.COMMA_SEPARATOR)) {
                    String newRootTargetPath = targetPath.getRootField().replace(targetField.getName() + ",", "");

                    targetPath.setRoot(newRootTargetPath);
                }
            }

            targetField.setCustomMappings(customMappings);

            targetField.setValue(sourceField);

            return;
        }

        Function rootFunction = new Function(sourcePath.getRootField());

        if (rootFunction.hasNestedFunctions()) {
            rootFunction.getNestedFunctionsList().forEach(nestedFunction -> {
                nestedFunction.getArgumentsList().forEach(argument -> {
                    FlexibleField srcField = sourceWrapper.getMatchingFieldFor(argument);

                    if (srcField != null) {
                        Object value = srcField.getValue();

                        Class<?> type = srcField.getType();

                        String argumentsReplaced = nestedFunction.getArguments().replace(argument, value + "@" + type);

                        nestedFunction.setArguments(argumentsReplaced);
                    }
                });
            });
        } else {
            rootFunction.getSingleFunction().getArgumentsList().forEach(argument -> {
                FlexibleField srcField = sourceWrapper.getMatchingFieldFor(argument);

                if (srcField != null) {
                    Object value = srcField.getValue();

                    Class<?> type = srcField.getType();

                    String argumentsReplaced = rootFunction.getSingleFunction().getArguments().replace(argument, value + "@" + type);

                    rootFunction.getSingleFunction().setArguments(argumentsReplaced);
                }
            });
        }

        targetField.setValue(rootFunction.execute().getValue());
    }

}
