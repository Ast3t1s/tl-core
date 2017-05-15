package org.telegram.lib;

public class RpcErrorException extends Exception {

    private final int code;
    private final String tag;

    public RpcErrorException(int code, String tag) {
        super(code + ": " + tag);
        this.code = code;
        this.tag = tag;
    }

    public int getCode() {
        return code;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return getMessage();
    }

    public Integer getTagInteger() {
        String[] chunks = tag.split("_");
        return Integer.parseInt(chunks[chunks.length - 1]);
    }
}
