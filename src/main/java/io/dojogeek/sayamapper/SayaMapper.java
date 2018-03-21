package io.dojogeek.sayamapper;

public class SayaMapper implements Mapper {

    private SourceObject source;

    @Override
    public <T> T to(Class<T> targetClass) {
        TargetObject<T> targetObject = new TargetObject(targetClass);

        return targetObject.getFilledInstanceFrom(this.source);
    }

    @Override
    public SayaMapper from(Object source) {
        this.source = new SourceObject(source);

        return this;
    }

    @Override
    public SayaMapper ignoring(Ignorable fieldList) {

        return this;
    }

    @Override
    public SayaMapper relate(Customizable customizable) {

        return this;
    }

}
