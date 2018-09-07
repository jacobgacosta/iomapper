package io.dojogeek.sayamapper;

import java.util.logging.Logger;

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

                    if (ignorableFields != null && ignorableFields.hasRootFieldWithName(targetFieldName)) {
                        if (!ignorableFields.hasNestedFieldsFor(targetFieldName)) {
                            return;
                        }

                        ignorableFields.removeRootFieldsWithName(targetFieldName);
                    }

                    if (customMappings != null && customMappings.hasCustomizable() && customMappings.hasSourceMappingFor(targetFieldName)) {
                        customMappings.applyMapping(sourceObject, targetField);

                        return;
                    }

                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(targetFieldName);

                    if (sourceField != null) {
                        targetField.setIgnorableFields(ignorableFields);
                        targetField.setValue(sourceField);
                    }
                });
    }

    /**
     * Merge two fields taking into account the custom relations.
     *
     * @param source         the source object wrapper.
     * @param target         the target instance.
     * @param customMappings a mapper with the custom relations for mapping.
     */
    protected void merge(FlexibleField source, FlexibleField target, CustomMappings customMappings) {
        new InspectableObject(source.getValue())
                .getDeclaredFields()
                .forEach(sourceField -> {
                    if (customMappings != null && customMappings.hasCustomizable()) {
                        if (customMappings.hasSourceMappingFor(target.getName()) && customMappings.hasSourceFieldWithName(sourceField.getName())) {
                            customMappings.applyMapping(new SourceObject(source.getValue()), target);

                            return;
                        }

                        if (customMappings.hasSourceMappingFor(target.getName())
                                && customMappings.hasAnApplicableFunctonFor(sourceField.getName())) {
                            customMappings.applyMapping(new SourceObject(source.getValue()), target);

                            return;
                        }
                    }
                });
    }

}
