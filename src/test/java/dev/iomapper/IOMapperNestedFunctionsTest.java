package dev.iomapper;

import dev.iomapper.dtos.NumericDto;
import dev.iomapper.models.NestedNumericModel;
import dev.iomapper.models.NumericModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by norveo on 10/15/18.
 */
public class IOMapperNestedFunctionsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private BridgeMap<NumericDto, NumericModel> map = new IOMapperBridge<>();

    @Test
    public void shouldThrowARuntimeExceptionForNonExistentFunction() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("The 'nonexistentFunction' singleFunction doesn't exist.");

        NumericModel numericModel = new NumericModel();
        numericModel.setByteField((byte) 127);
        numericModel.setShortField((short) 10000);

        NumericDto numericDto = map.outer().from(numericModel).to(NumericDto.class).relate(customMapping ->
            customMapping.relate("nonexistentFunction(byteField, shortField)", "stringI")
        ).build();
    }

    @Test
    public void shouldSupportNestedFunctions() {
        NumericModel numericModel = new NumericModel();
        numericModel.setByteField((byte) 127);
        numericModel.setShortField((short) 10000);

        NumericDto numericDto = map.outer().from(numericModel).to(NumericDto.class).relate(customMapping ->
            customMapping.relate("toString(add(add(byteField, shortField), 1))", "stringI")
        ).build();

        assertEquals("10128", numericDto.getStringI());
    }

    @Test
    public void shouldSupportNestedFunctionsOnNestedFields() {
        NestedNumericModel nestedNumericModel = new NestedNumericModel();
        nestedNumericModel.setNumber1(7);
        nestedNumericModel.setNumber2(7);

        NumericModel numericModel = new NumericModel();
        numericModel.setNestedNumericModel(nestedNumericModel);

        NumericDto numericDto = map.outer().from(numericModel).to(NumericDto.class).relate(customMapping ->
            customMapping.relate("nestedNumericModel.toString(add(add(number1, number2), 7))", "stringI")
        ).build();

        assertEquals("21", numericDto.getStringI());
    }

}
