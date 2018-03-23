package io.dojogeek.sayamapper;

import io.dojogeek.dtos.AddressDto;
import io.dojogeek.dtos.BankCardDto;
import io.dojogeek.dtos.UserDto;
import io.dojogeek.models.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class SayaMapTest {

    private Mapper<UserDto, User> map = new SayaMap<>();

    @Test
    public void shouldReturnANotNullTargetInstance() {
        UserDto userDto = new UserDto();

        User user = map.from(userDto).to(User.class).build();

        assertNotNull(user);
    }

    @Test
    public void shouldMapTwoFieldsWithTheSameNamesAndTypesAtFirstNestedLevel() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");

        User user = map.from(userDto).to(User.class).build();

        assertEquals(userDto.getName(), user.getName());
    }

    @Test
    public void shouldMapTwoFieldsWithIdenticalNamesAndTypesAtFirstNestedLevel() {
        UserDto userDto = new UserDto();
        userDto.setEmailAddress("jgacosta@dojogeek.io");

        User user = map.from(userDto).to(User.class).build();

        assertEquals(userDto.getEmailAddress(), user.getEmail());
    }

    @Test
    public void shouldMapNestedMatchingFieldsForTwoObjects() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setNumber("1234567891011121");
        bankCardDto.setTrademark("Santander");

        UserDto userDto = new UserDto();
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).to(User.class).build();

        assertEquals(bankCardDto.getTrademark(), user.getCard().getMark());
        assertEquals(bankCardDto.getNumber(), user.getCard().getNumber());
    }

    @Test
    public void shouldIgnoreFieldsForMappingAtFirstNestedLevel() {
        UserDto userDto = new UserDto();
        userDto.setEmailAddress("jgacosta@dojogee.io");
        userDto.setName("Jacob G. Acosta");

        User user = map.from(userDto).to(User.class).ignoring(unwantedTargetList ->
                unwantedTargetList.ignore("email").ignore("name")
        ).build();

        assertNull(user.getEmail());
        assertNull(user.getName());
    }

    @Test
    public void shouldIgnoreNestedFieldsForMapping() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setNumber("1234567891011121");
        bankCardDto.setTrademark("Dojogeek");

        UserDto userDto = new UserDto();
        userDto.setCardDto(bankCardDto);

        User user = map.from(userDto).to(User.class).ignoring(unwantedTargetList ->
                unwantedTargetList.ignore("card.number").ignore("card.mark")
        ).build();

        assertNull(user.getCard().getMark());
        assertNull(user.getCard().getNumber());
    }

    @Test
    public void shouldMapCustomRelationsAtFirstNestedLevelWithTheSameTypes() {
        UserDto userDto = new UserDto();
        userDto.setUser("jgacosta");

        User user = map.from(userDto).to(User.class).relate(customMapping ->
                customMapping.relate("user", "id")
        ).build();

        assertEquals(userDto.getUser(), user.getId());
    }

    @Test
    public void shouldMapNestedCustomRelationsWithTheSameTypes() {
        AddressDto addressDto = new AddressDto();
        addressDto.setAvenue("Popocatepetl");
        addressDto.setLocation("372 Int. 401");

        UserDto userDto = new UserDto();
        userDto.setAddressDto(addressDto);

        User user = map.from(userDto).to(User.class).relate(customMapping ->
                customMapping
                        .relate("addressDto.avenue", "address.street")
                        .relate("addressDto.location", "address.number")
        ).build();

        assertEquals(addressDto.getAvenue(), user.getAddress().getStreet());
        assertEquals(addressDto.getLocation(), user.getAddress().getNumber());
    }

}
