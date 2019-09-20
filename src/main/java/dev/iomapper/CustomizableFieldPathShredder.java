package dev.iomapper;

import dev.iomapper.parser.SentenceValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <b>CustomizableFieldPathShredder</b> is an auxiliar class for @see dev.iomapper.CustomMappings class
 * it split the field path identifiying the <b>root</b> and nested fields.
 * <p>
 * Created by Jacob G. Acosta.
 */
public class CustomizableFieldPathShredder {

    private String rootField;
    private List<String> nestedFields = new ArrayList<>();

    /**
     * Applies the validations and assign the root field, as well as its nested fields (if it contains),.
     * on the <b>fieldPath</b> parameter.
     *
     * @param fieldPath the field path
     */
    public CustomizableFieldPathShredder(String fieldPath) {
        SentenceValidator sentenceValidator = SentenceValidator.getInstance();

        sentenceValidator.validate(fieldPath);

        List<String> fields = new LinkedList<>(Arrays.asList(fieldPath.split(Delimiters.DOT_SEPARATOR)));

        if (fields.size() > 1) {
            for (int index = 1; index < fields.size(); index++) {
                this.nestedFields.add(fields.get(index));
            }
        }

        this.rootField = fields.get(0);
    }

    /**
     * Sets the root field.
     *
     * @param rootField the root field
     */
    public void setRoot(String rootField) {
        this.rootField = rootField;
    }

    /**
     * Gets the root field.
     *
     * @return the root field
     */
    public String getRootField() {
        return this.rootField;
    }

    /**
     * Returns if the <b>fieldPath</b> has nested fields.
     *
     * @return the boolean
     */
    public boolean hasNestedFields() {
        return !this.nestedFields.isEmpty();
    }

    /**
     * Updates the <b>root field</b> by moving it with the next nested.
     */
    public void updateRootFieldWithNextField() {
        if (!this.nestedFields.isEmpty()) {
            this.rootField = this.nestedFields.get(0);

            this.nestedFields.remove(0);
        }
    }

}
