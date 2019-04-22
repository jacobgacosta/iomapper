package io.dojogeek.sayamapper;

import io.dojogeek.dtos.NumericDto;
import io.dojogeek.models.NumericModel;
import io.dojogeek.sayamapper.exceptions.NullValueException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by norveo on 10/15/18.
 */
public class SayaMapNestedFunctionsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private BridgeMap<NumericDto, NumericModel> map = new SayaMapBridge<>();

    @Test
    public void shouldThrowARuntimeExceptionForNonExistentFunction() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("The 'nonexistentFunction' function doesn't exist.");

        NumericModel numericModel = new NumericModel();
        numericModel.setByteField((byte) 127);
        numericModel.setShortField((short) 10000);

        NumericDto numericDto = map.outer().from(numericModel).to(NumericDto.class).relate(customMapping ->
                customMapping.relate("nonexistentFunction(byteField, shortField)", "stringI")
        ).build();
    }

    @Ignore
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
