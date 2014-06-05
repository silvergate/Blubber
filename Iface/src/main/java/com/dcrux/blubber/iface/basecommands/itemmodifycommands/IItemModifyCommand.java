package com.dcrux.blubber.iface.basecommands.itemmodifycommands;

import com.dcrux.blubber.iface.command.ICommand;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
public interface IItemModifyCommand<TReply extends Serializable> extends ICommand<TReply> {
}
