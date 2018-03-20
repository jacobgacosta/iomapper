package io.dojogeek.sayamapper;

/**
 * Created by norveo on 3/19/18.
 */
public class CustomSource {

    private String rootField;
    private String remainingSourceMapping;

    public void setRootField(String rootField) {
        this.rootField = rootField;
    }

    public String getRootField() {
        return rootField;
    }

    public void setRemainingFields(String remainingSourceMapping) {
        this.remainingSourceMapping = remainingSourceMapping;
    }

    public String getRemainingSourceMapping() {
        return remainingSourceMapping;
    }
}
