package io.dojogeek.parser.functions;

import io.dojogeek.parser.ArgumentCleaner;
import io.dojogeek.parser.Callable;
import io.dojogeek.parser.Result;
import io.dojogeek.iomapper.Determiner;

import java.util.List;
import java.util.logging.Logger;

public class Concat implements Callable {

    private final static Logger LOGGER = Logger.getLogger(Concat.class.getName());

    @Override
    public Result invoke(String arguments) {
        String delimiter = "";

        List<String> values = new ArgumentCleaner(arguments).getCleanArgumentsList();

        String lastArgument = values.get(values.size() - 1);

        if (Determiner.isExtraArgument(lastArgument)) {
            String delimiterArgument = this.getDelimiterFrom(lastArgument);

            switch (delimiterArgument) {
                case "s":
                case "S":
                    delimiter = " ";
                    break;
                default:
                    delimiter = delimiterArgument;
            }

            values.remove(values.size() - 1);
        }

        String finalResult = "";

        for (int index = 0; index < values.size(); index++) {
            if (index < values.size() - 1) {
                finalResult += values.get(index) +  delimiter;

                continue;
            }

            finalResult += values.get(index);
        }

        Result result = new Result();
        result.setValue(finalResult.trim());

        return result;
    }

    public String getDelimiterFrom(String delimiter) {
        String[] split = delimiter.split("\\'");

        return split[1];
    }

}
