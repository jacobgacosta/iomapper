package io.dojogeek.sayamapper;

import java.util.logging.Logger;

public abstract class MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(MergeableObject.class.getName());

    protected void merge(SourceObject sourceObject, Object target, UnwantedTargetList unwantedTargetList, CustomMapper customMapper) {
        new InspectableObject(target)
                .getDeclaredFields()
                .forEach(flexibleField -> {
                    if (unwantedTargetList != null && !unwantedTargetList.isEmpty()) {
                        if (unwantedTargetList.hasPresentTo(flexibleField.getName())) {
                            if (!unwantedTargetList.hasIgnorableNestedFor(flexibleField.getName())) {
                                unwantedTargetList.removeTo(flexibleField.getName());
                                return;
                            }

                            unwantedTargetList.removeTo(flexibleField.getName());
                            flexibleField.setIgnorableFields(unwantedTargetList);
                        }
                    }

                    if (customMapper != null && !customMapper.isEmpty()) {
                        if (!customMapper.hasATargetFor(flexibleField.getName())) {
                            return;
                        }

                        String sourceField = customMapper.getSourceFor(flexibleField.getName());
                        FlexibleField matchedField = sourceObject.findMatchingFor(sourceField);
                        customMapper.removeRootFields(sourceField, flexibleField.getName());
                        flexibleField.setCustomMappings(customMapper);
                        flexibleField.setValue(matchedField);

                        return;
                    }

                    FlexibleField matchedField = sourceObject.findMatchingFor(flexibleField.getName());
                    flexibleField.setValue(matchedField);
                });
    }

}
