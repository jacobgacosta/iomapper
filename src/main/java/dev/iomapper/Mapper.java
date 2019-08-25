package dev.iomapper;

/**
 * Mapper is the contract to fill a target object.
 *
 * @author norvek
 */
public interface Mapper<T, T2> {

    /**
     * Sets the source object to fill.
     *
     * @param source the source instance.
     * @return a <bold>IOMap</bold> instance.
     */
    Mapper<T, T2> from(T source);

    /**
     * Sets the target class to fill.
     *
     * @param target the target class.
     * @return a <bold>IOMap</bold> instance.
     */
    Mapper<T, T2> to(Class<T2> target);

    /**
     * Sets an ignorable object with the fields to fill in mapping.
     *
     * @param ignorable the target class.
     * @return a <bold>IOMap</bold> instance.
     */
    Mapper<T, T2> ignoring(Ignorable ignorable);

    /**
     * Sets a customizable object with the fields for explicit mapping.
     *
     * @param customizable the target class.
     * @return a <bold>IOMap</bold> instance.
     */
    Mapper<T, T2> relate(Customizable customizable);

    /**
     * Returns a filled target object.
     *
     * @return a target object instance.
     */
    T2 build();

}
