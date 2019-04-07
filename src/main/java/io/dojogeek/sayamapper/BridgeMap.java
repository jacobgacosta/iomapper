package io.dojogeek.sayamapper;

/**
 * BridgeMap is the contract to fill the source/target and vice versa.
 *
 * @author norvek
 */
public interface BridgeMap<T, T2> {

    /**
     * Return a new instance of SayaMap with a T like source object
     * and T2 like target object.
     *
     * @return a SayaMap instance.
     */
    Mapper<T, T2> inner();

    /**
     * Return a new instance of SayaMap with a T2 like source object
     * and T like target object.
     *
     * @return a SayaMap instance.
     */
    Mapper<T2, T> outer();

}
