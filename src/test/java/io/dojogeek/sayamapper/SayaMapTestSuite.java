package io.dojogeek.sayamapper;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by norveo on 10/15/18.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SayaMapTest.class,
        SayaMapNumericTest.class,
        SayaMapNestedFunctionsTest.class
})
public class SayaMapTestSuite {
}
