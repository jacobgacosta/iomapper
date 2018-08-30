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

    private FlexibleField customValueTo;
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

    public boolean hasTargetRootFieldWithName(String targetFieldName) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().equals(targetFieldName)) {
                return true;
            }
        }

        return false;
    }

    public void applyMapping(String targetFieldName, SourceObject sourceObject, FlexibleField targetField) {
        for (Map.Entry<CustomizableFieldPathShredder, CustomizableFieldPathShredder> customMapping : customMappings.entrySet()) {
            if (customMapping.getValue().getRootField().equals(targetFieldName)) {
                if (customMapping.getKey().getRootType().equals(SINGLE)) {
                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(customMapping.getKey().getRootField());
                    targetField.setValue(sourceField);
                } else if (customMapping.getKey().getRootType().equals(NESTED)) {
                    FlexibleField sourceField = sourceObject.getMatchingFieldFor(customMapping.getKey().getRootField());
                    customMapping.getKey().removeRootField();
                    customMapping.getValue().removeRootField();
                    targetField.setCustomMappings(this);
                    targetField.setValue(sourceField);
                } else if (customMapping.getKey().getRootType().equals(METHOD)) {
                    String rootField = customMapping.getKey().getRootField();

                    SignatureMethod signatureMethod = MethodShredder.dismantle(rootField);

                    List<Object> sourceFields = new ArrayList<>();

                    signatureMethod.getArgs().forEach(arg -> {
                        FlexibleField sourceField = sourceObject.getMatchingFieldFor(arg);
                        sourceFields.add(sourceField.getValue());
                    });

                    Object result = Executor.executeFunction(signatureMethod.getName(), sourceFields);

                    targetField.setValue(result);
                }
            }
        }
    }

}
