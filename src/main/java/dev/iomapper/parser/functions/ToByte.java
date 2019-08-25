package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * Created by norveo on 10/3/18.
 */
public class ToByte implements Callable {

    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Byte.valueOf(value));

        return result;
    }

}
