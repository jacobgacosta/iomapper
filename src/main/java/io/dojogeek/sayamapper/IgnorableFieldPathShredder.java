package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by norveo on 8/2/18.
 */
public class FieldPathShredder {

    private static final int SINGLE_FIELD = 1;

    private List<String> fields = new ArrayList<>();

    public FieldPathShredder(String path) {
        Arrays
                .asList(path.split(Delimiters.DOT_SEPARATOR))
                .forEach(field -> fields.add(field));
    }

    public RootField getRootField() {
        return this.fields.isEmpty() ? new RootField("") : new RootField(this.fields.get(0));
    }

    public boolean hasMoreFields() {
        return this.fields.size() > SINGLE_FIELD;
    }

    public void removeRootField() {
        this.fields.remove(0);
    }

}
