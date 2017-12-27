package io.dojogeek.sayamapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {

    private User userSource;

    @Before
    public void setup() {
        Address address = new Address();
        address.setStreet("Elm");
        address.setNumber("5233");

        this.userSource = new User();
        this.userSource.setName("Jacob");
        this.userSource.setAge(29);
        this.userSource.setEmail("dosek17@gmail.com");
        this.userSource.setAddress(address);
    }

    @Test
    public void fillAnObjectWithAnotherWhitCompatibleFields() {
        UserDto userDto = Map.from(userSource).to(UserDto.class).build();

        assertNotNull(userDto);
        assertEquals("Jacob", userDto.getName());
        assertEquals("dosek17@gmail.com", userDto.getEmail());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void ignoreFieldsFromSourceForMapping() {
        UserDto userDto = Map.from(userSource).to(UserDto.class).ignoreFieldsFromSource(toIgnore -> {
            toIgnore.add("name");
            toIgnore.add("email");
        }).build();

        assertNotNull(userDto);
        assertNull(userDto.getEmail());
        assertNull(userDto.getName());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void mapNestedFieldsFromSourceObjectToTargetObjectWithTheSameFieldsNames() {
        UserDto userDto = Map.from(userSource).to(UserDto.class).customRelate(map -> {
            map.relate("address.street", "address");
        }).build();

        assertEquals("Elm", userDto.getAddress());
    }

}
