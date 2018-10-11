package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/3/18.
 */
public class ToByte implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        Object value = values.get(values.size() - 1);

        byte byteValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            byteValue = new Byte(value.toString());
        } else {
            byteValue = (byte) values.get(values.size() - 1);
        }

        return byteValue;
    }

}
