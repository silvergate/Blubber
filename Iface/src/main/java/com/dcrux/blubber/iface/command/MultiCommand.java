package com.dcrux.blubber.iface.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by caelis on 18/05/14.
 */
public class MultiCommand<TSingleCommand extends ICommand<?>> implements IMultiCommand<TSingleCommand> {

    private static final MultiCommand NONE = new MultiCommand<>(Collections.emptyList(), Collections.emptyList(), Execution.inOrderStopOnFailure);
    private final List<TSingleCommand> commands;
    private final List<TSingleCommand> commandsRo;
    private final Execution execution;

    private MultiCommand(List<TSingleCommand> commands, List<TSingleCommand> commandsRo, Execution execution) {
        this.commands = commands;
        this.commandsRo = commandsRo;
        this.execution = execution;
    }

    @SuppressWarnings(value = "unchecked")
    public static <TSingleCommand extends ICommand<?>> MultiCommand<TSingleCommand> none() {
        return NONE;
    }

    public static <TSingleCommand extends ICommand<?>> MultiCommand<TSingleCommand> single(TSingleCommand command) {
        final List<TSingleCommand> singleCommands = Collections.singletonList(command);
        return new MultiCommand<TSingleCommand>(singleCommands, singleCommands, Execution.inOrderStopOnFailure);
    }

    public static <TSingleCommand extends ICommand<?>> MultiCommand<TSingleCommand> multi(Execution execution, TSingleCommand... command) {
        final List<TSingleCommand> commands = Arrays.asList(command);
        final List<TSingleCommand> commandsRo = Collections.unmodifiableList(commands);
        return new MultiCommand<>(commands, commandsRo, execution);
    }

    @Override
    public Execution getExecution() {
        return this.execution;
    }

    @Override
    public List<TSingleCommand> getCommands() {
        return this.commandsRo
                ;
    }
}
