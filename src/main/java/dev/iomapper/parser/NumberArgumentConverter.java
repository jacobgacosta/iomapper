package dev.iomapper.parser;

import java.util.ArrayList;
import java.util.List;


/**
 * <b>NumberArgumentConverter</b> converts a serie of arguments in a list of determinate type.
 *
 * @param <T> the type parameter
 */
public class NumberArgumentConverter<T> {

    private List<T> params;

    /**
     * Instantiates a new Number argument converter.
     * <p>
     * When the arguments are received the way: 1, 2, 3 in String type,
     * these are cataloged to a list of the defined type.
     *
     * @param delimiter the delimiter
     * @param arguments the arguments
     * @param type      the type
     */
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

    /**
     * Gets the arguments.
     *
     * @return the arguments list
     */
    public List<T> getArguments() {
        return this.params;
    }

}
