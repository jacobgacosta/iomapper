package io.dojogeek.sayamapper;

import io.dojogeek.dtos.AddressDto;
import io.dojogeek.dtos.UserDto;
import io.dojogeek.models.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class SayaMapTest {

    private BridgeMap<UserDto, User> map = new SayaMapBridge<>();

    @Test
    public void shouldReturnANotNullTargetInstance() {
        UserDto userDto = new UserDto();

        User user = map.inner().from(userDto).to(User.class).build();

        assertNotNull(user);
    }

    @Test
    public void shouldMapFieldWithTheSameTypeAndName() {
        AddressDto addressDto = new AddressDto();
        addressDto.setState("CDMX");
        addressDto.setZip("03400");

        UserDto userDto = new UserDto();
        userDto.setName("Jacob");
        userDto.setJob("Programmer");
        userDto.setAddressDto(addressDto);

        User user = map.inner().from(userDto).to(User.class).build();

        assertEquals("Jacob", user.getName());
        assertEquals("Programmer", user.getJob());
    }

    @Test
    public void shouldIgnoreFields() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");
        userDto.setJob("Programmer");

        User user = map.inner().from(userDto).to(User.class).ignoring(target ->
            target.ignore("name").ignore("job")
        ).build();

        assertNull(user.getName());
        assertNull(user.getJob());
    }

    @Test
    public void shouldDoAnExplicitMapping() {
        UserDto userDto = new UserDto();
        userDto.setEmail("dosek17@gmail.com");

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
            customMapping
                    .relate("email", "userId")
        ).build();

        assertEquals("dosek17@gmail.com", user.getUserId());
    }

    @Test
    public void shouldIgnoreNestedFields() {
        AddressDto addressDto = new AddressDto();
        addressDto.setState("CDMX");
        addressDto.setZip("03400");

        UserDto userDto = new UserDto();
        userDto.setAddressDto(addressDto);

        User user = map.inner().from(userDto).to(User.class).ignoring(ignorableFields ->
                ignorableFields.ignore("zip")
        ).build();

        assertEquals("CDMX", user.getAddress().getState());
        assertNull(user.getAddress().getZipCode());
    }

    @Test
    public void shouldDoAnExplicitNestedMapping() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("03400");

        UserDto userDto = new UserDto();
        userDto.setAddressDto(addressDto);

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
                customMapping
                        .relate("addressDto.zip", "address.zipCode")
        ).build();

        assertEquals("03400", user.getAddress().getZipCode());
    }

    @Test
    public void shouldExecuteAFunctionThroughTheCustomMapping() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");
        userDto.setMiddleName("Guzman");
        userDto.setLastName("Acosta");

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
                customMapping
                        .relate("concat(name, middleName, lastName)", "name")
        ).build();

        assertEquals("JacobGuzmanAcosta", user.getName());
    }

}
