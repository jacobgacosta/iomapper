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
     * @param sourceObject    the source object wrapper.
     * @param target          the target instance.
     * @param ignorableFields a list with the unwanted target fields.
     * @param customMappings  a mapper with the custom relations for mapping.
     */
    protected void merge(SourceObject sourceObject, Object target, IgnorableFields ignorableFields, CustomMappings customMappings) {
        new InspectableObject(target)
                .getDeclaredFields()
                .forEach(targetField -> {
                    String targetFieldName = targetField.getName();

                    if (ignorableFields != null && ignorableFields.hasPresentTo(targetFieldName)) {
                        if (!ignorableFields.hasNestedFieldsFor(targetFieldName)) {
                            return;
                        }

                        ignorableFields.removeParentFieldWithName(targetFieldName);
                    }

                    if (customMappings != null &&
                            customMappings.hasCustomizable() &&
                            customMappings.hasTargetWithName(targetFieldName) &&
                            customMappings.hasSourceFor(targetFieldName)) {

                        this.mergeCustomMapping(sourceObject, targetField, customMappings);

                        return;
                    }

                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(targetFieldName);

                    if (sourceField != null) {
                        targetField.setIgnorableFields(ignorableFields);
                        targetField.setValue(sourceField);
                    }
                });
    }

    private void mergeCustomMapping(SourceObject sourceObject, FlexibleField targetField, CustomMappings customMappings) {
        CustomizableFieldPathShredder sourceMapping = customMappings.getSourceFor(targetField.getName());

        CustomizableFieldPathShredder targetMapping = customMappings.getTargetWithName(targetField.getName());

        if (sourceMapping.getRootType().equals(SINGLE)) {
            FlexibleField sourceField = sourceObject.getMatchingFieldFor(sourceMapping.getRootField());
            targetField.setValue(sourceField);
        } else if (sourceMapping.getRootType().equals(NESTED)) {
            FlexibleField sourceField = sourceObject.getMatchingFieldFor(sourceMapping.getRootField());

            if (sourceMapping.getRootType().equals(NESTED) && targetMapping.getRootType().equals(SINGLE)) {
                sourceMapping.removeRootField();

                targetField.setCustomMappings(customMappings);
                targetField.setValue(sourceField);

                return;
            }

            sourceMapping.removeRootField();
            targetMapping.removeRootField();

            targetField.setCustomMappings(customMappings);
            targetField.setValue(sourceField);
        } else if (sourceMapping.getRootType().equals(METHOD)) {
            String rootField = sourceMapping.getRootField();

            if (Determiner.isFunction(rootField)) {
                List<Object> sourceFields = new ArrayList<>();

                SignatureMethod signatureMethod = MethodShredder.dismantle(rootField);

                signatureMethod.getArgs().forEach(arg -> {
                    if (Determiner.isExtraArgument(arg)) {
                        sourceFields.add(arg);

                        return;
                    }

                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(arg);
                    sourceFields.add(sourceField.getValue());
                });

                Object result = Executor.executeFunction(signatureMethod.getName(), sourceFields);

                targetField.setValue(result);

                return;
            }

            FlexibleField sourceField = sourceObject.getMatchingFieldFor(rootField);

            sourceMapping.removeRootField();

            targetField.setCustomMappings(customMappings);
            targetField.setValue(sourceField);
        }

        if (targetMapping.hasOtherFields()) {
            targetMapping.removeRootField();
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
        if (source.getValue() == null) {
            return;
        }

        new InspectableObject(source.getValue())
                .getDeclaredFields()
                .forEach(sourceField -> {
                    if (customMappings != null && customMappings.hasCustomizable()) {
                        if (customMappings.hasSourceFor(target.getName()) && customMappings.hasSourceFieldWithName(sourceField.getName())) {
                            this.mergeCustomMapping(new SourceObject(source.getValue()), target, customMappings);

                            return;
                        }

                        if (customMappings.hasSourceFor(target.getName())
                                && customMappings.hasAnApplicableFunctonFor(sourceField.getName())) {
                            this.mergeCustomMapping(new SourceObject(source.getValue()), target, customMappings);

                            return;
                        }
                    }
                });
    }

}
