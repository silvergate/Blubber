package com.dcrux.blubber.iface.basecommands.itemreadcommands;

import com.dcrux.blubber.iface.field.FieldNameIndex;

/**
 * Created by caelis on 21/05/14.
 */
public class GetFieldCmd implements IItemReadCommand<GetFieldCmdReply> {
    private FieldNameIndex name;
}
