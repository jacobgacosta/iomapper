package dev.iomapper.parser.functions;

import dev.iomapper.parser.Callable;
import dev.iomapper.parser.Result;

/**
 * Created by norveo on 10/14/18.
 */
public class ToString implements Callable {

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
