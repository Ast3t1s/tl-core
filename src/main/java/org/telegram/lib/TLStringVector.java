package org.telegram.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.telegram.lib.StreamUtils.readTLString;
import static org.telegram.lib.StreamUtils.writeString;
import static org.telegram.lib.TLUtils.SIZE_CONSTRUCTOR_ID;
import static org.telegram.lib.TLUtils.SIZE_INT32;

public class TLStringVector extends TLVector<String> {
    public TLStringVector() {
        super(String.class);
    }

    @Override
    protected void serializeItem(String item, OutputStream stream) throws IOException {
        writeString(item, stream);
    }

    @Override
    protected String deserializeItem(InputStream stream, TLContext context) throws IOException {
        return readTLString(stream);
    }

    @Override
    public int computeSerializedSize() {
        int size = SIZE_CONSTRUCTOR_ID + SIZE_INT32;
        for (String item : items)
            size += TLUtils.computeTLStringSerializedSize(item);
        return size;
    }


    @Override
    public String toString() {
        return "vector<string>#1cb5c415";
    }
}
