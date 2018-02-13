package io.dojogeek.sayamapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

}
