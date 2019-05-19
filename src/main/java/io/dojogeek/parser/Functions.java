package io.dojogeek.parser;

import java.util.List;
import java.util.Map;

public class Functions {

    private List<Function> functionList;
    private static Functions functions = new Functions();

    private Functions() {
    }

    public static Functions wrap(List<Function> functionList) {
        functions.setFunctionList(functionList);

        return functions;
    }

    public Functions validateAgainst(Map<String, Callable> functionInfo) {
        this.functionList.forEach(function -> {
            if (functionInfo.get(function.getMethodName()) == null) {
                throw new RuntimeException("The '" + function.getMethodName() + "'" + " function doesn't exist.");
            }
        });

        return functions;
    }

    public Result execute() {
        Function executable = this.functionList.get(this.functionList.size() - 1);

        return executable.getCallable().invoke(executable.getArguments());
    }

    private void setFunctionList(List<Function> functionList) {
        this.functionList = functionList;
    }

}
