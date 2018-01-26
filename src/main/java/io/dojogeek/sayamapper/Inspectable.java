package io.dojogeek.sayamapper;

public interface Inspectable {

    java.util.Map<String, Object> getPropertiesAndValuesMap();

    Object getValueFor(String field);

}
