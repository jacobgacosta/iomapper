package io.dojogeek.sayamapper;

import java.util.logging.Logger;

public class TargetObject<T> extends MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(TargetObject.class.getName());

    private Class<T> target;

    public TargetObject(Class<T> targetClass) {
        this.target = targetClass;
    }

    public T getFilledInstanceFrom(SourceObject source) {
        Object target = null;

        try {
            target = this.target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.target + "\n" + e.getMessage());
        }

        super.merge(source, target, null, null);

        return (T) target;
    }

}
