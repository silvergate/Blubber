package com.dcrux.blubber.iface.basecommands.itemreadcommands;

import com.dcrux.blubber.iface.command.ICommand;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
public interface IItemReadCommand<TReply extends Serializable> extends ICommand<TReply> {
}
