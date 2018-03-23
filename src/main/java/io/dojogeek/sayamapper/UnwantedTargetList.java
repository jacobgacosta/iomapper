package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UnwantedTargetList extends ArrayList<String> {

    private static final int SINGLE_FIELD = 1;
    private static final int DOT_POSITION = 1;
    private static final String SEPARATOR = "\\.";
    private static final Logger LOGGER = Logger.getLogger(UnwantedTargetList.class.getName());

    public UnwantedTargetList ignore(String fieldName) {
        super.add(fieldName);

        return this;
    }

    public boolean hasPresentTo(String fieldName) {
        for (String ignorable : this) {
            if (this.popRootField(ignorable).equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasIgnorableNestedFor(String fieldName) {
        for (String ignorable : this) {
            if (this.popRootField(ignorable).equals(fieldName)
                    && this.getSplitFieldsFrom(ignorable).length > SINGLE_FIELD) {
                return true;
            }
        }

        return false;
    }

    public void removeTo(String fieldName) {
        for (int index = 0; index < this.size(); index++) {
            String currentItem = this.get(index);

            if (this.popRootField(currentItem).equals(fieldName)
                    && this.getSplitFieldsFrom(currentItem).length == SINGLE_FIELD) {
                this.remove(fieldName);
            } else if (this.popRootField(currentItem).equals(fieldName)
                    && this.getSplitFieldsFrom(currentItem).length > SINGLE_FIELD) {
                this.set(index, this.removeRootField(currentItem));
            }
        }
    }

    private String removeRootField(String path) {
        return path.substring(path.indexOf(".") + DOT_POSITION);
    }

    private String popRootField(String path) {
        return this.getSplitFieldsFrom(path)[0];
    }

    private String[] getSplitFieldsFrom(String path) {
        return path.split(SEPARATOR);
    }

}
