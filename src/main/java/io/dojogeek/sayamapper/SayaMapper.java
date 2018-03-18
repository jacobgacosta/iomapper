package io.dojogeek.sayamapper;

public class SayaMapper implements Mapper {

    private SourceObject source;
    private IgnorableList fieldsToIgnore;

    @Override
    public <T> T to(Class<T> targetClass) {
        TargetObject<T> targetObject = new TargetObject(targetClass);
        targetObject.fillWith(this.source);
        targetObject.ignoreFieldsForMapping(this.fieldsToIgnore);

        return targetObject.getFilledInstance();
    }

    @Override
    public SayaMapper from(Object source) {
        this.source = new SourceObject(source);

        return this;
    }

    @Override
    public SayaMapper ignoring(Ignorable fieldList) {
        this.fieldsToIgnore = fieldList.ignore(new IgnorableList());

        return this;
    }

    @Override
    public SayaMapper relate(Customizable customizable) {

        return this;
    }

}
