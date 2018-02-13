package io.dojogeek.sayamapper;

import java.util.logging.Logger;

public class TargetObject extends ManagementObject {

    private final static Logger LOGGER = Logger.getLogger(TargetObject.class.getName());

    private Object target;
    private SourceObject source;

    public <T> T getFilledInstanceOf(Class<T> clazz) {
        try {
            this.target = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + target + "\n" + e.getMessage());
        }

        this.populateFrom(this.source);

        return (T) this.target;
    }

    public void setSourceObject(SourceObject source) {
        this.source = source;
    }

    @Override
    protected InspectableObject getReferenceToManageableObject() {
        return new InspectableObject(this.target);
    }

}
