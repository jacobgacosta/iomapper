package io.dojogeek.sayamapper;

public class SourceObject extends ManagementObject {

    private Object source;

    public SourceObject(Object source) {
        this.source = source;
    }

    @Override
    protected InspectableObject getReferenceToManageableObject() {
        return new InspectableObject(this.source);
    }

}
