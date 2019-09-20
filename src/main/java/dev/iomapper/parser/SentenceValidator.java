package dev.iomapper.parser;

/**
 * <b>SentenceValidator</b> helps validate the structure of a function.
 *
 * @author Jacob G. Acosta
 */
public class SentenceValidator {

    private static SentenceValidator instance = new SentenceValidator();

    /**
     * Gets an instance of <b>SentenceValidator</b>.
     *
     * @return the instance
     */
    public static SentenceValidator getInstance() {
        return instance;
    }

    /**
     * Validates the sentence.
     *
     * @param sentence the sentence
     * @return the instance
     */
    public boolean validate(String sentence) {
        sentence = sentence.trim();

        if (!this.isValidStructure(sentence)) {
            throw new RuntimeException("'" + sentence + "'" + " <- malformed sentence.");
        }

        return true;
    }

    /**
     * Validates the sentence structure.
     *
     * @param sentence the sentence
     * @return a boolean
     */
    private boolean isValidStructure(String sentence) {
        char openParenthesis = '(';
        char closeParenthesis = ')';

        int parenthesisCount = -1;

        char[] characters = sentence.toCharArray();

        for (int index = 0; index <= characters.length - 1; index++) {
            if (characters[index] == openParenthesis) {
                parenthesisCount += 1;
            }

            if (characters[index] == closeParenthesis) {
                parenthesisCount -= 1;
            }
        }

        return parenthesisCount != 0 && this.isAValidFullSentence(sentence);
    }

    /**
     * Validates if the function sentence contains its correct opening and closing parentheses.
     *
     * @param sentence the sentence
     * @return a boolean
     */
    private boolean isAValidFullSentence(String sentence) {
        return sentence.matches("^[a-zA-Z]+(\\.[a-zA-Z0-9]+)*((\\([a-zA-Z0-9]+)*(,\\s[[a-zA-Z0-9]|\\[\\'[.|\\*]\\'\\]]+)*(\\))*)*$");
    }

}
