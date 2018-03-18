package io.dojogeek.sayamapper;

import org.junit.Test;

import static org.junit.Assert.*;

public class SayaMapperTest {

    private Mapper map = new SayaMapper();

    @Test
    public void shouldMapFieldsByNameConvention() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setNumber("1234567890123456");
        bankCardDto.setTrademark("Santander");

        UserDto userDto = new UserDto();
        userDto.setName("Jacob");
        userDto.setAge(30);
        userDto.setEmail("jgacosta@dojogeek.io");
        userDto.setAddress("CDMX");
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).to(User.class);

        assertNotNull(userDto);
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getAge(), user.getAge());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertNull(user.getAddress());
        assertEquals(bankCardDto.getNumber(), user.getCard().getNumber());
        assertEquals(bankCardDto.getTrademark(), user.getCard().getMark());
    }

    @Test
    public void shouldIgnoreFieldsForMappingAtFirstLevel() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setNumber("1234567890123456");

        UserDto userDto = new UserDto();
        userDto.setAge(29);
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).ignoring(targetFields ->
                targetFields
                        .ignore("age")
                        .ignore("cardDto")
        ).to(User.class);

        assertNotNull(user);
        assertNull(user.getCard());
        assertEquals(0, user.getAge());
    }

    @Test
    public void shouldIgnoreNestedFieldsForMapping() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setNumber("1234567890123456");
        bankCardDto.setTrademark("Santander");

        UserDto userDto = new UserDto();
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).ignoring(targetFields ->
                targetFields
                        .ignore("card.number")
                        .ignore("card.mark")
        ).to(User.class);

        assertNotNull(user);
        assertNull(user.getCard().getNumber());
        assertNull(user.getCard().getMark());
    }

    @Test
    public void shouldMapCustomFieldsAtFirstLevel() {
        UserDto userDto = new UserDto();
        userDto.setUser("JGAcosta24");

        User user = map.from(userDto).relate(customMapping ->
                customMapping.relate("user", "id")
        ).to(User.class);

        assertNotNull(user);
        assertEquals(userDto.getUser(), user.getId());
    }

}
