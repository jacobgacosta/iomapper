package io.dojogeek.sayamapper;

/**
 * SayaMapBridge is a bridge that allows switch between source/target mapping object
 * and vice versa, avoiding create two instances variables for use it.
 *
 * @author norvek
 */
public class SayaMapBridge<T, T2> implements BridgeMap<T, T2> {

    /**
     * Return a new instance of SayaMap with a T like source object
     * and T2 like target object.
     *
     * @return a SayaMap instance.
     */
    @Override
    public Mapper<T, T2> inner() {
        return new SayaMap<>();
    }

    /**
     * Return a new instance of SayaMap with a T2 like source object
     * and T like target object.
     *
     * @return a SayaMap instance.
     */
    @Override
    public Mapper<T2, T> outer() {
        return new SayaMap<>();
    }

}
