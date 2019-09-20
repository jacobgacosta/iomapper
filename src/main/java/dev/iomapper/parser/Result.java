package dev.iomapper.parser;

/**
 * <b>Result</b> is a wrapper class for the result of a callable object execution.
 *
 * @author Jacob G. Acosta
 */
public class Result {

    private Object value;

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the value
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
