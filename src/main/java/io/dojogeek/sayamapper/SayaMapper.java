package io.dojogeek.sayamapper;

public class SayaMapper implements Mapper {

    private TargetObject targetObject = new TargetObject();

    @Override
    public <T> T to(Class<T> targetClass) {
        return targetObject.getFilledInstanceOf(targetClass);
    }

    @Override
    public SayaMapper from(Object source) {
        targetObject.fillWith(new SourceObject(source));

        return this;
    }

    @Override
    public SayaMapper ignoring(Ignorable fieldList) {
        targetObject.ignoreFieldsForMapping(fieldList.ignore(new IgnorableList()));

        return this;
    }

    @Override
    public SayaMapper relate(Customizable customizable) {
        targetObject.applyCustomRelations(customizable);

        return this;
    }

}
