package dev.iomapper;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by norveo on 10/15/18.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
    IOMapperTest.class,
    IOMapperNumericTest.class,
    IOMapperNestedFunctionsTest.class
})
public class IOMapperTestSuite {
}
