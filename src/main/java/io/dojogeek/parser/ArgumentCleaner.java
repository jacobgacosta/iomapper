package io.dojogeek.parser;

import java.util.ArrayList;
import java.util.List;

public class ArgumentCleaner {

    private String arguments;

    public ArgumentCleaner(String arguments) {
        this.arguments = arguments;
    }

    public String getCleanArguments() {
        String[] separateArguments = this.arguments.split(",");

        StringBuilder cleanArguments = new StringBuilder();

        for (String argument : separateArguments) {
            if (argument.contains("@")) {
                String[] singleArgument = argument.split("@");

                cleanArguments.append(singleArgument[0]);
            } else {
                cleanArguments.append(argument);
            }
        }

        return cleanArguments.toString().replace(" ", ", ");
    }

    public List<String> getCleanArgumentsList() {
        List<String> cleanArguments = new ArrayList<>();

        String[] separateArguments = this.arguments.split(",");

        for (String argument : separateArguments) {
            if (argument.contains("@")) {
                String[] singleArgument = argument.split("@");

                cleanArguments.add(singleArgument[0].trim());
            } else {
                cleanArguments.add(argument.trim());
            }
        }

        return cleanArguments;
    }
}
