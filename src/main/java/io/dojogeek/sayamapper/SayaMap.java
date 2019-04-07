package io.dojogeek.sayamapper;

/**
 * SayaMap prepare the elements to create a target object.
 *
 * @author norvek
 */
public class SayaMap<T, T2> implements Mapper<T, T2> {

    private T source;
    private Class<T2> target;
    private Ignorable ignorable;
    private Customizable customizable;

    /**
     * Sets the source object to fill.
     *
     * @param source  the source instance.
     * @return        a <bold>SayaMap</bold> instance.
     */
    @Override
    public SayaMap<T, T2> from(T source) {
        this.source = source;

        return this;
    }

    /**
     * Sets the target class to fill.
     *
     * @param target  the target class.
     * @return        a <bold>SayaMap</bold> instance.
     */
    @Override
    public SayaMap<T, T2> to(Class<T2> target) {
        this.target = target;

        return this;
    }

    /**
     * Sets an ignorable object with the fields to fill in mapping.
     *
     * @param ignorable  the target class.
     * @return           a <bold>SayaMap</bold> instance.
     */
    @Override
    public SayaMap<T, T2> ignoring(Ignorable ignorable) {
        this.ignorable = ignorable;

        return this;
    }

    /**
     * Sets a customizable object with the fields for explicit mapping.
     *
     * @param customizable  the target class.
     * @return              a <bold>SayaMap</bold> instance.
     */
    @Override
    public SayaMap<T, T2> relate(Customizable customizable) {
        this.customizable = customizable;

        return this;
    }

    /**
     * Returns a filled target object.
     *
     * @return a target object instance.
     */
    @Override
    public T2 build() {
        TargetWrapper<T2> targetWrapper = new TargetWrapper<>(this.target);

        if (this.ignorable != null) {
            targetWrapper.ignore(this.ignorable.fill(new IgnorableFields()));
        }

        if (this.customizable != null) {
            targetWrapper.relate(this.customizable.fill(new CustomMappings()));
        }

        return targetWrapper
                .populateWith(new SourceObject(this.source))
                .get();
    }
    
}
