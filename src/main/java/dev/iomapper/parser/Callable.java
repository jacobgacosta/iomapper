package dev.iomapper.parser;


/**
 * <b>Callable</b> is the contract for the functions implementations.
 *
 * @author Jacob G. Acosta
 */
public interface Callable {

    /**
     * Invoke the function implementation.
     *
     * @param arguments the arguments of function
     * @return a @see dev.iomapper.parser.Result object
     */
    Result invoke(String arguments);

}
