package io.dojogeek.sayamapper;

import io.dojogeek.dtos.NumericDto;
import io.dojogeek.models.NumericModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by norveo on 10/12/18.
 */
public class SayaMapNumberTest {

    private BridgeMap<NumericDto, NumericModel> map = new SayaMapBridge<>();

    @Test
    public void shouldCastStringToByteFieldThroughCustomMapping() {
        NumericDto numericDto = new NumericDto();
        numericDto.setStringB("127");

        NumericModel numericModel = map.inner().from(numericDto).to(NumericModel.class).relate(customMapping ->
                customMapping.relate("toByte(stringB)", "byteField")
        ).build();

        assertEquals(127, numericModel.getByteField());
    }

}
