package com.dcrux.blubber.iface.field;

/**
 * Created by caelis on 19/05/14.
 */
public enum FieldType {
    integer(0),
    number(1),
    bool(2),
    string(3),
    binary(4),
    composition(5),
    //TODO: Deprecate arrays?
    @Deprecated
    integerArray(80),
    @Deprecated
    numberArray(81),
    @Deprecated
    boolArray(82),
    @Deprecated
    stringArray(83),
    @Deprecated
    binaryArray(84);

    private byte code;

    private FieldType(int code) {
        this.code = (byte) code;
    }

    public short getCode() {
        return code;
    }

    public FieldType getArrayVersion() {
        switch (this) {
            case integer:
                return integerArray;
            case number:
                return numberArray;
            case bool:
                return boolArray;
            case string:
                return stringArray;
            case binary:
                return binaryArray;
            default:
                throw new IllegalStateException("Method can only be called on non-array types.");
        }
    }
}
