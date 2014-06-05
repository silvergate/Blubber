package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.basecommands.itemreadcommands.IItemReadCommand;
import com.dcrux.blubber.iface.command.IMultiCommand;
import com.dcrux.blubber.iface.item.ItemId;

/**
 * Created by caelis on 13/05/14.
 */
public class ReadItemCmd implements IBaseCommand<ReadItemCmdReply> {
    private ItemId itemId;
    private IMultiCommand<IItemReadCommand<?>> readCommandList;
}
