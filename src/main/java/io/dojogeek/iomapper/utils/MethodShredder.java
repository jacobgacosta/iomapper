package io.dojogeek.iomapper.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodShredder {

    public static SignatureMethod dismantle(String value) {
        String methodName = value.substring(0, value.indexOf("("));

        List<String> args = Arrays
                .asList(value.substring(value.indexOf("(") + 1, value.indexOf(")")).split(","))
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());

        SignatureMethod signatureMethod = new SignatureMethod();
        signatureMethod.setName(methodName);
        signatureMethod.setArgs(args);

        return signatureMethod;
    }

}
