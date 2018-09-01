package io.dojogeek.sayamapper.utils;

import io.dojogeek.sayamapper.functions.Executable;

public class Executor {

    public static Object executeFunction(String functionName, Object... args) {
        String classPath = "io.dojogeek.sayamapper.functions." + functionName.substring(0, 1).toUpperCase() + functionName.substring(1);

        Object result = null;

        try {
            Executable executable = (Executable) Class.forName(classPath).newInstance();

            result = executable.execute(args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }

}
