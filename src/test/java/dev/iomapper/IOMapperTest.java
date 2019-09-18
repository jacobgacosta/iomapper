package dev.iomapper;

import dev.iomapper.dtos.AddressDto;
import dev.iomapper.dtos.ScholarshipDto;
import dev.iomapper.dtos.UserDto;
import dev.iomapper.models.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class IOMapperTest {

    private BridgeMap<UserDto, User> map = new IOMapperBridge<>();

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
        assertEquals("CDMX", user.getAddress().getState());
    }

    @Test
    public void shouldIgnoreFields() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");
        userDto.setJob("Programmer");

        User user = map.inner().from(userDto).to(User.class).ignoring(ignorableFields ->
            ignorableFields.ignore("name").ignore("job")
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
            ignorableFields.ignore("address.state")
        ).build();

        assertNull(user.getAddress().getState());
    }

    @Test
    public void shouldDoAnExplicitNestedMapping() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("03400");
        addressDto.setState("CDMX");

        UserDto userDto = new UserDto();
        userDto.setAddressDto(addressDto);

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
            customMapping
                .relate("addressDto.zip", "address.zipCode")
                .relate("addressDto.state", "fullAddress")
        ).build();

        assertEquals("03400", user.getAddress().getZipCode());
        assertEquals("CDMX", user.getFullAddress());
    }

    @Test
    public void shouldExecuteAFunctionThroughTheCustomMapping() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");
        userDto.setMiddleName("Guzman");
        userDto.setLastName("Acosta");

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
            customMapping
                .relate("concat(name, middleName, lastName, ['s'])", "name")
        ).build();

        assertEquals("Jacob Guzman Acosta", user.getName());
    }

    @Test
    public void shouldExecuteANestedFunctionThroughTheCustomMapping() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("03400");
        addressDto.setState("CDMX");

        UserDto userDto = new UserDto();
        userDto.setAddressDto(addressDto);

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
            customMapping
                .relate("addressDto.concat(state, zip, ['*'])", "fullAddress")
        ).build();

        assertEquals("CDMX*03400", user.getFullAddress());
    }

    @Test
    public void shouldMapAFieldToMultipleFieldsThroughCustomMapping() {
        UserDto userDto = new UserDto();
        userDto.setName("Jacob");

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
            customMapping
                .relate("name", "alias, userId")
        ).build();

        assertEquals("Jacob", user.getAlias());
        assertEquals("Jacob", user.getUserId());
    }

    @Test
    public void shouldNotMapAnObjectWithAPrimitiveType() {
        ScholarshipDto scholarshipDto = new ScholarshipDto();
        scholarshipDto.setInstanceYears(4);
        scholarshipDto.setSchoolName("Harvard");

        UserDto userDto = new UserDto();
        userDto.setScholarshipDto(scholarshipDto);

        User user = map.inner().from(userDto).to(User.class).build();

        assertNull(user.getScholarship());
    }

    @Test
    public void shouldNotMapAnObjectWithAPrimitiveFieldThroughCustomMapping() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("03400");

        UserDto userDto = new UserDto();
        userDto.setAddressDto(addressDto);

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
            customMapping.relate("addressDto", "address.zipCode")
        ).build();

        assertNull(user.getAddress().getZipCode());
    }

    @Test
    public void shouldNotMapAPrimitiveFieldWithAnObjectThroughCustomMapping() {
        UserDto userDto = new UserDto();
        userDto.setJob("Developer");

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
            customMapping.relate("job", "address")
        ).build();

        assertNull(user.getAddress());
    }

}
