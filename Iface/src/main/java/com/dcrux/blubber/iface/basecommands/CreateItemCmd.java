package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.basecommands.itemmodifycommands.IItemModifyCommand;
import com.dcrux.blubber.iface.command.IMultiCommand;
import com.dcrux.blubber.iface.command.MultiCommand;
import com.dcrux.blubber.iface.itemdef.ItemDefIdOrHash;
import com.dcrux.blubber.iface.security.DomainId;

/**
 * Created by caelis on 13/05/14.
 */
public class CreateItemCmd implements IBaseCommand<CreateItemCmdReply>, ICreateUpdateItem {
    private ItemDefIdOrHash itemDefId;
    private DomainId domainId;
    private IMultiCommand<IItemModifyCommand<?>> commands = MultiCommand.none();

    public ItemDefIdOrHash getItemDefId() {
        return itemDefId;
    }

    public DomainId getDomainId() {
        return domainId;
    }

    public IMultiCommand<IItemModifyCommand<?>> getCommands() {
        return commands;
    }
}
