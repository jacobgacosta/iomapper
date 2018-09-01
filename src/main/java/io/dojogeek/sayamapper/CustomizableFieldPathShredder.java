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
    private List<String> nestedFields;

    public CustomizableFieldPathShredder(String value) {
        if (Determiner.isSingle(value)) {
            this.value = value;
            this.rootTypeEnum = SINGLE;
        } else if (Determiner.isNested(value)) {
            nestedFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.DOT_SEPARATOR)));
            this.value = nestedFields.get(0);
            this.rootTypeEnum = NESTED;
        } else if (Determiner.isFunction(value)) {
            nestedFields = new LinkedList<>(Arrays.asList(value.split(Delimiters.DOT_SEPARATOR)));
            this.value = nestedFields.get(0);
            this.rootTypeEnum = METHOD;
        }
    }

    public String getRootField() {
        return (nestedFields != null && !nestedFields.isEmpty()) ? nestedFields.get(0) : value;
    }

    public RootTypeEnum getRootType() {
        return rootTypeEnum;
    }

    public void removeRootField() {
        this.nestedFields.remove(0);
    }

}
