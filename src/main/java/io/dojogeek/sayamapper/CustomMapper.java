package io.dojogeek.sayamapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * CustomMapper is a extension of an HashMap for the custom mapping fields.
 *
 * @author norvek
 */
public class CustomMapper extends HashMap<String, String> {

    private static final int SINGLE_FIELD = 1;
    private static final int DOT_POSITION = 1;
    private static final String SEPARATOR = "\\.";
    private static final int SEPARATOR_POSITION = 1;
    private final static Logger LOGGER = Logger.getLogger(CustomMapper.class.getName());

    /**
     * Add a relation source-target.
     *
     * @param sourceField  the source field.
     * @param targetField  ths target field.
     * @return             a CustomMapper
     */
    public CustomMapper relate(String sourceField, String targetField) {
        super.put(sourceField, targetField);

        return this;
    }

    /**
     * Check if a field name is present into a target mapping path.
     *
     * @param fieldName    the field name.
     * @return             <code>true</code> if the field exist into target path
     *                     <code>false</code> if the field no exist into target path
     */
    public boolean hasATargetFor(String fieldName) {
        for (Map.Entry<String, String> entry : this.entrySet()) {
            String [] values = entry.getValue().split(",");

            if (values.length > 0) {
                for (String value : values) {
                    String field = value.trim();
                    if (field.equals(fieldName)) {
                        return true;
                    }
                }
            }

            if (this.popRootField(entry.getValue()).equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the full source path to the given field name.
     *
     * @param fieldName    the field name.
     * @return             the source path
     */
    public String getSourceFor(String fieldName) {
        String field = "";

        for (Map.Entry<String, String> entry : this.entrySet()) {
            String [] values = entry.getValue().split(",");

            if (values.length > 0) {
                for (String value : values) {
                    field = value.trim();
                    if (field.equals(fieldName)) {
                        return this.popRootField(entry.getKey());
                    }
                }
            }

            if (this.popRootField(entry.getValue()).equals(fieldName)) {
                return this.popRootField(entry.getKey());
            }
        }

        return field;
    }

    /**
     * Removes the root fields of the source and target mapping paths.
     *
     * @param sourceField    the source field name.
     * @param targetField    the target field name.
     */
    public void removeRootFields(String sourceField, String targetField) {
        Map<String, String> mapCopy = new HashMap<>();
        mapCopy.putAll(this);

        for (Map.Entry<String, String> entry : mapCopy.entrySet()) {
            String [] values = entry.getValue().split(",");

            if (values.length > 0) {
                for (String value : values) {
                    String field = value.trim();
                    if (field.equals(targetField)) {
                        this.remove(entry.getKey());

                        this.put(this.removeRootField(entry.getKey()), this.removeFirstField(entry.getValue()));

                        return;
                    }
                }
            }

            if (this.popRootField(entry.getValue()).equals(targetField)) {
                this.remove(entry.getKey());

                this.put(this.removeRootField(entry.getKey()), this.removeRootField(entry.getValue()));
            }
        }
    }

    /**
     * Check if a field name is present into a source mapping path.
     *
     * @param fieldName    the field name.
     * @return             <code>true</code> if the field exist into source path
     *                     <code>false</code> if the field no exist into source path
     */
    public boolean hasASourceFor(String fieldName) {
        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (this.popRootField(entry.getKey()).equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the field name has nested fields.
     *
     * @param fieldName    the field name.
     * @return             <code>true</code> if the field has nested fields
     *                     <code>false</code> if the field has not nested fields
     */
    public boolean hasNestedSourceFieldsFor(String fieldName) {
        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (this.popRootField(entry.getKey()).equals(fieldName)) {
                if (entry.getKey().split(SEPARATOR).length > SINGLE_FIELD) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gets the root field name.
     *
     * @param path  the path of fields.
     */
    private String popRootField(String path) {
        return this.getSplitFieldsFrom(path)[0];
    }

    /**
     * Gets an array of fields.
     *
     * @param path  the path of fields.
     */
    private String[] getSplitFieldsFrom(String path) {
        return path.split(SEPARATOR);
    }

    /**
     * Remove the root field name.
     *
     * @param path  the path of fields.
     */
    private String removeRootField(String path) {
        return path.substring(path.indexOf(".") + DOT_POSITION);
    }

    private String removeFirstField(String fields) {
        return fields.substring(fields.indexOf(",") + SEPARATOR_POSITION);
    }

}
