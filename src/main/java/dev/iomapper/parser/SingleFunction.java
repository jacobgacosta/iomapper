package dev.iomapper.parser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <b>SingleFunction</b> contains the information of a function.
 *
 * @author Jacob G. Acosta
 */
public class SingleFunction {

    private String name;
    private String arguments;
    private Callable callable;
    private String hash;
    private String reference;
    private Result result;

    /**
     * Gets the name of function.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the function name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets function arguments.
     *
     * @return the arguments
     */
    public String getArguments() {
        return this.arguments;
    }

    /**
     * Sets the function arguments.
     *
     * @param arguments the arguments
     */
    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Gets a function's arguments list.
     *
     * @return the arguments list
     */
    public List<String> getArgumentsList() {
        List<String> arguments = new ArrayList<>();

        Stream.of(this.arguments.split(","))
            .map(String::trim)
            .forEach(arguments::add);

        return arguments;
    }

    /**
     * Gets the callable object for the function.
     *
     * @return the callable
     */
    public Callable getCallable() {
        return callable;
    }

    /**
     * Sets the callable object for the function.
     *
     * @param callable the callable
     */
    public void setCallable(Callable callable) {
        this.callable = callable;
    }

    /**
     * Executes the function's callable.
     *
     * @return the result
     */
    public Result execute() {
        this.result = this.callable.invoke(this.arguments);

        return this.result;
    }

    /**
     * Gets the function signature.
     *
     * @return the signature
     */
    public String getSignature() {
        return this.name + "(" + this.arguments + ")";
    }

    /**
     * Generates a hash for the function.
     *
     * @return the hash
     */
    public String hash() {
        if (this.hash != null) {
            return this.hash;
        }

        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = (this.name + "(" + this.getArguments() + ")").getBytes();

        this.hash = messageDigest.digest(bytes).toString();

        return this.hash;
    }

    /**
     * Gets the function reference.
     *
     * @return the reference
     */
    public String getReference() {
        return this.reference;
    }

    /**
     * Sets a reference for the function, to act as a linked list in mapping operations.
     *
     * @param reference the reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Check if the function has a reference.
     *
     * @return the boolean
     */
    public boolean hasAReference() {
        return reference != null;
    }

    /**
     * Gets the function's execution result.
     *
     * @return the result
     */
    public Result getResult() {
        return this.result;
    }

}
