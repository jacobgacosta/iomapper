package io.dojogeek.sayamapper;

import java.util.List;
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
     * @param sourceObject       the source object wrapper.
     * @param target             the target instance.
     * @param unwantedTargetList a list with the unwanted target fields.
     * @param customMapper       a mapper with the custom relations for mapping.
     */
    protected void merge(SourceObject sourceObject, Object target, UnwantedTargetList unwantedTargetList, CustomMapper customMapper) {
        List<FlexibleField> targetFields = new InspectableObject(target).getDeclaredFields();

        targetFields.forEach(flexibleField -> {
            unwantedTargetList.get().forEach(item -> {
                if (item.getRootField().equals(flexibleField.getName())) {

                }
            });
        });
    }

    /**
     * Merge two fields taking into account the custom relations.
     *
     * @param source       the source object wrapper.
     * @param target       the target instance.
     * @param customMapper a mapper with the custom relations for mapping.
     */
    protected void merge(FlexibleField source, FlexibleField target, CustomMapper customMapper) {
        new InspectableObject(source.getValue())
                .getDeclaredFields()
                .forEach(flexibleField -> {

                });
    }

}
