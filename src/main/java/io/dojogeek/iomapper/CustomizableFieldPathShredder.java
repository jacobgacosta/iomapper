package io.dojogeek.iomapper;

import io.dojogeek.iomapper.utils.Delimiters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.dojogeek.iomapper.RootTypeEnum.*;

/**
 * Created by norveo on 8/7/18.
 */
public class CustomizableFieldPathShredder {

    private String root;
    private RootTypeEnum rootTypeEnum;
    private List<String> nestedFields;

    public CustomizableFieldPathShredder(String value) {
        if (Determiner.isSingle(value)) {
            this.root = value;
            this.rootTypeEnum = SINGLE;
        } else if (Determiner.isNested(value)) {
            this.nestedFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.DOT_SEPARATOR)));
            this.root = nestedFields.get(0);
            this.rootTypeEnum = NESTED_FIELD;
        } else if (Determiner.isFunction(value)) {
            this.nestedFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.DOT_SEPARATOR)));
            this.root = nestedFields.get(0);
            this.rootTypeEnum = SINGLE_METHOD;
        } else if (Determiner.isMultiple(value)) {
            this.nestedFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.COMMA_SEPARATOR)));
            this.root = nestedFields.get(0);
            this.rootTypeEnum = MULTIPLE;
        } else if (Determiner.isNestedMethod(value)) {
            this.root = value;
            this.rootTypeEnum = NESTED_METHOD;
        }
    }

    public String getRootField() {
        if (this.nestedFields != null && !this.nestedFields.isEmpty()) {
            return this.nestedFields.get(0);
        } else {
            return this.root;
        }
    }

    public RootTypeEnum getType() {
        return this.rootTypeEnum;
    }

    public void removeRootField() {
        this.nestedFields.remove(0);
    }

    public boolean hasOtherFields() {
        return this.nestedFields != null && !this.nestedFields.isEmpty();
    }

}
