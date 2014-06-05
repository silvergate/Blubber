package com.dcrux.blubber.iface.itemcommands;

import com.dcrux.blubber.iface.basecommands.itemreadcommands.IItemReadCommand;
import com.dcrux.blubber.iface.fieldread.IFieldReader;
import com.dcrux.blubber.iface.item.FieldKey;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
@Deprecated
public class GetData<TValue extends Serializable> implements IItemReadCommand<TValue> {
    private FieldKey key;
    private IFieldReader<TValue> getter;
}
