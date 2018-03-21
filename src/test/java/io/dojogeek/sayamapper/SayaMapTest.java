package io.dojogeek.sayamapper;

import io.dojogeek.dtos.BankCardDto;
import io.dojogeek.dtos.UserDto;
import io.dojogeek.models.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SayaMapTest {

    private Mapper<UserDto, User> map = new SayaMap<>();

    @Test
    public void shouldReturnANotNullTargetInstance() {
        UserDto userDto = new UserDto();

        User user = map.from(userDto).to(User.class).build();

        assertNotNull(user);
    }

    @Test
    public void shouldMapTwoFieldsWithTheSameNamesAtFirstNestedLevel() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");

        User user = map.from(userDto).to(User.class).build();

        assertEquals(userDto.getName(), user.getName());
    }

    @Test
    public void shouldMapTwoFieldsWithIdenticalNamesAtFirstNestedLevel() {
        UserDto userDto = new UserDto();
        userDto.setEmailAddress("jgacosta@dojogeek.io");

        User user = map.from(userDto).to(User.class).build();

        assertEquals(userDto.getEmailAddress(), user.getEmail());
    }

    @Test
    public void shouldMapNestedMatchingFieldsForTwoObjects() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setTrademark("Santander");

        UserDto userDto = new UserDto();
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).to(User.class).build();

        assertEquals(bankCardDto.getTrademark(), user.getCard().getMark());
    }

    @Test
    public void shouldIgnoreFieldsForMappingAtFirstLevel() {
        UserDto userDto = new UserDto();
        userDto.setEmailAddress("jgacosta@dojogee.io");
        userDto.setName("Jacob G. Acosta");

        User user = map.from(userDto).to(User.class).ignoring(unwantedTargetList ->
                unwantedTargetList.ignore("emailAddress").ignore("name")
        ).build();

        assertNull(user.getEmail());
        assertNull(user.getName());
    }

}
