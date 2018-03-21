package io.dojogeek.sayamapper;

/**
 * Created by norveo on 3/19/18.
 */
public class CustomSource {

    private static final String SEPARATOR = ".";

    private String rootField;
    private String remainingSourceMapping;

    public String getRootField() {
        return rootField;
    }

    public void setRootField(String rootField) {
        this.rootField = rootField;
    }

    public void setRemainingFields(String remainingSourceMapping) {
        this.remainingSourceMapping = remainingSourceMapping;
    }

    public String getRemainingSourceMapping() {
        return this.remainingSourceMapping;
    }

    public String getFullMappingPath() {
        return this.rootField + SEPARATOR+ this.remainingSourceMapping;
    }

}
