package dev.iomapper.parser;

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

        return parenthesisCount != 0 && this.isAValidFullSentence(function);
    }

    private boolean isAValidFullSentence(String name) {
        return name.matches("^[a-zA-Z]+(\\.[a-zA-Z0-9]+)*((\\([a-zA-Z0-9]+)*(,\\s[[a-zA-Z0-9]|\\[\\'[.|\\*]\\'\\]]+)*(\\))*)*$");
    }

}
