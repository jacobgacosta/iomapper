package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/12/18.
 */
public class ToInt implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        Object value = values.get(values.size() - 1);

        int intValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            intValue = new Integer(value.toString());
        } else {
            intValue = (int) values.get(values.size() - 1);
        }

        return intValue;
    }

}
