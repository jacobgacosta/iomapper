package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/12/18.
 */
public class ToShort implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        Object value = values.get(values.size() - 1);

        short shortValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            shortValue = new Short(value.toString());
        } else {
            shortValue = (short) values.get(values.size() - 1);
        }

        return shortValue;
    }

}
