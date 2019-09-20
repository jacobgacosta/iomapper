package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToFloat</b> class converts a type to float. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToFloat implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Float.valueOf(value));

        return result;
    }

}
