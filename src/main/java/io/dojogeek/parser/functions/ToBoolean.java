package io.dojogeek.parser.functions;

import io.dojogeek.parser.Callable;
import io.dojogeek.parser.Result;

/**
 * Created by norveo on 10/12/18.
 */
public class ToBoolean implements Callable {

    @Override
    public Result invoke(String arguments) {
        String value = arguments.split("@")[0];

        Result result = new Result();
        result.setValue(Boolean.valueOf(value));

        return result;
    }

}
