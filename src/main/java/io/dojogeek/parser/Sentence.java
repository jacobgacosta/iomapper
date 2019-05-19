package io.dojogeek.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by norveo on 11/14/18.
 */
public class Sentence {

    public static final char OPENING_PARENTHESIS = '(';
    public static final char CLOSING_PARENTHESIS = ')';

    private int lastOpeningParenthesisPosition = 0;
    private int firstClosingParenthesisPosition = 0;

    public Result execute(String sentence) {
        this.validate(sentence);

        List<Function> functions = this.extractFunctions(sentence, new ArrayList<>());

        return Functions.wrap(functions)
                .validateAgainst(FunctionLoader.callable)
                .execute();
    }

    private List<Function> extractFunctions(String sentence, List<Function> functions) {
        if (!this.isCallable(sentence)) {
            return functions;
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

                Function function = new Function();
                function.setFunctionName(functionName);
                function.setArguments(arguments);

                if (FunctionLoader.callable.get(functionName) instanceof Callable) {
                    function.setCallable(FunctionLoader.callable.get(functionName));
                } else {
                    function.setCallable(new ExtrangerFunction());
                }

                functions.add(function);

                sentence = this.replaceOccurrencesInSentence(sentence, function.getSignature(), function.execute());

                this.resetSweepValues();

                break identifyContructor;
            }
        }

        return this.extractFunctions(sentence, functions);
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

    private String replaceOccurrencesInSentence(String sentence, String replaceable, Result result) {
        return sentence.replace(replaceable, String.valueOf(result.getValue()));
    }

    private void resetSweepValues() {
        this.lastOpeningParenthesisPosition = 0;
        this.firstClosingParenthesisPosition = 0;
    }

}
