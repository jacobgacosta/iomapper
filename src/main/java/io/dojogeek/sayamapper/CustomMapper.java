package io.dojogeek.sayamapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CustomMapper extends HashMap<String, String> {

    private static final int SINGLE_FIELD = 1;
    private static final int DOT_POSITION = 1;
    private static final String SEPARATOR = ".";
    private final static Logger LOGGER = Logger.getLogger(CustomMapper.class.getName());

    public CustomMapper relate(String sourceField, String targetField) {
        super.put(sourceField, targetField);

        return this;
    }

    public CustomTarget getTargetFor(FlexibleField flexibleField) {
        CustomTarget customTarget = new CustomTarget();

        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (entry.getValue().split("\\.").length > SINGLE_FIELD) {
                String rootField = entry.getValue().substring(0, entry.getValue().indexOf(SEPARATOR));

                if (rootField.equals(flexibleField.getName())) {
                    customTarget.setRootField(rootField);
                    customTarget.setRemainingFields(
                            entry.getValue()
                                    .substring(entry.getValue().indexOf(SEPARATOR) + DOT_POSITION)
                    );
                }
            }

            if (entry.getValue().equals(flexibleField.getName())) {
                customTarget.setRootField(entry.getValue());
            }
        }

        return customTarget;
    }

    public CustomSource getSourceFor(CustomTarget customTarget) {
        String targetField = customTarget.getRootField() +
                (customTarget.getRemainingTargetMapping() != null ? SEPARATOR + customTarget.getRemainingTargetMapping() : "");

        CustomSource customSource = new CustomSource();

        for (Map.Entry<String, String> entry : this.entrySet()) {
            if (entry.getValue().equals(targetField)) {
                if (entry.getKey().split("\\.").length > SINGLE_FIELD) {
                    customSource.setRootField(entry.getKey().substring(0, entry.getKey().indexOf(SEPARATOR)));
                    customSource.setRemainingFields(
                            entry.getKey()
                                    .substring(entry.getKey().indexOf(SEPARATOR) + DOT_POSITION)
                    );

                    break;
                }

                customSource.setRootField(entry.getKey());
            }
        }

        return customSource;
    }

}
