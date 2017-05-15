package org.telegram.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.telegram.lib.StreamUtils.readLong;
import static org.telegram.lib.StreamUtils.writeLong;
import static org.telegram.lib.TLUtils.SIZE_CONSTRUCTOR_ID;
import static org.telegram.lib.TLUtils.SIZE_INT32;
import static org.telegram.lib.TLUtils.SIZE_INT64;

public class TLLongVector extends TLVector<Long> {

    public TLLongVector() {
        super(Long.class);
    }

    @Override
    protected void serializeItem(Long item, OutputStream stream) throws IOException {
        writeLong(item, stream);
    }

    @Override
    protected Long deserializeItem(InputStream stream, TLContext context) throws IOException {
        return readLong(stream);
    }

    @Override
    public int computeSerializedSize() {
        return SIZE_CONSTRUCTOR_ID + SIZE_INT32 + SIZE_INT64 * size();
    }

    @Override
    public String toString() {
        return "vector<long>#1cb5c415";
    }

    public long[] toLongArray() {
        long[] array = new long[size()];
        for (int i = 0; i < array.length; i++)
            array[i] = items.get(i);
        return array;
    }
}
