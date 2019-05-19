package io.dojogeek.parser;

public class Function {

    private String methodName;
    private String arguments;
    private Callable callable;

    public String getMethodName() {
        return methodName;
    }

    public void setFunctionName(String methodName) {
        this.methodName = methodName;
    }

    public String getArguments() {
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
        return this.callable.invoke(this.arguments);
    }

    public String getSignature() {
        return this.methodName + "(" + this.getArguments() + ")";
    }

}
