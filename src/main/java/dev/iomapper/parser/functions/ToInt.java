package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToInt</b> class converts a type to int. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToInt implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Integer.valueOf(value));

        return result;
    }

}
