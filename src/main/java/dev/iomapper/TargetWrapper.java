package dev.iomapper;

import java.util.logging.Logger;

/**
 * TargetWrapper is a wrapper to handle easily the target class filling.
 *
 * @author Jacob G. Acosta
 */
public class TargetWrapper<T> extends MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(TargetWrapper.class.getName());

    private Class<T> target;
    private SourceWrapper sourceWrapper;
    private Object targetInstance;
    private CustomMappings customRelations = new CustomMappings();
    private IgnorableFields ignorableFields = new IgnorableFields();

    /**
     * <b>TargetWrapper</b> constructor.
     *
     * @param targetClass the target class.
     */
    public TargetWrapper(Class<T> targetClass) {
        this.target = targetClass;
    }

    /**
     * Sets a @see dev.iomapper.SourceWrapper object.
     *
     * @param sourceWrapper the source wrapper object.
     * @return a <b>TargetWrapper</b> object.
     */
    public TargetWrapper<T> populateWith(SourceWrapper sourceWrapper) {
        this.sourceWrapper = sourceWrapper;

        return this;
    }

    /**
     * Sets an @see dev.iomapper.IgnorableFields object.
     *
     * @param ignorableFields the ignorable fields object.
     * @return a <b>TargetWrapper</b> object
     */
    public TargetWrapper<T> ignore(IgnorableFields ignorableFields) {
        if (ignorableFields != null) {
            this.ignorableFields = ignorableFields;
        }

        return this;
    }

    /**
     * Sets a @see dev.iomapper.CustomMappings object.
     *
     * @param customizable the custom mappings object.
     * @return a <b>TargetWrapper</b> object
     */
    public TargetWrapper<T> relate(CustomMappings customizable) {
        if (customizable != null) {
            this.customRelations = customizable;
        }

        return this;
    }

    /**
     * Gets the filled target object.
     *
     * @return T the type of target object.
     */
    public T get() {
        try {
            this.targetInstance = this.target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.target + "\n" + e.getMessage());
        }

        super.merge(this.sourceWrapper, this.targetInstance, this.ignorableFields, this.customRelations);

        return (T) this.targetInstance;
    }

}
