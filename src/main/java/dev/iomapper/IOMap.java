package dev.iomapper;

/**
 * IOMap prepare the elements to create a target object.
 *
 * @author norvek
 */
public class IOMap<T, T2> implements Mapper<T, T2> {

    private T source;
    private Class<T2> target;
    private IgnorableFields ignorableFields;
    private CustomMappings customMappings;

    /**
     * Sets the source object to fill.
     *
     * @param source the source instance.
     * @return a <bold>IOMap</bold> instance.
     */
    @Override
    public IOMap<T, T2> from(T source) {
        this.source = source;

        return this;
    }

    /**
     * Sets the target class to fill.
     *
     * @param target the target class.
     * @return a <bold>IOMap</bold> instance.
     */
    @Override
    public IOMap<T, T2> to(Class<T2> target) {
        this.target = target;

        return this;
    }

    /**
     * Sets an ignorable object with the fields to fill in mapping.
     *
     * @param ignorable the target class.
     * @return a <bold>IOMap</bold> instance.
     */
    @Override
    public IOMap<T, T2> ignoring(Ignorable ignorable) {
        if (ignorable != null) {
            this.ignorableFields = ignorable.fill(new IgnorableFields());
        }

        return this;
    }

    /**
     * Sets a customizable object with the fields for explicit mapping.
     *
     * @param customizable the target class.
     * @return a <bold>IOMap</bold> instance.
     */
    @Override
    public IOMap<T, T2> relate(Customizable customizable) {
        if (customizable != null) {
            this.customMappings = customizable.fill(new CustomMappings());
        }

        return this;
    }

    /**
     * Returns a filled target object.
     *
     * @return a target object instance.
     */
    @Override
    public T2 build() {
        return new TargetWrapper<>(this.target)
                .ignore(this.ignorableFields)
                .relate(this.customMappings)
                .populateWith(new SourceWrapper(this.source))
                .get();
    }

}
