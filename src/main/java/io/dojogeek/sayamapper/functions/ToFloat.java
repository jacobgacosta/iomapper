package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/12/18.
 */
public class ToFloat implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        Object value = values.get(values.size() - 1);

        float floatValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            floatValue = new Float(value.toString());
        } else {
            floatValue = (float) values.get(values.size() - 1);
        }

        return floatValue;
    }

}
