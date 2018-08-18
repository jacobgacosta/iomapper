package io.dojogeek.sayamapper;

import java.util.logging.Logger;

/**
 * TargetObject is a wrapper to handle easily the target class filling.
 *
 * @author norvek
 */
public class TargetObject<T> extends MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(TargetObject.class.getName());

    private Class<T> target;
    private CustomMappings customRelations;
    private IgnorableFields ignorableFields;

    /**
     * TargetObject constructor.
     *
     * @param targetClass  the target class.
     */
    public TargetObject(Class<T> targetClass) {
        this.target = targetClass;
    }

    /**
     * Returns a target filled instance from source object.
     *
     * @param source  the source object wrapper.
     * @return        a filled target instance.
     */
    public T getFilledInstanceFrom(SourceObject source) {
        Object target = null;

        try {
            target = this.target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.target + "\n" + e.getMessage());
        }

        super.merge(source, target, this.ignorableFields, this.customRelations);

        return (T) target;
    }

    /**
     * Sets a list of fields to fill.
     *
     * @param ignorableFields  a list of fields to fill.
     */
    public void ignore(IgnorableFields ignorableFields) {
        this.ignorableFields = ignorableFields;
    }

    /**
     * Sets a fill of the custom relations mapping.
     *
     * @param customMappings  a fill with the custom relations.
     */
    public void relate(CustomMappings customMappings) {
        this.customRelations = customMappings;
    }

}
