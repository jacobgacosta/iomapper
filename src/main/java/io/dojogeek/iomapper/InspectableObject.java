package io.dojogeek.iomapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * InspectableObject is a wrapper to inspect easily a object.
 *
 * @author norvek
 */
public class InspectableObject {

    private Object object;

    /**
     * InspectableObject constructor.
     *
     * @param object the object to inspect.
     */
    public InspectableObject(Object object) {
        this.object = object;
    }

    /**
     * Returns a list of FlexibleField
     *
     * @return a FlexibleField object.
     */
    public List<FlexibleField> getDeclaredFields() {
        List<FlexibleField> fields = new ArrayList<>();

        Stream.of(this.object.getClass().getDeclaredFields()).forEach(field -> {
            if (field.getType().isPrimitive() ||
                    field.getType().isAssignableFrom(Byte.class) ||
                    field.getType().isAssignableFrom(Short.class) ||
                    field.getType().isAssignableFrom(Integer.class) ||
                    field.getType().isAssignableFrom(Long.class) ||
                    field.getType().isAssignableFrom(Float.class) ||
                    field.getType().isAssignableFrom(Double.class) ||
                    field.getType().isAssignableFrom(String.class)) {
                fields.add(new JavaField(field, this.object));
                return;
            }

            fields.add(new ForeignField(field, this.object));
        });

        return fields;
    }

}
