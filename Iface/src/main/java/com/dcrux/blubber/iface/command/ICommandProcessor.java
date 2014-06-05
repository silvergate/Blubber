package com.dcrux.blubber.iface.command;

import com.dcrux.blubber.iface.common.ICallback;

import java.io.Serializable;

/**
 * Created by caelis on 14/05/14.
 */
public interface ICommandProcessor {
    <TLocalReply extends Serializable, TLocalCommand extends ICommand<TLocalReply>> void process(TLocalCommand command, ICallback<TLocalReply> callback);
}
