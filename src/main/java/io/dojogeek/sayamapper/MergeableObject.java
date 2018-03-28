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
     * @param sourceObject        the source object wrapper.
     * @param target              the target instance.
     * @param unwantedTargetList  a list with the unwanted target fields.
     * @param customMapper        a mapper with the custom relations for mapping.
     */
    protected void merge(SourceObject sourceObject, Object target, UnwantedTargetList unwantedTargetList, CustomMapper customMapper) {
        new InspectableObject(target)
                .getDeclaredFields()
                .forEach(flexibleField -> {
                    if (unwantedTargetList != null && !unwantedTargetList.isEmpty()) {
                        if (unwantedTargetList.hasPresentTo(flexibleField.getName())) {
                            if (!unwantedTargetList.hasIgnorableNestedFor(flexibleField.getName())) {
                                unwantedTargetList.removeTo(flexibleField.getName());
                                return;
                            }

                            unwantedTargetList.removeTo(flexibleField.getName());
                            flexibleField.setIgnorableFields(unwantedTargetList);
                        }
                    }

                    if (customMapper != null && !customMapper.isEmpty()) {
                        if (!customMapper.hasATargetFor(flexibleField.getName())) {
                            return;
                        }

                        String sourceField = customMapper.getSourceFor(flexibleField.getName());
                        FlexibleField matchedField = sourceObject.findMatchingFor(sourceField);
                        customMapper.removeRootFields(sourceField, flexibleField.getName());
                        flexibleField.setCustomMappings(customMapper);
                        flexibleField.setValue(matchedField);

                        return;
                    }

                    FlexibleField matchedField = sourceObject.findMatchingFor(flexibleField.getName());
                    flexibleField.setValue(matchedField);
                });
    }

    /**
     * Merge two fields taking into account the custom relations.
     *
     * @param source        the source object wrapper.
     * @param target        the target instance.
     * @param customMapper  a mapper with the custom relations for mapping.
     */
    protected void merge(FlexibleField source, FlexibleField target, CustomMapper customMapper) {
        new InspectableObject(source.getValue())
                .getDeclaredFields()
                .forEach(flexibleField -> {
                    if (!customMapper.hasASourceFor(flexibleField.getName())) {
                        return;
                    }

                    if (customMapper.hasNestedSourceFieldsFor(flexibleField.getName())) {
                        customMapper.removeRootFields(source.getName(), target.getName());
                        flexibleField.setCustomMappings(customMapper);
                    }

                    target.setValue(flexibleField);
                });
    }

}
