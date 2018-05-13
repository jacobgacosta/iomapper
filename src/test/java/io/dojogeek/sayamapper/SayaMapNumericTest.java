package io.dojogeek.sayamapper;

import io.dojogeek.dtos.NumericDto;
import io.dojogeek.models.Numeric;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SayaMapNumericTest {

    private BridgeMap<NumericDto, Numeric> numericMap = new SayaMapBridge<>();

    @Test
    public void shouldMapNumericTypeToWiderOtherNumbersThroughOfCustomRelations() {
        NumericDto numericDto = new NumericDto();
        numericDto.setByteValue((byte) 127);

        Numeric numeric = numericMap.inner().from(numericDto).to(Numeric.class).relate(customMapping ->
                customMapping
                        .relate("byteValue", "shortValue")
        ).build();

        assertEquals(127, numeric.getShortValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForDownCastingNumericTypesThroughOfCustomRelations() {
        NumericDto numericDto = new NumericDto();
        numericDto.setDoubleValue(123.89);

        numericMap.inner().from(numericDto).to(Numeric.class).relate(customMapping ->
                customMapping
                        .relate("doubleValue", "floatValue")
        ).build();
    }

}
