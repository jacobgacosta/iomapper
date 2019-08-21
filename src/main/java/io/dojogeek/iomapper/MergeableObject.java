package io.dojogeek.iomapper;

import io.dojogeek.parser.Function;

import java.util.logging.Logger;

import static io.dojogeek.iomapper.RootTypeEnum.*;

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

                    FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(targetFieldName);

                    targetFlexibleField.setValue(sourceField);

                    if (!customMappings.isEmpty() &&
                            customMappings.hasTargetWithName(targetFieldName) &&
                            customMappings.hasTargetFor(targetFieldName)) {

                        this.mergeCustomMappings(sourceWrapper, targetFlexibleField, customMappings);

                        return;
                    }
                });
    }

    protected void mergeCustomMappings(SourceWrapper sourceWrapper, FlexibleField targetField, CustomMappings customMappings) {
        if (customMappings.isEmpty()) {
            return;
        }

        CustomizableFieldPathShredder sourcePath = customMappings.getSourceFor(targetField.getName());

        CustomizableFieldPathShredder targetPath = customMappings.getTargetWithName(targetField.getName());

        FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(sourcePath.getRootField());

        if (sourcePath.getType().equals(NESTED_FIELD)) {
            sourcePath.removeRootField();

            targetField.setCustomMappings(customMappings);

            if (targetPath.getType().equals(SINGLE)) {
                targetField.setValue(sourceField);

                return;
            }

            targetPath.removeRootField();
        } else if (sourcePath.getType().equals(SINGLE_METHOD)) {
            String rootField = sourcePath.getRootField();

            if (Determiner.isFunction(rootField)) {
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
                }

                rootFunction.getSingleFunction().getArgumentsList().forEach(argument -> {
                    FlexibleField srcField = sourceWrapper.getMatchingFieldFor(argument);

                    if (srcField != null) {
                        Object value = srcField.getValue();

                        Class<?> type = srcField.getType();

                        String argumentsReplaced = rootFunction.getSingleFunction().getArguments().replace(argument, value + "@" + type);

                        rootFunction.getSingleFunction().setArguments(argumentsReplaced);
                    }
                });

                targetField.setValue(rootFunction.execute().getValue());

                return;
            }

            sourcePath.removeRootField();

            targetField.setCustomMappings(customMappings);
            targetField.setValue(sourceField);

            return;
        } else if (sourcePath.getType().equals(NESTED_METHOD)) {
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
            }

            targetField.setValue(rootFunction.execute().getValue());

            return;
        }

        targetField.setValue(sourceField);

        if (targetPath.hasOtherFields()) {
            targetPath.removeRootField();
        }
    }

}
