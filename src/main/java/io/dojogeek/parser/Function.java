package io.dojogeek.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by norveo on 11/14/18.
 */
public class Function {

    public static final char OPENING_PARENTHESIS = '(';
    public static final char CLOSING_PARENTHESIS = ')';

    private String sentence;
    private int lastOpeningParenthesisPosition = 0;
    private int firstClosingParenthesisPosition = 0;
    private List<SingleFunction> nestedFunctionList = new ArrayList<>();

    public Function(String sentence) {
        this.validate(sentence);

        this.sentence = sentence;

        this.shredFunction(this.sentence, this.nestedFunctionList);
    }

    public Result execute() {
        return Functions.wrap(this.nestedFunctionList)
                .validateAgainst(FunctionLoader.callable)
                .execute();
    }

    public boolean hasNestedFunctions() {
        return this.nestedFunctionList.size() > 1;
    }

    public SingleFunction getSingleFunction() {
        return this.nestedFunctionList.get(0);
    }

    public List<SingleFunction> getNestedFunctionList() {
        return this.nestedFunctionList;
    }

    private List<SingleFunction> shredFunction(String sentence, List<SingleFunction> functions) {
        if (!this.isCallable(sentence)) {
            return this.nestedFunctionList;
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
                    singleFunction.setReferenceTo(functions.get(functions.size() - 2).hash());
                }

                sentence = this.replaceOccurrencesInSentence(sentence, singleFunction.getSignature(), singleFunction.hash());

                this.resetSweepIndexes();

                break identifyContructor;
            }
        }

        return this.shredFunction(sentence, functions);
    }

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

    private void validate(String sentence) {
        sentence = sentence.trim();

        if (!this.isValidStructure(sentence)) {
            throw new RuntimeException("'" + sentence + "'" + " <- malformed sentence.");
        }
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

    private boolean isCallable(String sentence) {
        return !sentence.equals(ExtrangerFunction.UNRECOGNIZED_FUNCTION) && this.isAValidStartAndEndOfTheFunction(sentence);
    }

    private char[] getGranuleteFrom(String sentence) {
        return sentence.toCharArray();
    }

    private void registerOpeningParenthesisPosition(int position) {
        this.lastOpeningParenthesisPosition = position;
    }

    private void registerClosingParenthesisPosition(int position) {
        this.firstClosingParenthesisPosition = position;
    }

    private boolean isReadyToPrepareAFunction() {
        return this.firstClosingParenthesisPosition > 0;
    }

    private int getConstructorOpeningPosition() {
        return this.lastOpeningParenthesisPosition;
    }

    private int getConstructorClosingPosition() {
        return this.firstClosingParenthesisPosition;
    }

    private String replaceOccurrencesInSentence(String sentence, String replaceable, String hash) {
        return sentence.replace(replaceable, hash);
    }

    private void resetSweepIndexes() {
        this.lastOpeningParenthesisPosition = 0;
        this.firstClosingParenthesisPosition = 0;
    }

}
