package io.dojogeek.parser;

import java.util.List;
import java.util.Map;

public class Functions {

    private List<SingleFunction> singleFunctionList;
    private static Functions functions = new Functions();

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
        SingleFunction rootFunction = this.singleFunctionList.get(this.singleFunctionList.size() - 1);

        this.singleFunctionList.forEach(singleFunction -> {
            if (singleFunction.getReferenceTo() != null) {
                this.singleFunctionList.forEach(referencedFunction -> {
                    if (referencedFunction.hash().equals(singleFunction.getReferenceTo())) {
                        String replacedArguments = singleFunction.getArguments().replace(referencedFunction.hash(), referencedFunction.getResult().getValue().toString());

                        singleFunction.setArguments(replacedArguments);

                        singleFunction.execute();
                    }
                });
            } else {
                singleFunction.execute();
            }
        });

        return rootFunction.execute();
    }

    private void setSingleFunctionList(List<SingleFunction> singleFunctionList) {
        this.singleFunctionList = singleFunctionList;
    }

}
