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

        for (String argumen :  separateArguments) {
            if (argumen.contains("@")) {
                String[] singleArgument = argumen.split("@");

                cleanArguments.append(singleArgument[0]);
            } else {
                cleanArguments.append(argumen);
            }
        }

        return cleanArguments.toString().replace(" ", ", ");
    }

}
