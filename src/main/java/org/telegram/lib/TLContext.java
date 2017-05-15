package org.telegram.lib;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

public abstract class TLContext {

    private HashMap<Integer, Class> registeredClasses;

    @Deprecated
    public TLContext() {
        registeredClasses = new HashMap<>();
        init();
    }

    public TLContext(int size) {
        registeredClasses = new HashMap<>(size);
        init();
    }

    protected abstract void init();

    public final boolean isSupportedObject(TLObject object) {
        return isSupportedObject(object.getConstructorId());
    }

    public final boolean isSupportedObject(int constructorId) {
        return registeredClasses.containsKey(constructorId);
    }

    public final <T extends TLObject> void registerClass(int constructorId, Class<T> clazz) {
        registeredClasses.put(constructorId, clazz);
    }

    public final TLObject deserializeMessage(byte[] data) throws IOException {
        return deserializeMessage(data, null, -1);
    }

    public final <T extends TLObject> T deserializeMessage(byte[] data, Class<T> clazz, int constructorId) throws IOException {
        return deserializeMessage(new ByteArrayInputStream(data), clazz, constructorId);
    }

    public final TLObject deserializeMessage(InputStream stream) throws IOException {
        return deserializeMessage(stream, null, -1);
    }

    @SuppressWarnings({"unchecked", "DuplicateThrows"})
    public final <T extends TLObject> T deserializeMessage(InputStream stream, Class<T> clazz, int constructorId) throws DeserializationException, IOException {
        int realConstructorId = StreamUtils.readInt(stream);
        if (constructorId != -1 && realConstructorId != constructorId) {
            throw new InvalidConstructorIdException(realConstructorId, constructorId);
        } else if (constructorId == -1) {
            constructorId = realConstructorId;
            clazz = null;
        }

        if (constructorId == TLGzipObject.CONSTRUCTOR_ID)
            return (T) deserializeMessage(unzipStream(stream));
        if (constructorId == TLBool.TRUE_CONSTRUCTOR_ID)
            return (T) TLBool.TRUE;
        if (constructorId == TLBool.FALSE_CONSTRUCTOR_ID)
            return (T) TLBool.FALSE;
        if (constructorId == TLVector.CONSTRUCTOR_ID) {
            return (T) deserializeVectorBody(stream, new TLVector<>());
        }

        try {
            if (clazz == null) {
                clazz = registeredClasses.get(constructorId);
                if (clazz == null)
                    throw new UnsupportedConstructorException(constructorId);
            }

            T message = clazz.getConstructor().newInstance();
            message.deserializeBody(stream, this);
            return message;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unable to deserialize data. This error should not happen", e);
        }
    }

    public final TLVector deserializeVector(InputStream stream) throws IOException {
        return deserializeVector(stream, new TLVector<>());
    }

    public final TLIntVector deserializeIntVector(InputStream stream) throws IOException {
        return (TLIntVector) deserializeVector(stream, new TLIntVector());
    }

    public final TLLongVector deserializeLongVector(InputStream stream) throws IOException {
        return (TLLongVector) deserializeVector(stream, new TLLongVector());
    }

    public final TLStringVector deserializeStringVector(InputStream stream) throws IOException {
        return (TLStringVector) deserializeVector(stream, new TLStringVector());
    }

    private TLVector<?> deserializeVector(InputStream stream, TLVector<?> vector) throws IOException {
        int constructorId = StreamUtils.readInt(stream);
        if (constructorId == TLGzipObject.CONSTRUCTOR_ID)
            return deserializeVector(unzipStream(stream));

        if (constructorId == TLVector.CONSTRUCTOR_ID)
            return deserializeVectorBody(stream, vector);

        throw new InvalidConstructorIdException(constructorId, TLVector.CONSTRUCTOR_ID);
    }

    private TLVector<?> deserializeVectorBody(InputStream stream, TLVector<?> vector) throws IOException {
        vector.deserializeBody(stream, this);
        return vector;
    }

    private InputStream unzipStream(InputStream stream) throws IOException {
        TLGzipObject obj = new TLGzipObject();
        obj.deserializeBody(stream, this);
        ByteArrayInputStream packedDataStream = new ByteArrayInputStream(obj.getPackedData());
        return new BufferedInputStream(new GZIPInputStream(packedDataStream));
    }
}
