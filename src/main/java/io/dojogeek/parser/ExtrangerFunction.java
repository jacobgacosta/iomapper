package io.dojogeek.parser;

public class ExtrangerFunction implements Callable {

    public static int UNRECOGNIZED_FUNCTION = -1;

    @Override
    public Result invoke(String arguments) {
        Result result = new Result();
        result.setValue(UNRECOGNIZED_FUNCTION);

        return result;
    }

}
