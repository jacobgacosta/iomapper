package io.dojogeek.sayamapper;

public interface Mapper {

    <T> T to(Class<T> clazz);

    SayaMapper from(Object source);

    SayaMapper ignoring(Ignorable fieldList);

    SayaMapper relate(Customizable customizable);

}
