package dev.iomapper.parser.functions;

import dev.iomapper.parser.*;

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

                int totalInt = 0;

                for (Integer argumen : integerArguments) {
                    totalInt += argumen;
                }

                result.setValue(totalInt);
                break;
            case LONG:
                List<Long> longArguments = new NumberArgumentConverter<Long>(",", new ArgumentCleaner(arguments).getCleanArguments(), Long.class).getArguments();

                long totalLong = 0;

                for (Long argumen : longArguments) {
                    totalLong += argumen;
                }

                result.setValue(totalLong);
                break;
            case FLOAT:
                List<Float> floatArguments = new NumberArgumentConverter<Float>(",", new ArgumentCleaner(arguments).getCleanArguments(), Float.class).getArguments();

                float totalFloat = 0;

                for (Float argumen : floatArguments) {
                    totalFloat += argumen;
                }

                result.setValue(totalFloat);
                break;
            case DOUBLE:
                List<Double> doubleArguments = new NumberArgumentConverter<Double>(",", new ArgumentCleaner(arguments).getCleanArguments(), Double.class).getArguments();

                double totalDouble = 0.0;

                for (Double argumen : doubleArguments) {
                    totalDouble += argumen;
                }

                result.setValue(totalDouble);
                break;
            default:
                System.out.print("Incompatible type");
        }

        return result;
    }

}
