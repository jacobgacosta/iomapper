package io.dojogeek.sayamapper;

public class Map {

    private Object origin;
    private Class destination;
    private Ignorable ignorable;
    private CustomMapper customRelations;

    private Map() {}

    public static Map from(Object source) {
        Map map = new Map();
        map.origin = source;
        return map;
    }

    public Map to(Class clazz) {
        this.destination = clazz;
        return this;
    }

    public <T> T build() {
        return this.fillDestinationObject();
    }

    private <T> T fillDestinationObject() {
        DestinationObject<T> destination = new DestinationObject<>(this.destination);
        destination.fillWith(new OriginObject(this.origin));
        destination.setIgnorable(this.ignorable);
        destination.setCustomMapping(this.customRelations);

        return destination.getFilledObject();
    }

    public Map ignoreFieldsFromSource(Ignorable ignorable) {
        this.ignorable = ignorable;
        return this;
    }

    public Map customRelate(CustomMapper customMapper) {
        this.customRelations = customMapper;
        return this;
    }

}
