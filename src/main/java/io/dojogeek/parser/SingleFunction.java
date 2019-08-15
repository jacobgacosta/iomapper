package io.dojogeek.parser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SingleFunction {

    private String name;
    private String arguments;
    private Callable callable;
    private String hash;
    private String referenceTo;
    private Result result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArguments() {
        return this.arguments;
    }

    public List<String> getArgumentsList() {
        List<String> arguments = new ArrayList<>();

        Stream.of(this.arguments.split(","))
                .map(String::trim)
                .forEach(arguments::add);

        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public Callable getCallable() {
        return callable;
    }

    public void setCallable(Callable callable) {
        this.callable = callable;
    }

    public Result execute() {
        this.result = this.callable.invoke(this.arguments);

        return this.result;
    }

    public String getSignature() {
        return this.name + "(" + this.arguments + ")";
    }

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

    public String getReferenceTo() {
        return referenceTo;
    }

    public void setReferenceTo(String referenceTo) {
        this.referenceTo = referenceTo;
    }

    public String getSetence() {
        return this.name + "(" + this.arguments + ")";
    }

    public Result getResult() {
        return this.result;
    }

}
