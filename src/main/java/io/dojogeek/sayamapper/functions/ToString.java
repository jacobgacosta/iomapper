package io.dojogeek.sayamapper.functions;

import java.util.List;

/**
 * Created by norveo on 10/14/18.
 */
public class ToString implements Executable {

    @Override
    public Object execute(Object... args) {
        List<Object> values = (List<Object>) args[0];

        return values.get(values.size() - 1).toString();
    }

}
