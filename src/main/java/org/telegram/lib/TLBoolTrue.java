package org.telegram.lib;

public class TLBoolTrue extends TLBool {

    public static final int CONSTRUCTOR_ID = 0x997275b5;

    @Override
    public int getConstructorId() {
        return CONSTRUCTOR_ID;
    }

    @Override
    public String toString() {
        return "boolTrue#997275b5";
    }
}
