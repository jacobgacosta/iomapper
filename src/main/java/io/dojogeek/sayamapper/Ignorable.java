package io.dojogeek.sayamapper;

import java.util.List;

public interface Ignorable {

    void fillWithFieldsToIgnore(List<String> toIgnore);

}
