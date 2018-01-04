package io.dojogeek.sayamapper;

import java.lang.reflect.Field;

public class AllocationHandler<T> {

    private java.util.Map<String, Object> originMap;
    private T destinationInstance;

    public AllocationHandler(java.util.Map<String, Object> originMap, T destinationInstance) {
        this.originMap = originMap;
        this.destinationInstance = destinationInstance;
    }

    public <T> T getPopulatedDestinationObject() {
        this.populateDestinationInstance();

        return (T) this.destinationInstance;
    }

    private void populateDestinationInstance() {
        originMap.forEach((fieldName, value) -> {
            this.mergeFieldAndValue(this.getFieldFromDestinationInstance(fieldName), value);
        });
    }

    private void mergeFieldAndValue(Field field, Object value) {
        if (!this.isMergeable(field, value)) {
            System.out.println("no merge for: " + field + " with value: " + value);
            return;
        }

        try {
            field.set(this.destinationInstance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean isMergeable(Field field, Object value) {
        try {
            field.set(this.destinationInstance, value);
        } catch (IllegalAccessException e) {
            return false;
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    private Field getFieldFromDestinationInstance(String fieldName) {
        Field field = null;

        try {
            field = this.destinationInstance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return field;
    }

}
