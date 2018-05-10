package io.dojogeek.sayamapper;

import io.dojogeek.dtos.NumericDto;
import io.dojogeek.models.Numeric;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SayaMapNumericTest {

    private BridgeMap<NumericDto, Numeric> numericMap = new SayaMapBridge<>();

    @Test
    public void shouldMapByteTypeWithWiderOtherNumbersThroughOfCustomRelations() {
        NumericDto numericDto = new NumericDto();
        numericDto.setByteValue((byte) 127);

        Numeric numeric = numericMap.inner().from(numericDto).to(Numeric.class).relate(customMapping ->
                customMapping
                        .relate("byteValue", "shortValue, intValue, longValue, doubleValue, floatValue")
        ).build();

        assertEquals(127, numeric.getShortValue());
        assertEquals(127, numeric.getIntValue());
        assertEquals(127, numeric.getLongValue());
        assertEquals(127.0, numeric.getDoubleValue(), 0.001);
        assertEquals(127.0, numeric.getFloatValue(), 0.001);
    }

    @Test
    public void shouldMapShortTypeWithWiderOtherNumbersThroughOfCustomRelations() {
        NumericDto numericDto = new NumericDto();
        numericDto.setShortValue((short) 127);

        Numeric numeric = numericMap.inner().from(numericDto).to(Numeric.class).relate(customMapping ->
                customMapping
                        .relate("shortValue", "intValue, longValue, doubleValue, floatValue")
        ).build();

        assertEquals(127, numeric.getIntValue());
        assertEquals(127, numeric.getLongValue());
        assertEquals(127.0, numeric.getDoubleValue(), 0.001);
        assertEquals(127.0, numeric.getFloatValue(), 0.001);
    }

    @Test
    public void shouldMapIntTypeWithWiderOtherNumbersThroughOfCustomRelations() {
        NumericDto numericDto = new NumericDto();
        numericDto.setIntValue(127);

        Numeric numeric = numericMap.inner().from(numericDto).to(Numeric.class).relate(customMapping ->
                customMapping
                        .relate("intValue", "longValue, doubleValue, floatValue")
        ).build();

        assertEquals(127, numeric.getLongValue());
        assertEquals(127.0, numeric.getDoubleValue(), 0.001);
        assertEquals(127.0, numeric.getFloatValue(), 0.001);
    }

    @Test
    public void shouldMapLongTypeWithWiderOtherNumbersThroughOfCustomRelations() {
        NumericDto numericDto = new NumericDto();
        numericDto.setLongValue(127);

        Numeric numeric = numericMap.inner().from(numericDto).to(Numeric.class).relate(customMapping ->
                customMapping
                        .relate("longValue", "doubleValue, floatValue")
        ).build();

        assertEquals(127.0, numeric.getDoubleValue(), 0.001);
        assertEquals(127.0, numeric.getFloatValue(), 0.001);
    }

    @Test
    public void shouldMapFloatTypeWithWiderOtherNumbersThroughOfCustomRelations() {
        NumericDto numericDto = new NumericDto();
        numericDto.setFloatValue(127);

        Numeric numeric = numericMap.inner().from(numericDto).to(Numeric.class).relate(customMapping ->
                customMapping
                        .relate("floatValue", "doubleValue")
        ).build();

        assertEquals(127.0, numeric.getDoubleValue(), 0.001);
    }

}
