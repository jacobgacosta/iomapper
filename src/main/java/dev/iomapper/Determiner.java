package dev.iomapper;

import dev.iomapper.parser.SentenceValidator;

import java.util.regex.Pattern;

/**
 * <b>Determiner</b> offer a serie of functions to validate the
 * field path type.
 * <p>
 * Created by Jacob G. Acosta.
 */
public class Determiner {

    /**
     * Checks if a field path is a single, with no nested.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isSingle(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");

        return pattern.matcher(value).find();
    }

    /**
     * Checks if a field path is a nested.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isNested(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$");

        return pattern.matcher(value).find();
    }

    /**
     * Checks if a field path is a first level function.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isFunction(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*\\([a-zA-Z0-9]+(,\\s[a-zA-Z0-9]+)*(,\\s\\[\\'.+\\'\\])?\\)");

        return pattern.matcher(value).find();
    }

    /**
     * Checks if a value is an extra argument.
     * <p>
     * The extra arguments are the way: ['value']
     *
     * @param arg the arg
     * @return the boolean
     */
    public static boolean isExtraArgument(String arg) {
        Pattern pattern = Pattern.compile("^\\[\\'.+\\'\\]$");

        return pattern.matcher(arg).find();
    }

    /**
     * Checks if a field path is a group of multiple fields.
     * <p>
     * The multiple fiels are separated by comma.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isMultiple(String value) {
        Pattern pattern = Pattern.compile("^\\w+(,\\s\\w+)+$");

        return pattern.matcher(value).find();
    }

    /**
     * Checks if a field path contains nested functions.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isNestedMethod(String value) {
        return SentenceValidator.getInstance().validate(value);
    }

}
