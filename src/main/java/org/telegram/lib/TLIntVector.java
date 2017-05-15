package org.telegram.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.telegram.lib.StreamUtils.readInt;
import static org.telegram.lib.StreamUtils.writeInt;
import static org.telegram.lib.TLUtils.SIZE_CONSTRUCTOR_ID;
import static org.telegram.lib.TLUtils.SIZE_INT32;

public class TLIntVector extends TLVector<Integer> {

    public TLIntVector() {
        super(Integer.class);
    }

    @Override
    protected void serializeItem(Integer item, OutputStream stream) throws IOException {
        writeInt(item, stream);
    }

    @Override
    protected Integer deserializeItem(InputStream stream, TLContext context) throws IOException {
        return readInt(stream);
    }

    @Override
    public int computeSerializedSize() {
        return SIZE_CONSTRUCTOR_ID + SIZE_INT32 + SIZE_INT32 * size();
    }

    @Override
    public String toString() {
        return "vector<int>#1cb5c415";
    }

    public int[] toIntArray() {
        int[] array = new int[size()];
        for (int i = 0; i < array.length; i++)
            array[i] = items.get(i);
        return array;
    }
}
