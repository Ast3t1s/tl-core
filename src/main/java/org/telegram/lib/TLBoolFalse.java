package org.telegram.lib;

public class TLBoolFalse extends TLBool {

    public static final int CONSTRUCTOR_ID = 0xbc799737;

    @Override
    public int getConstructorId() {
        return CONSTRUCTOR_ID;
    }

    @Override
    public String toString() {
        return "boolFalse#bc799737";
    }
}
