package io.dojogeek.parser;

import java.util.List;
import java.util.Map;

public class Functions {

    private static Functions functions = new Functions();
    private List<SingleFunction> singleFunctionList;

    private Functions() {
    }

    public static Functions wrap(List<SingleFunction> singleFunctionList) {
        functions.setSingleFunctionList(singleFunctionList);

        return functions;
    }

    public Functions validateAgainst(Map<String, Callable> functionInfo) {
        this.singleFunctionList.forEach(singleFunction -> {
            if (functionInfo.get(singleFunction.getName().toLowerCase()) == null) {
                throw new RuntimeException("The '" + singleFunction.getName() + "'" + " singleFunction doesn't exist.");
            }
        });

        return functions;
    }

    public Result execute() {
        this.singleFunctionList.forEach(function -> {
            if (function.hasAReference()) {
                this.singleFunctionList.forEach(functionToCheck -> {
                    if (functionToCheck.hash().equals(function.getReferenceTo())) {
                        String replacedArguments = function.getArguments().replace(functionToCheck.hash(), functionToCheck.getResult().getValue().toString());

                        function.setArguments(replacedArguments);

                        function.execute();
                    }
                });
            } else {
                function.execute();
            }
        });

        return this.singleFunctionList.get(this.singleFunctionList.size() - 1).getResult();
    }

    private void setSingleFunctionList(List<SingleFunction> singleFunctionList) {
        this.singleFunctionList = singleFunctionList;
    }

}
