package io.dojogeek.parser;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FunctionLoader {

    public static Map<String, Callable> callable = new HashMap<>();

    static {
        String functionsPackageName = "io.dojogeek.parser.functions";

        String functionsPathLocation = functionsPackageName.replace(".", "/");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String[] functionsNames = new File(classLoader.getResource(functionsPathLocation).getFile()).list();

        List<String> cleanFunctionNames = Arrays.stream(functionsNames)
                .map(fileName -> fileName.replace(".class", ""))
                .collect(Collectors.toList());

        for (String functionName : cleanFunctionNames) {
            String classPath = functionsPackageName + "." + functionName;

            try {
                Callable callable = (Callable) Class.forName(classPath).newInstance();

                FunctionLoader.callable.put(classPath, callable);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

}
