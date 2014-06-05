package com.dcrux.blubber.iface.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by caelis on 16/05/14.
 */
@Deprecated
public class BatchCommand<TReply extends Serializable, TSingleCommand extends ICommand<TReply>> implements IBatchCommand<TReply, TSingleCommand> {

    private final Execution execution;
    private Class<TSingleCommand> singleCommandClass;
    private List<TSingleCommand> commandList = new ArrayList<>();
    private List<TSingleCommand> immutableCommandList = Collections.unmodifiableList(this.commandList);

    public BatchCommand(Execution execution, TSingleCommand command) {
        this.execution = execution;
        add(command);
    }

    public BatchCommand<TReply, TSingleCommand> add(TSingleCommand singleCommand) {
        Class<? extends ICommand> localClass = singleCommand.getClass();
        if (this.singleCommandClass == null)
            this.singleCommandClass = (Class<TSingleCommand>) localClass;
        else {
            if (!this.singleCommandClass.equals(localClass))
                throw new IllegalArgumentException("Added command class is wrong (all classes need to be equal - not derived).");
        }
        this.commandList.add(singleCommand);
        return this;
    }

    @Override
    public Execution getExecution() {
        return this.execution;
    }

    @Override
    public Class<TSingleCommand> getCommandClass() {
        return this.singleCommandClass;
    }

    @Override
    public List<TSingleCommand> getCommands() {
        return this.immutableCommandList;
    }
}
