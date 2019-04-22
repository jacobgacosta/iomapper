package io.dojogeek.sayamapper.utils;

import io.dojogeek.sayamapper.functions.Executable;

import java.io.File;
import java.util.Arrays;

public class Executor {

    public static Object executeFunction(String functionName, Object... args) {
        String packageName = "io.dojogeek.sayamapper.functions";

        String path = packageName.replace(".", "/");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String[] methodsList = new File(classLoader.getResource(path).getFile()).list();

        if (!Arrays.stream(methodsList)
                .map(fileName -> fileName.replace(".class", "").toLowerCase())
                .anyMatch(functionName::equalsIgnoreCase)) {
            throw new RuntimeException("The '" + functionName + "'" + " function doesn't exist.");
        }

        String classPath = packageName + "." + functionName.substring(0, 1).toUpperCase() + functionName.substring(1);

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
