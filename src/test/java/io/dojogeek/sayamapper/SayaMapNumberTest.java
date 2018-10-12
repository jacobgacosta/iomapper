package io.dojogeek.sayamapper;

import io.dojogeek.dtos.UserDto;
import io.dojogeek.models.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by norveo on 10/12/18.
 */
public class SayaMapNumberTest {

    private BridgeMap<UserDto, User> map = new SayaMapBridge<>();

    @Test
    public void shouldCastStringToByteFieldThroughCustomMapping() {
        UserDto userDto = new UserDto();
        userDto.setYears("127");

        User user = map.inner().from(userDto).to(User.class).relate(customMapping ->
                customMapping.relate("toByte(years)", "age")
        ).build();

        assertEquals(127, user.getAge());
    }

}
