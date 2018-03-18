package io.dojogeek.sayamapper;

import java.util.HashMap;

public class CustomMapper extends HashMap<String, String> {

    public CustomMapper relate(String sourceField, String targetField) {
        super.put(sourceField, targetField);

        return this;
    }

}
