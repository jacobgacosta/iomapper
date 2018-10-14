package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/12/18.
 */
public class ToDouble implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        Object value = values.get(values.size() - 1);

        double doubleValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            doubleValue = new Double(value.toString());
        } else {
            doubleValue = (double) values.get(values.size() - 1);
        }

        return doubleValue;
    }

}
