package io.dojogeek.sayamapper;

import java.util.regex.Pattern;

/**
 * Created by norveo on 8/7/18.
 */
public class Determiner {

    public static boolean isNested(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$");

        return pattern.matcher(value).find();
    }

    public static boolean isSingle(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");

        return pattern.matcher(value).find();
    }

    public static boolean isFunction(String value) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)\\([a-zA-Z0-9]+((,\\s[a-zA-Z0-9]+)*(,\\s\\[\\'\\w+\\'\\])?)\\)");

        return pattern.matcher(value).find();
    }

    public static boolean isExtraArgument(String arg) {
        Pattern pattern = Pattern.compile("^\\[\\'\\w+\\'\\]$");

        return pattern.matcher(arg).find();
    }
}
