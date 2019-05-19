package io.dojogeek.parser.functions;

import io.dojogeek.parser.Callable;
import io.dojogeek.parser.Result;
import io.dojogeek.sayamapper.Determiner;

import java.util.List;
import java.util.logging.Logger;

public class Concat implements Callable {

    private final static Logger LOGGER = Logger.getLogger(Concat.class.getName());

    @Override
    public Result invoke(String arguments) {
        String value = "";
        String delimiter = "";

        List<Object> values = (List<Object>) null;

        String lastArgument = (String) values.get(values.size() - 1);

        if (Determiner.isExtraArgument(lastArgument)) {
            String delimiterArgument = this.getDelimiterFrom(lastArgument);

            switch (delimiterArgument) {
                case "s":
                case "S":
                    delimiter = " ";
                    break;
                default:
                    delimiter = lastArgument;
            }

            values.remove(values.size() - 1);
        }

        for (Object argument : values) {
            value += argument + delimiter;
        }

        return null; //value.trim();
    }

    public String getDelimiterFrom(String delimiter) {
        String[] split = delimiter.split("\\'");

        return split[1];
    }

}
