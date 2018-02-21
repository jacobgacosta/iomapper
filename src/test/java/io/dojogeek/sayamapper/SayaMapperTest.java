package io.dojogeek.sayamapper;

import org.junit.Test;

import static org.junit.Assert.*;

public class SayaMapperTest {

    private Mapper map = new SayaMapper();

    @Test
    public void shouldMapFieldsByNameConvention() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setNumber("1234567890123456");

        UserDto userDto = new UserDto();
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).to(User.class);

        assertNotNull(userDto);
        assertEquals(bankCardDto.getNumber(), user.getCard().getNumber());
    }

    @Test
    public void shouldIgnoreFieldsForMappingAtFirstLevel() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setNumber("1234567890123456");

        UserDto userDto = new UserDto();
        userDto.setAge(29);
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).ignoring(targetFields ->
            targetFields.ignore("age").ignore("cardDto")
        ).to(User.class);

        assertNotNull(user);
        assertNull(user.getCard());
        assertEquals(0, user.getAge());
    }



}
