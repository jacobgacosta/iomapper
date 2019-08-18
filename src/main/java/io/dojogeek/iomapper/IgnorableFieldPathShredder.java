package io.dojogeek.iomapper;

import io.dojogeek.iomapper.utils.Delimiters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by norveo on 8/2/18.
 */
public class IgnorableFieldPathShredder {

    private static final int SINGLE_FIELD = 1;

    private List<String> fields = new ArrayList<>();

    public IgnorableFieldPathShredder(String path) {
        Arrays
                .asList(path.split(Delimiters.DOT_SEPARATOR))
                .forEach(field -> fields.add(field));
    }

    public String getRootField() {
        return fields.get(0);
    }

    public boolean hasNestedFields() {
        return fields.size() > SINGLE_FIELD;
    }

    public void removeRootField() {
        fields.remove(0);
    }

}
