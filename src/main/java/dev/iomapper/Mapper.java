package dev.iomapper;

/**
 * Mapper is the contract to build a target object.
 *
 * @author Jacob G. Acosta
 */
public interface Mapper<T, T2> {

    /**
     * Sets the source object.
     *
     * @param source the source instance.
     * @return a <b>IOMap</b> instance.
     */
    Mapper<T, T2> from(T source);

    /**
     * Sets the target class.
     *
     * @param target the target class.
     * @return a <b>IOMap</b> instance.
     */
    Mapper<T, T2> to(Class<T2> target);

    /**
     * Sets an ignorable object with the ignorable fields for mapping operation.
     *
     * @param ignorable the ignorable instance.
     * @return a <b>IOMap</b> instance.
     */
    Mapper<T, T2> ignoring(Ignorable ignorable);

    /**
     * Sets a customizable object with the fields for the explicit mapping.
     *
     * @param customizable the customizable instance.
     * @return a <b>IOMap</b> instance.
     */
    Mapper<T, T2> relate(Customizable customizable);

    /**
     * Starts with the building target object
     *
     * @return a target object instance.
     */
    T2 build();

}
