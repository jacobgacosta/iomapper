package dev.iomapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * InspectableObject helps inspect an object and choose its type.
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
     * Iterates over the inspectable object and choose the field type,
     *
     * @return a FlexibleField object.
     * @see dev.iomapper.JavaField or @see dev.iomapper.ForeignField.
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
