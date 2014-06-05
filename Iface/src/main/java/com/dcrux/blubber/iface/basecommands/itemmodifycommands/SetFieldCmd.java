package com.dcrux.blubber.iface.basecommands.itemmodifycommands;

import com.dcrux.blubber.iface.common.VoidReply;
import com.dcrux.blubber.iface.field.FieldNameIndex;
import com.dcrux.blubber.iface.field.NullableValue;

/**
 * Created by caelis on 20/05/14.
 */
public class SetFieldCmd implements IItemModifyCommand<VoidReply> {
    private FieldNameIndex name;
    private NullableValue value;
}
