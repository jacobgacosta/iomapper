package dev.iomapper;

import dev.iomapper.dtos.NumericDto;
import dev.iomapper.BridgeMap;
import dev.iomapper.IOMapBridge;
import dev.iomapper.models.NumericModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by norveo on 10/12/18.
 */
public class IOMapNumericTest {

    private BridgeMap<NumericDto, NumericModel> map = new IOMapBridge<>();

    @Test
    public void shouldCastStringTypeToByteTypeThroughCustomMapping() {
        NumericDto numericDto = new NumericDto();
        numericDto.setStringB("127");
        numericDto.setStringS("10000");
        numericDto.setStringI("894393");
        numericDto.setStringL("873843784738");
        numericDto.setStringF("893234.90");
        numericDto.setStringD("483098434.3873");
        numericDto.setStringBo("true");

        NumericModel numericModel = map.inner().from(numericDto).to(NumericModel.class).relate(customMapping ->
                customMapping
                        .relate("toByte(stringB)", "byteField")
                        .relate("toShort(stringS)", "shortField")
                        .relate("toInt(stringI)", "intField")
                        .relate("toLong(stringL)", "longField")
                        .relate("toFloat(stringF)", "floatField")
                        .relate("toDouble(stringD)", "doubleField")
                        .relate("toBoolean(stringBo)", "booleanField")
        ).build();

        assertEquals(127, numericModel.getByteField());
        assertEquals(10000, numericModel.getShortField());
        assertEquals(894393, numericModel.getIntField());
        assertEquals(873843784738L, numericModel.getLongField());
        assertEquals(893234.90, numericModel.getFloatField(), 0.1);
        assertEquals(483098434.3873, numericModel.getDoubleField(), 0.1);
        assertEquals(true, numericModel.isBooleanField());
    }

    @Test
    public void shouldCastNumericTypeToStringTypeThroughCustomMapping() {
        NumericModel numericModel = new NumericModel();
        numericModel.setBooleanField(true);

        NumericDto numericDto = map.outer().from(numericModel).to(NumericDto.class).relate(customMapping ->
                customMapping.relate("toString(booleanField)", "stringBo")
        ).build();

        assertEquals("true", numericDto.getStringBo());
    }

    @Test
    public void shouldAddSomeNumbers() {
        NumericModel numericModel = new NumericModel();
        numericModel.setByteField((byte) 127);
        numericModel.setShortField((short) 10000);
        numericModel.setDoubleField(99.902);
        numericModel.setFloatField(93.90f);

        NumericDto numericDto = map.outer().from(numericModel).to(NumericDto.class).relate(customMapping ->
                customMapping.relate("add(byteField, shortField)", "iValue")
                             .relate("add(doubleField, floatField)", "dValue")
        ).build();

        assertEquals(10127, numericDto.getiValue());
        assertEquals(193.8020015258789, numericDto.getdValue(), 0.001);
    }

}
