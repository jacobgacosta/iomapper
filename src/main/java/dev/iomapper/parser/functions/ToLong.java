package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToLong</b> class converts a type to long. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToLong implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Long.valueOf(value));

        return result;
    }

}
