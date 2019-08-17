package io.dojogeek.parser.functions;

import io.dojogeek.parser.Callable;
import io.dojogeek.parser.NumericArgumetValidator;
import io.dojogeek.parser.Result;

import java.util.List;

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
