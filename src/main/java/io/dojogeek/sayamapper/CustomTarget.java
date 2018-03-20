package io.dojogeek.sayamapper;

/**
 * Created by norveo on 3/19/18.
 */
public class CustomTarget {

    private String rootField;
    private String remainingTargetMapping;

    public String getRemainingTargetMapping() {
        return remainingTargetMapping;
    }

    public void setRemainingFields(String remainingTargetMapping) {
        this.remainingTargetMapping = remainingTargetMapping;
    }

    public String getRootField() {
        return rootField;
    }

    public void setRootField(String currentField) {
        this.rootField = currentField;
    }

}
