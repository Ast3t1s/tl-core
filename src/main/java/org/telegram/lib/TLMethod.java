package org.telegram.lib;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class TLMethod<T extends TLObject> extends TLObject {

    private transient T response;

    public T deserializeResponse(byte[] data, TLContext context) throws IOException {
        return response = deserializeResponse(new ByteArrayInputStream(data), context);
    }

    public abstract T deserializeResponse(InputStream stream, TLContext context) throws IOException;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
