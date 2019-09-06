package dev.iomapper;

import dev.iomapper.parser.Function;
import dev.iomapper.utils.Delimiters;

import java.util.logging.Logger;

/**
 * MergeableObject allows to mergeCustomMappings a source with a target object.
 *
 * @author norvek
 */
public abstract class MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(MergeableObject.class.getName());

    /**
     * Merge the source with the target object taking into account the ignorable fields and the custom relations.
     *
     * @param sourceWrapper  the source object wrapper.
     * @param target         the target instance.
     * @param ignorable      a list with the unwanted target fields.
     * @param customMappings a mapper with the custom relations for mapping.
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

                if ((customMappings.isNotEmpty() && customMappings.hasTargetWithName(targetFieldName)) ||
                    customMappings.existInMultiple(targetFieldName)) {
                    this.mergeCustomMappings(sourceWrapper, targetFlexibleField, customMappings);

                    return;
                }

                FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(targetFieldName);

                targetFlexibleField.setValue(sourceField);
            });
    }

    protected void mergeCustomMappings(SourceWrapper sourceWrapper, FlexibleField targetField, CustomMappings customMappings) {
        CustomizableFieldPathShredder targetPath = customMappings.getTargetWithName(targetField.getName());

        CustomizableFieldPathShredder sourcePath = customMappings.getSourceFor(targetField.getName());

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
                sourcePath.updateRootWithNextField();
            }

            if (targetPath.hasNestedFields()) {
                targetPath.updateRootWithNextField();
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
            rootFunction.getNestedFunctionList().forEach(nestedFunction -> {
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
