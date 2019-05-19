package io.dojogeek.parser.functions;

import io.dojogeek.parser.Callable;
import io.dojogeek.parser.Result;

import java.util.List;

/**
 * Created by norveo on 10/12/18.
 */
public class ToFloat implements Callable {

    @Override
    public Result invoke(String arguments) {
        List<Object> values = (List<Object>) null;

        Object value = values.get(values.size() - 1);

        float floatValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            floatValue = new Float(value.toString());
        } else {
            floatValue = (float) values.get(values.size() - 1);
        }

        return null;
    }

}
