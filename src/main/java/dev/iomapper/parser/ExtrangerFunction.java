package dev.iomapper.parser;

/**
 * <b>ExtrangerFunction</b> marks a function as strange if it not appear in
 * the callable list of @see dev.iomapper.parser.FunctionLoader object.
 *
 * @author Jacob G. Acosta
 */
public class ExtrangerFunction implements Callable {

    public static int UNRECOGNIZED_FUNCTION = -1;


    /**
     * {@inheritDoc}
     */
    @Override
    public Result invoke(String arguments) {
        Result result = new Result();
        result.setValue(UNRECOGNIZED_FUNCTION);

        return result;
    }

}
