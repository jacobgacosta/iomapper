package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToString</b> class converts a type to String. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToString implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value;

        if (arguments.contains("@")) {
            value = arguments.split("@")[0];
        } else {
            value = arguments;
        }

        Result result = new Result();
        result.setValue(value);

        return result;
    }

}
