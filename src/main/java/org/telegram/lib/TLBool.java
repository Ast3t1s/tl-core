package org.telegram.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.telegram.lib.StreamUtils.readInt;

public abstract class TLBool extends TLObject {

    public static final TLBool TRUE = new TLBoolTrue();
    public static final TLBool FALSE = new TLBoolFalse();

    public static final int TRUE_CONSTRUCTOR_ID = TLBoolTrue.CONSTRUCTOR_ID;
    public static final int FALSE_CONSTRUCTOR_ID = TLBoolFalse.CONSTRUCTOR_ID;

    public static TLBool get(boolean value) {
        return value ? TRUE : FALSE;
    }

    public static void serialize(boolean value, OutputStream stream) throws IOException {
        get(value).serialize(stream);
    }

    public static boolean deserialize(InputStream stream) throws IOException {
        int constructorId = readInt(stream);
        if (constructorId == TLBoolTrue.CONSTRUCTOR_ID)
            return true;
        if (constructorId == TLBoolFalse.CONSTRUCTOR_ID)
            return false;

        throw new InvalidConstructorIdException("Wrong TLBool constructor id. Found " + Integer.toHexString(constructorId)
                + ", expected: " + Integer.toHexString(TLBoolTrue.CONSTRUCTOR_ID)
                + " or " + Integer.toHexString(TLBoolFalse.CONSTRUCTOR_ID));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
