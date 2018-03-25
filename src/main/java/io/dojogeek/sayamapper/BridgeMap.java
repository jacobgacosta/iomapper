package io.dojogeek.sayamapper;

/**
 * Created by norveo on 3/24/18.
 */
public interface BridgeMap<T, T2>  {

    Mapper<T, T2> inner();

    Mapper<T2, T> outer();

}
