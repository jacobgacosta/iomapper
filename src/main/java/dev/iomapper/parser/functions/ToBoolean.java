package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToBoolean</b> class converts a type to boolean. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToBoolean implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Boolean.valueOf(value));

        return result;
    }

}
