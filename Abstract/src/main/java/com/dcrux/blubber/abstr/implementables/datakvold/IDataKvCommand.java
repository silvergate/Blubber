package com.dcrux.blubber.abstr.implementables.datakvold;

import com.dcrux.blubber.iface.command.ICommand;

import java.io.Serializable;

/**
 * Created by caelis on 18/05/14.
 */
@Deprecated
public interface IDataKvCommand<TReply extends Serializable> extends ICommand<TReply> {
}
