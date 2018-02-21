package io.dojogeek.sayamapper;

import java.util.logging.Logger;

public class TargetObject extends ManagementObject {

    private final static Logger LOGGER = Logger.getLogger(TargetObject.class.getName());

    private Object target;
    private SourceObject source;
    private IgnorableList ignorableList;

    public <T> T getFilledInstanceOf(Class<T> targetClass) {
        try {
            this.target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + target + "\n" + e.getMessage());
        }

        this.populateFrom(this.source, this.ignorableList);

        return (T) this.target;
    }

    public void fillWith(SourceObject source) {
        this.source = source;
    }

    public void ignoreFieldsForMapping(IgnorableList ignorableList) {
        this.ignorableList = ignorableList;
    }

    public void applyCustomRelations(Customizable customizable) {
    }

    @Override
    protected InspectableObject getInspectableObject() {
        return new InspectableObject(this.target);
    }

}
