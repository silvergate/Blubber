package com.dcrux.blubber.iface.index;

import java.io.Serializable;

/**
 * Created by caelis on 02/06/14.
 */
public class FieldIndexRange implements Serializable {
    private short firstIncluding;
    private short lastIncluding;
    //Wird abbrechen, falls eine Lücke vorhanden...
    private boolean continuous;

    public FieldIndexRange(short firstIncluding, short lastIncluding, boolean continuous) {
        this.firstIncluding = firstIncluding;
        this.lastIncluding = lastIncluding;
        this.continuous = continuous;
    }

    public short getFirstIncluding() {
        return firstIncluding;
    }

    public short getLastIncluding() {
        return lastIncluding;
    }

    public boolean isContinuous() {
        return continuous;
    }
}
