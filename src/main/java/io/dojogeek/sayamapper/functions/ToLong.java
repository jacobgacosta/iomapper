package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/12/18.
 */
public class ToLong implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        Object value = values.get(values.size() - 1);

        long longValue;

        if (value.getClass().getName().equals("java.lang.String")) {
            longValue = new Long(value.toString());
        } else {
            longValue = (long) values.get(values.size() - 1);
        }

        return longValue;
    }

}
