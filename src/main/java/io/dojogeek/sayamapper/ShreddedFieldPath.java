package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by norveo on 8/2/18.
 */
public class FieldPath {

    private static final String SEPARATOR = "\\.";
    private static int SINGLE_FIELD = 1;

    private List<String> fields = new ArrayList<>();

    public FieldPath(String path) {
        Arrays
                .asList(path.split(this.SEPARATOR))
                .forEach(field -> fields.add(field));
    }

    public String getRootField() {
        return this.fields.isEmpty() ? "" : this.fields.get(0);
    }

    public boolean hasMoreFields() {
        return this.fields.size() > SINGLE_FIELD;
    }

    public void removeRootField() {
        this.fields.remove(0);
    }

}
