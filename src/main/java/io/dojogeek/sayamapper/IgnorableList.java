package io.dojogeek.sayamapper;

import java.util.ArrayList;

public class IgnorableList extends ArrayList<String> {

    public IgnorableList ignore(String fieldName) {
        super.add(fieldName);

        return this;
    }

}
