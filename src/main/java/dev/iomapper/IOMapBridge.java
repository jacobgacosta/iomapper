package dev.iomapper;

/**
 * IOMapBridge is a bridge that allows switch between source/target mapping object
 * and vice versa, avoiding create two instances variables for use it.
 *
 * @author norvek
 */
public class IOMapBridge<T, T2> implements BridgeMap<T, T2> {

    /**
     * Return a new instance of IOMap with a T like source object
     * and T2 like target object.
     *
     * @return a IOMap instance.
     */
    @Override
    public Mapper<T, T2> inner() {
        return new IOMap<>();
    }

    /**
     * Return a new instance of IOMap with a T2 like source object
     * and T like target object.
     *
     * @return a IOMap instance.
     */
    @Override
    public Mapper<T2, T> outer() {
        return new IOMap<>();
    }

}
