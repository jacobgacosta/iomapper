package io.dojogeek.sayamapper;

import java.util.logging.Logger;

public abstract class MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(MergeableObject.class.getName());

    protected void merge(SourceObject sourceObject, Object target, IgnorableList ignorableList, CustomMapper customMapper) {
        new InspectableObject(target)
                .getDeclaredFields()
                .forEach(flexibleField -> {
                    FlexibleField matchedField = sourceObject.findMatchingWith(flexibleField);
                    flexibleField.setValue(matchedField);
                });
    }

}
