package io.dojogeek.sayamapper;

/**
 * Created by norveo on 3/24/18.
 */
public class SayaMapBridge<T, T2> implements BridgeMap<T, T2> {

    private Mapper<T, T2> inner = new SayaMap<>();
    private Mapper<T2, T> outer = new SayaMap<>();

    @Override
    public Mapper<T, T2> inner() {
        return this.inner;
    }

    @Override
    public Mapper<T2, T> outer() {
        return this.outer;
    }

}
