package org.telegram.lib;

import java.io.*;

import static org.telegram.lib.StreamUtils.readInt;
import static org.telegram.lib.StreamUtils.writeInt;
import static org.telegram.lib.TLUtils.*;

public abstract class TLObject implements Serializable {

    public abstract int getConstructorId();

    public final byte[] serialize() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(computeSerializedSize());
        serialize(stream);
        return stream.toByteArray();
    }

    public final void serialize(OutputStream stream) throws IOException {
        writeInt(getConstructorId(), stream);
        serializeBody(stream);
    }

    public final void deserialize(InputStream stream, TLContext context) throws IOException {
        int constructorId = readInt(stream);
        if (constructorId != getConstructorId())
            throw new InvalidConstructorIdException(constructorId, getConstructorId());
        deserializeBody(stream, context);
    }

    public void serializeBody(OutputStream stream) throws IOException {

    }

    public void deserializeBody(InputStream stream, TLContext context) throws IOException {

    }

    public int computeSerializedSize() {
        return SIZE_CONSTRUCTOR_ID; // Constructor is 4-byte (int32)
    }

    protected final void throwNullFieldException(String fieldName, int flags) throws NullPointerException {
        throw new NullPointerException("Attempt to serialize null field " + fieldName + ", flags = " + flags);
    }
}
