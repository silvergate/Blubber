package com.dcrux.blubber.abstr.basecommands;

import com.dcrux.blubber.abstr.basecommands.itemmodifycommands.ItemModifyState;
import com.dcrux.blubber.iface.command.ICommandProcessor;
import com.dcrux.blubber.iface.command.ICommandProcessorFactory;

/**
 * Created by caelis on 21/05/14.
 */
public interface IItemModifyCommandProcessorFactory extends ICommandProcessorFactory<ItemModifyState, ICommandProcessor> {
}
