package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.command.ICommand;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
public interface IBaseCommand<TResult extends Serializable> extends Serializable, ICommand<TResult> {
}
