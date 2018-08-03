package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author norvek
 */
public class FilterField {

    public static List<FlexibleField> excludeFields(List<FlexibleField> flexibleFields, UnwantedTargetList unwantedTargetList) {
        List<FlexibleField> copyFields = new ArrayList<>();
        copyFields.addAll(flexibleFields);

        if (unwantedTargetList == null || unwantedTargetList.isEmpty()) {
            return flexibleFields;
        }

        for (FlexibleField flexibleField : flexibleFields) {
            if (unwantedTargetList.hasPresentTo(flexibleField.getName())) {
                unwantedTargetList.removeTo(flexibleField.getName());
                copyFields.remove(flexibleField);
            }
        }

        return copyFields;
    }

}
