package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToShort</b> class converts a type to short. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToShort implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Short.valueOf(value));

        return result;
    }

}
