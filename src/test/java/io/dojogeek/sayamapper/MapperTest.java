package io.dojogeek.sayamapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapperTest {

    private User source;

    @Before
    public void setup() {
        this.source = new User();
        this.source.setName("Jacob");
        this.source.setAge(29);
        this.source.setEmail("dosek17@gmail.com");
    }

    @Test
    public void fillAnObjectWithAnotherWhitCompatibleFields() {
        UserDto userDto = Mapper.from(source).to(UserDto.class).build();

        assertNotNull(userDto);
        assertEquals("Jacob", userDto.getName());
        assertEquals("dosek17@gmail.com", userDto.getEmail());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void ignoreFieldsFromSourceForMapping() {
        UserDto userDto = Mapper.from(source).to(UserDto.class).ignoreFromSource(toIgnore -> {
            toIgnore.add("name");
            toIgnore.add("email");
        }).build();

        assertNotNull(userDto);
        assertNull(userDto.getEmail());
        assertNull(userDto.getName());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void ignoreFieldsFromTargetForMapping() {
        UserDto userDto = Mapper.from(source).to(UserDto.class).ignoreFromTarget(toIgnore -> {
            toIgnore.add("name");
            toIgnore.add("email");
        }).build();

        assertNotNull(userDto);
        assertNull(userDto.getEmail());
        assertNull(userDto.getName());
        assertEquals(29, userDto.getAge());
    }

}
