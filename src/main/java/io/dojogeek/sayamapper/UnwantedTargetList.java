package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UnwantedTargetList extends ArrayList<String> {

    private final static Logger LOGGER = Logger.getLogger(UnwantedTargetList.class.getName());
    private static final int SINGLE_FIELD = 1;

    public UnwantedTargetList ignore(String fieldName) {
        super.add(fieldName);

        return this;
    }

    public boolean hasFieldNamed(String fieldName) {
        for (String item : this) {
            if (item.split("\\.").length == SINGLE_FIELD && (item.equals(fieldName) || item.contains(fieldName))) {
                this.remove(item);

                return true;
            }
        }

        return false;
    }

    public UnwantedTargetList getIgnorableFor(String fieldName) {
        UnwantedTargetList ignorableFields = new UnwantedTargetList();

        for (String item : this) {
            if (item.split("\\.").length > SINGLE_FIELD) {
                String name = item.substring(0, item.lastIndexOf("."));

                if (name.equals(fieldName)) {
                    ignorableFields.add(item.substring(item.lastIndexOf(".") + 1));
                }
            }
        }

        return ignorableFields;
    }

}
