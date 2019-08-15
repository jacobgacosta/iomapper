package io.dojogeek.parser;

public class NumericArgumetValidator {
    private StringBuilder typesOfArguments = new StringBuilder();

    public NumericArgumetValidator(String arguments) {
        for (String argument : arguments.split(",")) {
            if (argument.trim().contains("@")) {
                String[] parameterStructure = argument.trim().split("@");

                String type = parameterStructure[1];

                if (!type.equals("float") && !type.equals("double") && type.equals("long")) {
                    typesOfArguments.append(OperationTypeEnum.LONG + "|");

                    continue;
                } else if (!type.equals("float") &&
                        !type.equals("double") &&
                        !type.equals("long") &&
                        (type.equals("int") || type.equals("byte") || type.equals("short"))
                ) {
                    typesOfArguments.append(OperationTypeEnum.INT + "|");

                    continue;
                } else if (type.equals("double")) {
                    typesOfArguments.append(OperationTypeEnum.DOUBLE);

                    continue;
                } else if (type.equals("float")) {
                    typesOfArguments.append(OperationTypeEnum.FLOAT);

                    continue;
                }
            } else if (this.isInt(argument.trim())) {
                typesOfArguments.append(OperationTypeEnum.INT);

                continue;
            } else if (this.isLong(argument.trim())) {
                typesOfArguments.append(OperationTypeEnum.LONG);

                continue;
            } else if (this.isFloat(argument.trim())) {
                typesOfArguments.append(OperationTypeEnum.FLOAT);

                continue;
            } else if (this.isDouble(argument.trim())) {
                typesOfArguments.append(OperationTypeEnum.DOUBLE);

                continue;
            }
        }
    }

    public OperationTypeEnum getOperationType() {
        if (typesOfArguments.toString().contains(OperationTypeEnum.FLOAT.name())) {
            return OperationTypeEnum.FLOAT;
        } else if (typesOfArguments.toString().contains(OperationTypeEnum.DOUBLE.name())) {
            return OperationTypeEnum.DOUBLE;
        } else if (typesOfArguments.toString().contains(OperationTypeEnum.LONG.name())) {
            return OperationTypeEnum.LONG;
        }

        return OperationTypeEnum.INT;
    }

    private boolean isInt(String value) {
        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean isLong(String value) {
        try {
            Long.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean isFloat(String value) {
        try {
            Float.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean isDouble(String value) {
        try {
            Double.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
