package io.dojogeek.sayamapper;

import java.util.HashMap;
import java.util.Map;

public class CustomMapper extends HashMap<String, String> {

    public CustomMapper relate(String sourceField, String targetField) {
        super.put(sourceField, targetField);

        return this;
    }

    public String getSourceFor(String fieldName) {
        String source = "";

        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (fieldName.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return source;
    }

}
