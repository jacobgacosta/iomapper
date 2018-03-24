package io.dojogeek.sayamapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CustomMapper extends HashMap<String, String> {

    private static final int SINGLE_FIELD = 1;
    private static final int DOT_POSITION = 1;
    private static final String SEPARATOR = "\\.";
    private final static Logger LOGGER = Logger.getLogger(CustomMapper.class.getName());

    public CustomMapper relate(String sourceField, String targetField) {
        super.put(sourceField, targetField);

        return this;
    }


    public boolean hasATargetFor(String name) {
        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (this.popRootField(entry.getValue()).equals(name)) {
                return true;
            }
        }

        return false;
    }

    public String getSourceFor(String name) {
        String field = "";

        for (Map.Entry<String, String> entry : this.entrySet()) {
            String rootTarget = this.popRootField(entry.getValue());

            if (rootTarget.equals(name)) {
                return this.popRootField(entry.getKey());
            }
        }

        return field;
    }

    public void removeRootFields(String sourceField, String targetField) {
        Map<String, String> mapCopy = new HashMap<>();
        mapCopy.putAll(this);

        for (Map.Entry<String, String> entry : mapCopy.entrySet()) {
            if (this.popRootField(entry.getValue()).equals(targetField)) {
                this.remove(entry.getKey());

                this.put(this.removeRootField(entry.getKey()), this.removeRootField(entry.getValue()));
            }
        }
    }

    public boolean hasASourceFor(String name) {
        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (this.popRootField(entry.getKey()).equals(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasNestedSourceFieldsFor(String name) {
        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (this.popRootField(entry.getKey()).equals(name)) {
                if (entry.getKey().split(SEPARATOR).length > SINGLE_FIELD) {
                    return true;
                }
            }
        }

        return false;
    }

    private String popRootField(String path) {
        return this.getSplitFieldsFrom(path)[0];
    }

    private String[] getSplitFieldsFrom(String path) {
        return path.split(SEPARATOR);
    }

    private String removeRootField(String path) {
        return path.substring(path.indexOf(".") + DOT_POSITION);
    }

}
