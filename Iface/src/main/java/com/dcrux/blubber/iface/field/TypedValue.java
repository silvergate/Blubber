package com.dcrux.blubber.iface.field;

import java.io.Serializable;

/**
 * Created by caelis on 19/05/14.
 */
public class TypedValue implements Serializable {
    private FieldType type;
    private Serializable value;

    private static final int INTEGER_SIZE = 8;
    private static final int STRING_SIZE_M = 2;
    private static final int MAX_SIZE = 524288;
    private static final int ARRAY_MAX_ENTRIES = 2048; //TODO: CHECK IN VALIDATE
    private static final int ARRAY_CONST_ENTRY_PLUS_SIZE = 1; //Every array entry adds 2 bytes (plus the content). Const types like integer, byte, ...
    private static final int ARRAY_VAR_ENTRY_PLUS_SIZE = 3; // Vor variable types like string, byte, ...

    private TypedValue(FieldType type, Serializable value) {
        this.type = type;
        this.value = value;
    }

    public FieldType getType() {
        return type;
    }

    public int getSize() {
        switch (this.type) {
            case integer:
                return INTEGER_SIZE;
            case string:
                return ((String) this.value).length() * STRING_SIZE_M;
            default:
                throw new IllegalArgumentException("Not implemented");
        }
    }

    private void assertType(FieldType type) {
        if (this.type != type)
            throw new IllegalStateException("Operation is not valid for type " + this.type + ", should be type " + type + ".");
    }

    public void validate() {
        if (this.value == null)
            throw new IllegalArgumentException("value is null");

        int size = getSize();
        if (size > MAX_SIZE)
            throw new IllegalArgumentException("Value is too large. Size is: " + size + " bytes, maximum is " + MAX_SIZE + " bytes.");
    }

    public static TypedValue integer(long value) {
        return new TypedValue(FieldType.integer, value);
    }

    public long getInteger() {
        assertType(FieldType.integer);
        return (long) this.value;
    }

    public static TypedValue string(String value) {
        return new TypedValue(FieldType.string, value);
    }

    public String getString() {
        assertType(FieldType.string);
        return (String) this.value;
    }

    public static TypedValue binary(byte[] value) {
        return new TypedValue(FieldType.binary, value);
    }
}
