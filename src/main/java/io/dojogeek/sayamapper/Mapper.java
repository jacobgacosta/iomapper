package io.dojogeek.sayamapper;

public interface Mapper<T, T2> {

    Mapper<T, T2> from(T source);

    Mapper<T, T2> to(Class<T2> target);

    Mapper<T, T2> ignoring(Ignorable ignorable);

    Mapper<T, T2> relate(Customizable customizable);

    T2 build();

}
