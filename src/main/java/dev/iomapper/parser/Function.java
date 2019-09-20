package dev.iomapper.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Function</b> is a wrapper for a function sentence that allows to operate on it.
 *
 * @author Jacob G. Acosta
 */
public class Function {

    public static final char OPENING_PARENTHESIS = '(';
    public static final char CLOSING_PARENTHESIS = ')';

    private String sentence;
    private int lastOpeningParenthesisPosition = 0;
    private int firstClosingParenthesisPosition = 0;
    private List<SingleFunction> nestedFunctionsList = new ArrayList<>();

    /**
     * Instantiates a new <b>Function</b> object, apply a validation on the <b>sentence</b>
     * and fills a list of functions found.
     * <p>
     * If the function has no other nested functions, the first level function is added
     * as the only element in the list of nested functions.
     *
     * @param sentence the sentence
     */
    public Function(String sentence) {
        this.validate(sentence);

        this.sentence = sentence;

        this.shredFunction(this.sentence, this.nestedFunctionsList);
    }

    /**
     * Gets the result of sentence execution. The sentence may contain nested functions.
     *
     * @return a @see dev.iomapper.parser.Result object
     */
    public Result execute() {
        return Functions.wrap(this.nestedFunctionsList)
            .validateAgainst(FunctionLoader.callable)
            .execute();
    }

    /**
     * Checks if the sentence contains nested functions.
     *
     * @return a boolean
     */
    public boolean hasNestedFunctions() {
        return this.nestedFunctionsList.size() > 1;
    }

    /**
     * Gets the most outer function in the sentence.
     *
     * @return @see dev.iomapper.parser.SingleFunction object
     */
    public SingleFunction getSingleFunction() {
        return this.nestedFunctionsList.get(0);
    }

    /**
     * Gets a list of nested functions.
     *
     * @return the nested function list
     */
    public List<SingleFunction> getNestedFunctionsList() {
        return this.nestedFunctionsList;
    }

    /**
     * Fills a list of functions contained in the sentence.
     *
     * @param sentence  the sentence
     * @param functions an empty list of functions
     * @return a list of functions
     */
    private List<SingleFunction> shredFunction(String sentence, List<SingleFunction> functions) {
        if (!this.isCallable(sentence)) {
            return this.nestedFunctionsList;
        }

        char[] granulatedSentence = this.getGranuleteFrom(sentence);

        identifyContructor:
        for (int index = 0; index < granulatedSentence.length; index++) {
            char character = granulatedSentence[index];

            if (character == OPENING_PARENTHESIS) {
                this.registerOpeningParenthesisPosition(index);
            } else if (character == CLOSING_PARENTHESIS) {
                this.registerClosingParenthesisPosition(index);
            }

            if (this.isReadyToPrepareAFunction()) {
                String functionName = this.deduceTheMostNestedFunctionName(sentence);

                String arguments = sentence.substring(this.getConstructorOpeningPosition() + 1, this.getConstructorClosingPosition());

                SingleFunction singleFunction = new SingleFunction();
                singleFunction.setName(functionName);
                singleFunction.setArguments(arguments);

                if (FunctionLoader.callable.get(functionName.toLowerCase()) instanceof Callable) {
                    singleFunction.setCallable(FunctionLoader.callable.get(functionName.toLowerCase()));
                } else {
                    singleFunction.setCallable(new ExtrangerFunction());
                }

                functions.add(singleFunction);

                if (functions.size() > 1) {
                    singleFunction.setReference(functions.get(functions.size() - 2).hash());
                }

                sentence = this.replaceOccurrencesInSentence(sentence, singleFunction.getSignature(), singleFunction.hash());

                this.resetSweepIndexes();

                break identifyContructor;
            }
        }

        return this.shredFunction(sentence, functions);
    }

    /**
     * Deduces the name of a function in a sentence.
     *
     * @param sentence the sentence
     * @return the name of the function
     */
    private String deduceTheMostNestedFunctionName(String sentence) {
        String subset = sentence.substring(0, this.getConstructorOpeningPosition());

        StringBuffer presumedFunctionName = new StringBuffer();

        for (int index = subset.length() - 1; index >= 0; index--) {
            if (!subset.split("")[index].matches("[^\\w]")) {
                presumedFunctionName = presumedFunctionName.append(subset.split("")[index]);

                continue;
            }

            break;
        }

        return presumedFunctionName.reverse().toString();
    }

    /**
     * Validates a function sentence.
     *
     * @param sentence the sentence
     */
    private void validate(String sentence) {
        sentence = sentence.trim();

        if (!this.isValidStructure(sentence)) {
            throw new RuntimeException("'" + sentence + "'" + " <- malformed sentence.");
        }
    }

    /**
     * Validates the function sentence structure.
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

        return parenthesisCount != 0 && this.isAValidStartAndEndOfTheFunction(sentence);
    }

    /**
     * Checks the start and end of the function sentence.
     *
     * @param sentence the sentence
     * @return a boolean
     */
    private boolean isAValidStartAndEndOfTheFunction(String sentence) {
        return sentence.matches("^([a-zA-Z]+\\()+.+\\)$");
    }

    /**
     * Checks the sentence corresponds to a valid callable function.
     *
     * @param sentence the sentence
     * @return a boolean
     */
    private boolean isCallable(String sentence) {
        return !sentence.equals(ExtrangerFunction.UNRECOGNIZED_FUNCTION) && this.isAValidStartAndEndOfTheFunction(sentence);
    }

    /**
     * Converts the setence to a char array.
     *
     * @param sentence the sentence
     * @return an array of chars
     */
    private char[] getGranuleteFrom(String sentence) {
        return sentence.toCharArray();
    }

    /**
     * Sets the position for an opening parenthesis in the sentence.
     *
     * @param position the position
     */
    private void registerOpeningParenthesisPosition(int position) {
        this.lastOpeningParenthesisPosition = position;
    }

    /**
     * Sets the position for an closing parenthesis in the sentence.
     *
     * @param position the position
     */
    private void registerClosingParenthesisPosition(int position) {
        this.firstClosingParenthesisPosition = position;
    }

    /**
     * Checks if the function in a sentence has already closed parenthesis indicating
     * the end of the signature.
     *
     * @return a boolean
     */
    private boolean isReadyToPrepareAFunction() {
        return this.firstClosingParenthesisPosition > 0;
    }

    /**
     * Gets the last registered position for an opening parenthesis in the funcion sentence.
     *
     * @return the position
     */
    private int getConstructorOpeningPosition() {
        return this.lastOpeningParenthesisPosition;
    }

    /**
     * Gets the last registered position for an closing parenthesis in the funcion sentence.
     *
     * @return the position
     */
    private int getConstructorClosingPosition() {
        return this.firstClosingParenthesisPosition;
    }

    /**
     * Replaces the function signature within a sentence using the hash of function to avoid
     * processing functions with a repeat signatures.
     *
     * @param sentence    the sentence
     * @param replaceable the signature to replace
     * @return the has of function
     */
    private String replaceOccurrencesInSentence(String sentence, String replaceable, String hash) {
        return sentence.replace(replaceable, hash);
    }

    /**
     * Resets the indexes for the opening and closing parentheses.
     */
    private void resetSweepIndexes() {
        this.lastOpeningParenthesisPosition = 0;
        this.firstClosingParenthesisPosition = 0;
    }

}
