package org.telegram.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static org.telegram.lib.TLUtils.computeTLBytesSerializedSize;

public class TLGzipObject extends TLObject {

    public static final int CONSTRUCTOR_ID = 0x3072cfa1;

    private byte[] packedData;

    public TLGzipObject() {

    }

    public TLGzipObject(byte[] packedData) {
        this.packedData = packedData;
    }


    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamUtils.writeTLBytes(packedData, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        packedData = StreamUtils.readTLBytes(stream);
    }

    @Override
    public int computeSerializedSize() {
        return CONSTRUCTOR_ID + computeTLBytesSerializedSize(packedData.length);
    }

    @Override
    public String toString() {
        return "gzip_packed#3072cfa1";
    }

    @Override
    public int getConstructorId() {
        return CONSTRUCTOR_ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TLGzipObject)) return false;
        if (this == obj) return true;

        TLGzipObject o = (TLGzipObject) obj;
        return Arrays.equals(packedData, o.packedData);
    }

    public byte[] getPackedData() {
        return packedData;
    }

    public void setPackedData(byte[] packedData) {
        this.packedData = packedData;
    }
}
