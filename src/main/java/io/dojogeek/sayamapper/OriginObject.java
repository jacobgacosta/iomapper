package io.dojogeek.sayamapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class OriginObject implements Inspectable {

    private Object origin;

    public OriginObject(Object origin) {
        this.origin = origin;
    }

    public Map<String, Object> getPropertiesAndValuesMap() {
        return Arrays.asList(origin.getClass().getDeclaredFields())
                .stream()
                .filter(field -> this.getValueFor(field) != null)
                .collect(Collectors.toMap(field -> field.getName(), field -> this.getValueFor(field)));
    }

    @Override
    public Object getValueFor(String field) {
        Object value = this.origin;

        for (String fieldName : field.split("\\.")) {
            try {
                Field declaredField = value.getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                value = declaredField.get(value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    private Object getValueFor(Field field) {
        field.setAccessible(true);
        Object value = null;

        try {
            value = field.get(origin);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return value;
    }

}
