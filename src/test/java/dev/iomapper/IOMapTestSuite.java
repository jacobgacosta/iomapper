package dev.iomapper;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by norveo on 10/15/18.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IOMapTest.class,
        IOMapNumericTest.class,
        IOMapNestedFunctionsTest.class
})
public class IOMapTestSuite {
}
