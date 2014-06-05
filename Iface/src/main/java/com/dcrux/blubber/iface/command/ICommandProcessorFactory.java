package com.dcrux.blubber.iface.command;

/**
 * Created by caelis on 19/05/14.
 */
public interface ICommandProcessorFactory<TState, TCommandProcessor extends ICommandProcessor> {
    TCommandProcessor create(TState state);
}
