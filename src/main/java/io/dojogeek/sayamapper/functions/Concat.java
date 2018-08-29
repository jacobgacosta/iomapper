package io.dojogeek.sayamapper.functions;

import java.util.List;

public class Concat implements Executable {

    @Override
    public Object execute(Object... args) {
        String value = "";

        List<Object> values = (List<Object>) args[0];

        for (Object argument : values) {
            value += argument;
        }

        return value;
    }

}
