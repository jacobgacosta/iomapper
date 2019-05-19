package io.dojogeek.parser.functions;

import io.dojogeek.parser.Callable;
import io.dojogeek.parser.Result;

import java.util.List;

/**
 * Created by norveo on 10/15/18.
 */
public class Add implements Callable {

    @Override
    public Result invoke(String arguments) {
        byte byteValue = 0;
        short shortValue = 0;
        int intValue = 0;
        long longValue = 0;
        float floatValue = 0;
        double doubleValue = 0.0;

        List<Object> values = (List<Object>) null;

        for (Object value : values) {
            if (value instanceof Double) {
                doubleValue += (double) value;
            } else if (value instanceof Float) {
                floatValue += (float) value;
            } else if (value instanceof Long) {
                longValue += (long) value;
            } else if (value instanceof Byte) {
                byteValue += (byte) value;
            } else if (value instanceof Short) {
                shortValue += (short) value;
            }
        }

        if (doubleValue != 0.0) {
            //return doubleValue + floatValue + longValue + intValue + byteValue + shortValue;
        } else if (floatValue != 0.0) {
            //return floatValue + longValue + intValue + byteValue + shortValue;
        }else if (longValue != 0) {
            //return longValue + intValue + byteValue + shortValue;
        }

        return null;//intValue + byteValue + shortValue;
    }

}
