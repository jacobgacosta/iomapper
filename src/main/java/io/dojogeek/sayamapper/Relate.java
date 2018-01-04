package io.dojogeek.sayamapper;

import java.util.HashMap;

public class Relate extends HashMap<String, String> {

    public Object relate(String sourceField, String targetField) {
        return super.put(sourceField, targetField);
    }

}
