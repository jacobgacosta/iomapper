package io.dojogeek.iomapper;

/**
 * BridgeMap is the contract to fill the source/target and vice versa.
 *
 * @author norvek
 */
public interface BridgeMap<T, T2> {

    /**
     * Return a new instance of IOMap with a T like source object
     * and T2 like target object.
     *
     * @return a IOMap instance.
     */
    Mapper<T, T2> inner();

    /**
     * Return a new instance of IOMap with a T2 like source object
     * and T like target object.
     *
     * @return a IOMap instance.
     */
    Mapper<T2, T> outer();

}
