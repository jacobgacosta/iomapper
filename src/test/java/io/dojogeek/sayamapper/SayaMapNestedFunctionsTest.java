package io.dojogeek.sayamapper;

import io.dojogeek.dtos.NumericDto;
import io.dojogeek.models.NumericModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by norveo on 10/15/18.
 */
public class SayaMapNestedFunctionsTest {

    private BridgeMap<NumericDto, NumericModel> map = new SayaMapBridge<>();

    @Test
    public void shouldSupportNestedFunctions() {
        NumericModel numericModel = new NumericModel();
        numericModel.setByteField((byte) 127);
        numericModel.setShortField((short) 10000);

        NumericDto numericDto = map.outer().from(numericModel).to(NumericDto.class).relate(customMapping ->
                    customMapping.relate("toString(add(byteField, shortField))", "stringI")
        ).build();

        assertEquals("10127", numericDto.getStringI());
    }

}
