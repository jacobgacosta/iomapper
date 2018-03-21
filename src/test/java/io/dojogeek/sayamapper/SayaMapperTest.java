package io.dojogeek.sayamapper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SayaMapperTest {

    private Mapper map = new SayaMapper();

    @Test
    public void shouldReturnANotNullTargetInstance() {
        assertNotNull(map.from(new UserDto()).to(User.class));
    }

    @Test
    public void shouldMapTwoJavaFieldsWithTheSameNames() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");

        User user = map.from(userDto).to(User.class);

        assertEquals(userDto.getName(), user.getName());
    }

    @Test
    public void shouldMapTwoForeignFieldsWithTheSameNames() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setTrademark("Santander");

        UserDto userDto = new UserDto();
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).to(User.class);

        assertEquals(bankCardDto.getTrademark(), user.getCard().getMark());
    }

}
