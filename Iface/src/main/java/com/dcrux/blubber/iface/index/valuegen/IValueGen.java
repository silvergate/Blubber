package com.dcrux.blubber.iface.index.valuegen;

import com.dcrux.blubber.iface.common.ValidationException;
import com.dcrux.blubber.iface.index.ValueType;

/**
 * Created by caelis on 02/06/14.
 */
public interface IValueGen {
    void validate() throws ValidationException;

    ValueType getOutputType();
}
