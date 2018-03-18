package io.dojogeek.sayamapper;

import java.util.logging.Logger;

public class TargetObject<T> extends ManagementObject {

    private final static Logger LOGGER = Logger.getLogger(TargetObject.class.getName());

    private Class<T> target;
    private SourceObject source;
    private CustomMapper customMapper;
    private IgnorableList ignorableFieldsToMerge;

    public TargetObject(Class<T> targetClass) {
        this.target = targetClass;
    }

    public T getFilledInstance() {
        Object target = null;

        try {
            target = this.target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.target + "\n" + e.getMessage());
        }

        super.merge(this.source, target, this.ignorableFieldsToMerge);

        return (T) target;
    }

    public void fillWith(SourceObject source) {
        this.source = source;
    }

    public void ignoreFieldsForMapping(IgnorableList ignorableList) {
        this.ignorableFieldsToMerge = ignorableList;
    }

    public void setCustomMapping(CustomMapper customFields) {
        this.customMapper = customFields;
    }

}
