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
    public void fillAnObjectWithAnotherWhitCompatibleFields() {
        UserDto userDto = Map.from(originUser).to(UserDto.class).build();

        assertNotNull(userDto);
        assertEquals("Jacob", userDto.getName());
        assertEquals("dosek17@gmail.com", userDto.getEmail());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void ignoreFieldsFromSourceForMapping() {
        UserDto userDto = Map.from(originUser).to(UserDto.class).ignoreFieldsFromSource(toIgnore -> {
            toIgnore.add("name");
            toIgnore.add("email");
        }).build();

        assertNotNull(userDto);
        assertNull(userDto.getEmail());
        assertNull(userDto.getName());
        assertEquals(29, userDto.getAge());
    }

    @Test
    public void mapCustomFields() {
        UserDto userDto = Map.from(originUser).to(UserDto.class).customRelate(map -> {
            map.relate("address.street", "address");
            map.relate("card.mark", "cardDto.trademark");
        }).build();

        assertEquals("Elm", userDto.getAddress());
        assertEquals("Santander", userDto.getCardDto().getTrademark());
    }

}
