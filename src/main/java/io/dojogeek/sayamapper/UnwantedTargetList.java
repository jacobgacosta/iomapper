package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * UnwantedTargetList is a extension of an ArrayList for the ignorable fields.
 *
 * @author norvek
 */
public class UnwantedTargetList extends ArrayList<String> {

    private static final int SINGLE_FIELD = 1;
    private static final int DOT_POSITION = 1;
    private static final String SEPARATOR = "\\.";
    private static final Logger LOGGER = Logger.getLogger(UnwantedTargetList.class.getName());

    /**
     * Add the name of the field to fill.
     *
     * @param fieldName  the field name.
     * @return           a <bold>UnwantedTargetList</bold> instance
     */
    public UnwantedTargetList ignore(String fieldName) {
        super.add(fieldName);

        return this;
    }

    /**
     * Checks if the field name is present into ignorable list.
     *
     * @param fieldName  the field name.
     * @return           a <bold>UnwantedTargetList</bold> instance
     */
    public boolean hasPresentTo(String fieldName) {
        for (String ignorable : this) {
            if (this.popRootField(ignorable).equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the field name has nested fields.
     *
     * @param fieldName  the field name.
     * @return           <code>true</code> if the field has nested fields
     *                   <code>false</code> if the field has not nested fields
     */
    public boolean hasIgnorableNestedFor(String fieldName) {
        for (String ignorable : this) {
            if (this.popRootField(ignorable).equals(fieldName)
                    && this.getSplitFieldsFrom(ignorable).length > SINGLE_FIELD) {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes the field name from the ignorable list, if the field has
     * nested fields, only the root field is removed if it's matching with
     * the field name to find.
     *
     * @param fieldName  the field name.
     */
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

    /**
     * Remove the root field name.
     *
     * @param path  the path of fields.
     */
    private String removeRootField(String path) {
        return path.substring(path.indexOf(".") + DOT_POSITION);
    }

    /**
     * Gets the root field name.
     *
     * @param path  the path of fields.
     */
    private String popRootField(String path) {
        return this.getSplitFieldsFrom(path)[0];
    }

    /**
     * Gets an array of fields.
     *
     * @param path  the path of fields.
     */
    private String[] getSplitFieldsFrom(String path) {
        return path.split(SEPARATOR);
    }

}
