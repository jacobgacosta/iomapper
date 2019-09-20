package dev.iomapper.models;

/**
 * Created by norveo on 10/12/18.
 */
public class NumericModel {

    private NestedNumericModel nestedNumericModel;
    private byte byteField;
    private short shortField;
    private int intField;
    private long longField;
    private float floatField;
    private double doubleField;
    private boolean booleanField;

    public byte getByteField() {
        return byteField;
    }

    public void setByteField(byte byteField) {
        this.byteField = byteField;
    }

    public short getShortField() {
        return shortField;
    }

    public void setShortField(short shortField) {
        this.shortField = shortField;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public float getFloatField() {
        return floatField;
    }

    public void setFloatField(float floatField) {
        this.floatField = floatField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public NestedNumericModel getNestedNumericModel() {
        return nestedNumericModel;
    }

    public void setNestedNumericModel(NestedNumericModel nestedNumericModel) {
        this.nestedNumericModel = nestedNumericModel;
    }

}
