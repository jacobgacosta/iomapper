package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/12/18.
 */
public class ToBoolean implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        Object value = values.get(values.size() - 1);

        boolean booleanValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            booleanValue = new Boolean(value.toString());
        } else {
            booleanValue = (boolean) values.get(values.size() - 1);
        }

        return booleanValue;
    }

}
