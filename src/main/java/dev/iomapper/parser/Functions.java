package dev.iomapper.parser;

import java.util.List;
import java.util.Map;


/**
 * <b>Functions</b> helps validate and execute a list of @see dev.iomapper.parser.SingleFunction objects.
 *
 * @author Jacob G. Acosta
 */
public class Functions {

    private static Functions functions = new Functions();
    private List<SingleFunction> singleFunctionsList;

    private Functions() {
    }

    /**
     * Sets the <b>singleFunctionList</b>
     *
     * @param singleFunctionsList the single functions list
     * @return a static reference to <b>Functions</b> instance
     */
    public static Functions wrap(List<SingleFunction> singleFunctionsList) {
        functions.setSingleFunctionList(singleFunctionsList);

        return functions;
    }

    /**
     * Validates against a map of functions.
     *
     * @param functionsInfo the functions map
     * @return a static reference to <b>Functions</b> instance
     */
    public Functions validateAgainst(Map<String, Callable> functionsInfo) {
        this.singleFunctionsList.forEach(singleFunction -> {
            if (functionsInfo.get(singleFunction.getName().toLowerCase()) == null) {
                throw new RuntimeException("The '" + singleFunction.getName() + "'" + " singleFunction doesn't exist.");
            }
        });

        return functions;
    }

    /**
     * Executes the Callable of functions.
     *
     * @return a @see dev.iomapper.parser.Result object
     */
    public Result execute() {
        this.singleFunctionsList.forEach(function -> {
            if (function.hasAReference()) {
                this.singleFunctionsList.forEach(functionToCheck -> {
                    if (functionToCheck.hash().equals(function.getReference())) {
                        String replacedArguments = function.getArguments().replace(functionToCheck.hash(), functionToCheck.getResult().getValue().toString());

                        function.setArguments(replacedArguments);

                        function.execute();
                    }
                });
            } else {
                function.execute();
            }
        });

        return this.singleFunctionsList.get(this.singleFunctionsList.size() - 1).getResult();
    }

    /**
     * Sets the <b>singleFunctionList</b>
     *
     * @param singleFunctionsList the single functions list
     */
    private void setSingleFunctionList(List<SingleFunction> singleFunctionsList) {
        this.singleFunctionsList = singleFunctionsList;
    }

}
