package io.dojogeek.sayamapper;

import io.dojogeek.sayamapper.utils.Executor;
import io.dojogeek.sayamapper.utils.MethodShredder;
import io.dojogeek.sayamapper.utils.SignatureMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.dojogeek.sayamapper.RootTypeEnum.*;

/**
 * MergeableObject allows to merge a source with a target object.
 *
 * @author norvek
 */
public abstract class MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(MergeableObject.class.getName());

    /**
     * Merge the source with the target object taking into account the ignorable fields and the custom relations.
     *
     * @param source   the source object wrapper.
     * @param target          the target instance.
     * @param ignorable a list with the unwanted target fields.
     * @param customMappings  a mapper with the custom relations for mapping.
     */
    protected void merge(SourceWrapper source, Object target, IgnorableFields ignorable, CustomMappings customMappings) {
        new InspectableObject(target)
                .getDeclaredFields()
                .forEach(targetField -> {
                    String targetFieldName = targetField.getName();

                    if (!ignorable.isEmpty() && ignorable.containsTo(targetFieldName)) {
                        if (!ignorable.hasIgnorableNestedFor(targetFieldName)) {
                            return;
                        }

                        ignorable.removeRootFieldWithName(targetFieldName);

                        targetField.setIgnorableFields(ignorable);
                    }

                    if (!customMappings.isEmpty() &&
                            customMappings.hasTargetWithName(targetFieldName) &&
                            customMappings.hasSourceFor(targetFieldName)) {

                        this.mergeCustomMapping(source, targetField, customMappings);

                        return;
                    }

                    FlexibleField sourceField = source.getMatchingFieldFor(targetFieldName);

                    targetField.setValue(sourceField);
                });
    }

    private void mergeCustomMapping(SourceWrapper sourceWrapper, FlexibleField targetField, CustomMappings customMappings) {
        CustomizableFieldPathShredder sourcePath = customMappings.getSourceFor(targetField.getName());

        CustomizableFieldPathShredder targetPath = customMappings.getTargetWithName(targetField.getName());

        if (sourcePath.getRootType().equals(SINGLE)) {
            FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(sourcePath.getRootField());
            targetField.setValue(sourceField);
        } else if (sourcePath.getRootType().equals(NESTED)) {
            FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(sourcePath.getRootField());

            if (targetPath.getRootType().equals(SINGLE)) {
                sourcePath.removeRootField();

                targetField.setCustomMappings(customMappings);
                targetField.setValue(sourceField);

                return;
            }

            sourcePath.removeRootField();
            targetPath.removeRootField();

            targetField.setCustomMappings(customMappings);
            targetField.setValue(sourceField);
        } else if (sourcePath.getRootType().equals(METHOD)) {
            String rootField = sourcePath.getRootField();

            if (Determiner.isFunction(rootField)) {
                List<Object> sourceFields = new ArrayList<>();

                SignatureMethod signatureMethod = MethodShredder.dismantle(rootField);

                signatureMethod.getArgs().forEach(arg -> {
                    if (Determiner.isExtraArgument(arg)) {
                        sourceFields.add(arg);

                        return;
                    }

                    FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(arg);
                    sourceFields.add(sourceField.getValue());
                });

                Object result = Executor.executeFunction(signatureMethod.getName(), sourceFields);

                targetField.setValue(result);

                return;
            }

            FlexibleField sourceField = sourceWrapper.getMatchingFieldFor(rootField);

            sourcePath.removeRootField();

            targetField.setCustomMappings(customMappings);
            targetField.setValue(sourceField);
        }

        if (targetPath.hasOtherFields()) {
            targetPath.removeRootField();
        }
    }

    /**
     * Merge two fields taking into account the custom relations.
     *
     * @param source         the source object wrapper.
     * @param target         the target instance.
     * @param customMappings a mapper with the custom relations for mapping.
     */
    protected void merge(FlexibleField source, FlexibleField target, CustomMappings customMappings) {
        if (source == null || source.getValue() == null) {
            return;
        }

        new InspectableObject(source.getValue())
                .getDeclaredFields()
                .forEach(sourceField -> {
                    if (customMappings != null && !customMappings.isEmpty()) {
                        if (customMappings.hasSourceFor(target.getName()) && customMappings.hasSourceFieldWithName(sourceField.getName())) {
                            this.mergeCustomMapping(new SourceWrapper(source.getValue()), target, customMappings);

                            return;
                        }

                        if (customMappings.hasSourceFor(target.getName())
                                && customMappings.hasAnApplicableFunctonFor(sourceField.getName())) {
                            this.mergeCustomMapping(new SourceWrapper(source.getValue()), target, customMappings);

                            return;
                        }
                    }
                });
    }

}
