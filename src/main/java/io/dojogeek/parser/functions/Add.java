package io.dojogeek.parser.functions;

import io.dojogeek.parser.*;

import java.util.List;

public class Add implements Callable {

    @Override
    public Result invoke(String arguments) {
        NumericArgumetValidator numericArgumetValidator = new NumericArgumetValidator(arguments);

        OperationTypeEnum operationType = numericArgumetValidator.getOperationType();

        Result result = new Result();

        switch (operationType) {
            case INT:
                List<Integer> integerArguments = new NumberArgumentConverter<Integer>(",", new ArgumentCleaner(arguments).getCleanArguments(), Integer.class).getArguments();

                int total = 0;

                for (Integer argumen : integerArguments) {
                    total += argumen;
                }

                result.setValue(total);
            case LONG:
                List<Long> longArguments = new NumberArgumentConverter<Long>(",", arguments, Long.class).getArguments();
                break;
            case FLOAT:
                List<Float> floatArguments = new NumberArgumentConverter<Float>(",", arguments, Float.class).getArguments();
                break;
            case DOUBLE:
                List<Double> doubleArguments = new NumberArgumentConverter<Double>(",", arguments, Double.class).getArguments();
                break;
            default:
                System.out.print("Incompatible type");
        }

        return result;
    }

}
