package dev.iomapper;

import dev.iomapper.parser.SentenceValidator;
import dev.iomapper.utils.Delimiters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static dev.iomapper.RootTypeEnum.*;

/**
 * Created by norveo on 8/7/18.
 */
public class CustomizableFieldPathShredder {

    private String root;
    private List<String> nestedFields = new ArrayList<>();

    public CustomizableFieldPathShredder(String value) {
        SentenceValidator sentenceValidator = SentenceValidator.getInstance();

        sentenceValidator.validate(value);

        List<String> fields = new LinkedList<>(Arrays.asList(value.split(Delimiters.DOT_SEPARATOR)));

        if (fields.size() > 1) {
            for (int index = 1; index < fields.size(); index++) {
                this.nestedFields.add(fields.get(index));
            }
        }

        this.root = fields.get(0);
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRootField() {
        return this.root;
    }

    public boolean hasNestedFields() {
        return !this.nestedFields.isEmpty();
    }

    public boolean hasNoNestedFields() {
        return this.nestedFields.isEmpty();
    }

    public void updateRootWithNextField() {
        if (!this.nestedFields.isEmpty()) {
            this.root = this.nestedFields.get(0);

            this.nestedFields.remove(0);
        }
    }
}
