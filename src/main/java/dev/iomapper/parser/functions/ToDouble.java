package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToDouble</b> class converts a type to double. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToDouble implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Double.valueOf(value));

        return result;
    }

}
