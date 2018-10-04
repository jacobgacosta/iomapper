package io.dojogeek.sayamapper;

import io.dojogeek.sayamapper.utils.Executor;
import io.dojogeek.sayamapper.utils.MethodShredder;
import io.dojogeek.sayamapper.utils.SignatureMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dojogeek.sayamapper.RootTypeEnum.*;

/**
 * CustomMappings is a extension of an HashMap for the custom mapping fields.
 *
 * @author norvek
 */
public class CustomMappings {

    private Map<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMappings = new HashMap<>();

    /**
     * Add a relation source-target.
     *
     * @param sourceFieldPath the source field.
     * @param targetFieldPath ths target field.
     * @return a CustomMapper
     */
    public CustomMappings relate(String sourceFieldPath, String targetFieldPath) {
        customMappings.put(new CustomizableFieldPathShredder(sourceFieldPath), new CustomizableFieldPathShredder(targetFieldPath));

        return this;
    }

    public boolean hasCustomizable() {
        return !customMappings.isEmpty();
    }

    public boolean hasSourceMappingFor(String targetFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().trim().equals(targetFieldName)) {
                return !customMapping.getValue().getRootField().isEmpty();
            }
        }

        return false;
    }

    public boolean hasSourceRootFieldFor(String targetFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().equals(targetFieldName)) {
                return !customMapping.getKey().getRootField().isEmpty();
            }
        }

        return false;
    }

    public boolean hasSourceFor(String targetFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().equals(targetFieldName) && !customMapping.getKey().getRootField().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public void applyMapping(SourceObject sourceObject, FlexibleField targetField) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().trim().equals(targetField.getName())) {
                if (customMapping.getKey().getRootType().equals(SINGLE)) {
                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(customMapping.getKey().getRootField());
                    targetField.setValue(sourceField);
                } else if (customMapping.getKey().getRootType().equals(NESTED)) {
                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(customMapping.getKey().getRootField());

                    if (customMapping.getKey().getRootType().equals(NESTED) && customMapping.getValue().getRootType().equals(SINGLE)) {
                        customMapping.getKey().removeRootField();

                        targetField.setCustomMappings(this);
                        targetField.setValue(sourceField);

                        return;
                    }

                    customMapping.getKey().removeRootField();
                    customMapping.getValue().removeRootField();

                    targetField.setCustomMappings(this);
                    targetField.setValue(sourceField);
                } else if (customMapping.getKey().getRootType().equals(METHOD)) {
                    String rootField = customMapping.getKey().getRootField();

                    if (Determiner.isFunction(rootField)) {
                        List<Object> sourceFields = new ArrayList<>();

                        SignatureMethod signatureMethod = MethodShredder.dismantle(rootField);

                        signatureMethod.getArgs().forEach(arg -> {
                            if (Determiner.isExtraArgument(arg)) {
                                sourceFields.add(arg);

                                return;
                            }

                            FlexibleField sourceField = sourceObject.getMatchingFieldFor(arg);
                            sourceFields.add(sourceField.getValue());
                        });

                        Object result = Executor.executeFunction(signatureMethod.getName(), sourceFields);

                        targetField.setValue(result);

                        return;
                    }

                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(customMapping.getKey().getRootField());

                    customMapping.getKey().removeRootField();

                    targetField.setCustomMappings(this);
                    targetField.setValue(sourceField);
                }
            }

            if (customMapping.getValue().hasOtherFields()) {
                customMapping.getValue().removeRootField();
            }
        }
    }

    public boolean hasAnApplicableFunctonFor(String name) {
        String[] split = {};

        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (Determiner.isFunction(customMapping.getKey().getRootField())) {
                split = customMapping.getKey().getRootField().split("\\(|,|\\)");
            }
        }

        for (String value : split) {
            if (value.trim().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasSourceFieldWithName(String name) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getKey().getRootField().equals(name)) {
                return true;
            }
        }

        return false;
    }

}
