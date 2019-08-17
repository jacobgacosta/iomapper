package io.dojogeek.parser;

import java.util.ArrayList;
import java.util.List;

public class NumberArgumentConverter<T> {

    private List<T> params;

    public NumberArgumentConverter(String delimiter, String arguments, Class<T> type) {
        String[] args = arguments.split(delimiter);

        params = new ArrayList<>();

        for (String arg : args) {
            if (type.getName().equals(Byte.class.getTypeName())) {
                params.add((T) Byte.valueOf(arg.trim()));
            } else if (type.getName().equals(Short.class.getTypeName())) {
                params.add((T) Short.valueOf(arg.trim()));
            } else if (type.getName().equals(Integer.class.getTypeName())) {
                params.add((T) Integer.valueOf(arg.trim()));
            } else if (type.getName().equals(Long.class.getTypeName())) {
                params.add((T) Long.valueOf(arg.trim()));
            } else if (type.getName().equals(Float.class.getTypeName())) {
                params.add((T) Float.valueOf(arg.trim()));
            } else if (type.getName().equals(Double.class.getTypeName())) {
                params.add((T) Double.valueOf(arg.trim()));
            }
        }
    }

    public List<T> getArguments() {
        return this.params;
    }

}
