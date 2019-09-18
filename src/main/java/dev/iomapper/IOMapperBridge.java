package dev.iomapper;

/**
 * IOMapBridge is a bridge that allows switch between source/target objects
 *
 * @author Jacob G. Acosta
 */
public class IOMapperBridge<T, T2> implements BridgeMap<T, T2> {

    /**
     * Return a new instance of IOMapper with a T as a source object
     * and T2 as a target object.
     *
     * @return a IOMapper instance.
     */
    @Override
    public Mapper<T, T2> inner() {
        return new IOMapper<>();
    }

    /**
     * Return a new instance of IOMapper with a T2 as a source object
     * and T as a target object.
     *
     * @return a IOMap instance.
     */
    @Override
    public Mapper<T2, T> outer() {
        return new IOMapper<>();
    }

}
