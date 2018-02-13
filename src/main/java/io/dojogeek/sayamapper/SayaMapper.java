package io.dojogeek.sayamapper;

public class SayaMapper implements Mapper {

    private Ignorable toIgnore;
    private Customizable customRelations;
    private TargetObject targetObject = new TargetObject();

    @Override
    public <T> T to(Class<T> clazz) {
        return targetObject.getFilledInstanceOf(clazz);
    }

    @Override
    public SayaMapper from(Object source) {
        targetObject.setSourceObject(new SourceObject(source));

        return this;
    }

    @Override
    public SayaMapper ignoring(Ignorable fieldList) {
        this.toIgnore = fieldList;

        return this;
    }

    @Override
    public SayaMapper relate(Customizable customizable) {
        this.customRelations = customizable;

        return this;
    }

}
