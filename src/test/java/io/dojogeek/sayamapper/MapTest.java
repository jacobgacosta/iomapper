package io.dojogeek.sayamapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {

    private User originUser;

    @Before
    public void setup() {
        Address address = new Address();
        address.setStreet("Elm");
        address.setNumber("5233");

        BankCard bankCard = new BankCard();
        bankCard.setMark("Santander");

        this.originUser = new User();
        this.originUser.setName("Jacob");
        this.originUser.setAge(29);
        this.originUser.setEmail("dosek17@gmail.com");
        this.originUser.setAddress(address);
        this.originUser.setCard(bankCard);
    }

    @Test
    public void shouldMappingFieldsWithTheSameNameAndType() {
        UserDto userDto = Map.from(originUser).to(UserDto.class).build();

        assertNotNull(userDto);
        assertEquals("Jacob", userDto.getName());
        assertEquals("dosek17@gmail.com", userDto.getEmail());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void shouldIgnoreFieldsFromSourceAtFirstLevel() {
        UserDto userDto = Map.from(originUser).to(UserDto.class).ignoreFields(field ->
                field.toIgnore("email").toIgnore("name")
        ).build();

        assertNotNull(userDto);
        assertNull(userDto.getEmail());
        assertNull(userDto.getName());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void shouldMapNestedFieldsOfTheSameType() {
        UserDto userDto = Map.from(originUser).to(UserDto.class).customRelate(map -> {
            map.relate("address.street", "address");
            map.relate("card.mark", "cardDto.trademark");
        }).build();

        assertEquals("Elm", userDto.getAddress());
        assertEquals("Santander", userDto.getCardDto().getTrademark());
    }

}
