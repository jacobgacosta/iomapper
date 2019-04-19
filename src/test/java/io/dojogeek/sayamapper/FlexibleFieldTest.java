package io.dojogeek.sayamapper;

import io.dojogeek.sayamapper.exceptions.NullValueException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;

public class FlexibleFieldTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldThrowNullValueExceptionIfValueIsNull() {
        expectedEx.expect(NullValueException.class);
        expectedEx.expectMessage("The value should not be null.");

        FlexibleFieldImplTest flexibleField = new FlexibleFieldImplTest(null, this);
        flexibleField.setValue(null);
    }

}

class FlexibleFieldImplTest extends FlexibleField {

    /**
     * FlexibleField constructor.
     *
     * @param field        the field.
     * @param parentObject the reference object that hosts the field.
     */
    protected FlexibleFieldImplTest(Field field, Object parentObject) {
        super(field, parentObject);
    }

}
