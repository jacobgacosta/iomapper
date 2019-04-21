package io.dojogeek.sayamapper;

import io.dojogeek.sayamapper.utils.Delimiters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.dojogeek.sayamapper.RootTypeEnum.*;

/**
 * Created by norveo on 8/7/18.
 */
public class CustomizableFieldPathShredder {

    private String value;
    private RootTypeEnum rootTypeEnum;
    private List<String> otherFields;

    public CustomizableFieldPathShredder(String value) {
        if (Determiner.isSingle(value)) {
            this.value = value;
            this.rootTypeEnum = SINGLE;
        } else if (Determiner.isNested(value)) {
            otherFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.DOT_SEPARATOR)));
            this.value = otherFields.get(0);
            this.rootTypeEnum = NESTED;
        } else if (Determiner.isFunction(value)) {
            otherFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.DOT_SEPARATOR)));
            this.value = otherFields.get(0);
            this.rootTypeEnum = METHOD;
        } else if (Determiner.isMultiple(value)) {
            otherFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.COMMA_SEPARATOR)));
            this.value = otherFields.get(0);
            this.rootTypeEnum = MULTIPLE;
        }
    }

    public String getRootField() {
        return (otherFields != null && !otherFields.isEmpty()) ? otherFields.get(0) : value;
    }

    public RootTypeEnum getType() {
        return rootTypeEnum;
    }

    public void removeRootField() {
        this.otherFields.remove(0);
    }

    public boolean hasOtherFields() {
        return this.otherFields != null && !this.otherFields.isEmpty();
    }

}
