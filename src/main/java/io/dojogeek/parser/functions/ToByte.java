package io.dojogeek.parser.functions;

import io.dojogeek.parser.Callable;
import io.dojogeek.parser.Result;

import java.util.List;

/**
 * Created by norveo on 10/3/18.
 */
public class ToByte implements Callable {

    @Override
    public Result invoke(String arguments) {
        List<Object> values = (List<Object>) null;

        Object value = values.get(values.size() - 1);

        byte byteValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            byteValue = new Byte(value.toString());
        } else {
            byteValue = (byte) values.get(values.size() - 1);
        }

        return null;
    }

}
