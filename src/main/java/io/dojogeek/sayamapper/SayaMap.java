package io.dojogeek.sayamapper;

public class SayaMap<T, T2> implements Mapper<T, T2> {

    private T source;
    private Class<T2> target;
    private Ignorable ignorable;
    private Customizable customizable;

    @Override
    public SayaMap<T, T2> from(T source) {
        this.source = source;

        return this;
    }

    @Override
    public SayaMap<T, T2> to(Class<T2> target) {
        this.target = target;

        return this;
    }

    @Override
    public SayaMap<T, T2> ignoring(Ignorable ignorable) {
        this.ignorable = ignorable;

        return this;
    }

    @Override
    public Mapper<T, T2> relate(Customizable customizable) {
        this.customizable = customizable;

        return this;
    }


    @Override
    public T2 build() {
        return this.populateTarget();
    }

    private T2 populateTarget() {
        TargetObject<T2> targetObject = new TargetObject(this.target);

        if (this.ignorable != null) {
            targetObject.ignore(this.ignorable.ignore(new UnwantedTargetList()));
        }

        if (this.customizable != null) {
            targetObject.relate(this.customizable.fill(new CustomMapper()));
        }

        return targetObject.getFilledInstanceFrom(new SourceObject(this.source));
    }
}
