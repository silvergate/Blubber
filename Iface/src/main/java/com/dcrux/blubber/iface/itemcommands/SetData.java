package com.dcrux.blubber.iface.itemcommands;

import com.dcrux.blubber.iface.basecommands.itemmodifycommands.IItemModifyCommand;
import com.dcrux.blubber.iface.fieldmodify.IFieldModifier;
import com.dcrux.blubber.iface.item.FieldKey;

/**
 * Created by caelis on 13/05/14.
 */
@Deprecated
public class SetData implements IItemModifyCommand {
    private FieldKey key;
    private IFieldModifier setter;
}
