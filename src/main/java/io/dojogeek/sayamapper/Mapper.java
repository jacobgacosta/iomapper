package io.dojogeek.sayamapper;

public interface Mapper<T, T2> {

    Mapper<T, T2> from(T source);

    Mapper<T, T2> to(Class<T2> target);

    Mapper<T, T2> ignoring(Ignorable fieldList);

    T2 build();

}
