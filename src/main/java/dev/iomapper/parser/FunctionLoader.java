package dev.iomapper.parser;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class FunctionLoader {

    public static Map<String, Callable> callable = new HashMap<>();

    static {
        String functionsPackageName = "dev.iomapper.parser.functions";

        ServiceLoader<Callable> serviceLoader = ServiceLoader.load(Callable.class);

        List<String> buildInFunctionNames = new ArrayList<>();

        for (Callable implClass : serviceLoader) {
            buildInFunctionNames.add(implClass.getClass().getSimpleName());
        }

        loadFunctions(buildInFunctionNames, functionsPackageName);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String externalPackageName = "mapping.functions";

        String externalFunctionsPathLocation = externalPackageName.replace(".", "/");

        URL urlExternalResources = classLoader.getResource(externalFunctionsPathLocation);

        if (urlExternalResources != null) {
            String[] externalFunctionsNames = new File(urlExternalResources.getFile()).list();

            List<String> cleanExternalFunctionsNames = getFunctionNamesIn(externalFunctionsNames);

            loadFunctions(cleanExternalFunctionsNames, externalPackageName);
        }
    }

    private static List<String> getFunctionNamesIn(String[] functionsNames) {
        return Arrays.stream(functionsNames)
            .filter(fileName -> !fileName.contains("$1"))
            .map(fileName -> fileName.replace(".class", ""))
            .collect(Collectors.toList());
    }

    private static void loadFunctions(List<String> cleanFunctionNames, String functionsPackageName) {
        for (String functionName : cleanFunctionNames) {
            String classPath = functionsPackageName + "." + functionName;

            try {
                Class[] params = {};

                Callable callable = (Callable) Class.forName(classPath).getDeclaredConstructor(params).newInstance();

                FunctionLoader.callable.put(functionName.toLowerCase(), callable);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
