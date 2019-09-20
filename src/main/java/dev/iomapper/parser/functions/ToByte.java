package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * <b>ToByte</b> class converts a type to byte. The type needs to be compatible.
 *
 * @author Jacob G. Acosta
 */
public class ToByte implements Callable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Byte.valueOf(value));

        return result;
    }

}
