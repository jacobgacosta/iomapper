package dev.iomapper.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>ArgumentCleaner</b> helps to clean a serie of arguments in a String.
 * <p>
 * This class is used when the arguments come the way:
 * 127@byte, 10000@short where the type of the argument is separated by @ character
 *
 * @author Jacob G. Acosta
 */
public class ArgumentCleaner {

    private String arguments;

    /**
     * Instantiates a new Argument cleaner.
     *
     * @param arguments the arguments
     */
    public ArgumentCleaner(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Gets the arguments without the type sepparated by @ character.
     *
     * @return the clean arguments
     */
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

    /**
     * Gets a list of clean arguments.
     *
     * @return the clean arguments
     */
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
