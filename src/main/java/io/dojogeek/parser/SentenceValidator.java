package io.dojogeek.parser;

import java.io.File;
import java.util.Arrays;

/**
 * Created by norveo on 11/14/18.
 */
public class SentenceValidator {

    private static SentenceValidator instance = new SentenceValidator();

    public static SentenceValidator getInstance() {
        return instance;
    }

    public boolean validate(String sentence) {
        sentence = sentence.trim();

        if (!this.isValidStructure(sentence)) {
            throw new RuntimeException("'" + sentence + "'" + " <- malformed sentence.");
        }

        return true;
    }

    private boolean isValidStructure(String function) {
        char openParenthesis = '(';
        char closeParenthesis = ')';

        int parenthesisCount = -1;

        char[] characters = function.toCharArray();

        for (int index = 0; index <= characters.length - 1; index++) {
            if (characters[index] == openParenthesis) {
                parenthesisCount += 1;
            }

            if (characters[index] == closeParenthesis) {
                parenthesisCount -= 1;
            }
        }

        return parenthesisCount != 0 && this.isAValidStartAndEndOfTheFunction(function);
    }

    private boolean isAValidStartAndEndOfTheFunction(String name) {
        return name.matches("^([a-zA-Z]+\\()+.+\\)$");
    }

    private static void validateNestedFunctionsSentence(String sentence) {
        String packageName = "io.dojogeek.parser.functions";

        String path = packageName.replace(".", "/");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String[] methodsList = new File(classLoader.getResource(path).getFile()).list();

        String function = sentence.substring(0, sentence.indexOf("("));

        if (!Arrays.stream(methodsList)
                .map(fileName -> fileName.replace(".class", "").toLowerCase())
                .anyMatch(sentence.substring(0, sentence.indexOf("("))::equals)) {
            throw new RuntimeException("The '" + sentence + "'" + " function doesn't exist.");
        }
    }

}
