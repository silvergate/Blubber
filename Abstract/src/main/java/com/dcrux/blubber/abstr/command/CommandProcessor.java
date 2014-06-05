package com.dcrux.blubber.abstr.command;

import com.dcrux.blubber.iface.command.*;
import com.dcrux.blubber.iface.common.ICallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caelis on 14/05/14.
 */
public abstract class CommandProcessor<TState, TCommandProcessor extends ICommandProcessor> implements ICommandProcessorFactory<TState, TCommandProcessor> {
    private Map<Class<? extends ICommand<?>>, ICommandHandler<TState, ? extends Serializable, ? extends ICommand<?>>> handlers = new HashMap<>();
    private Map<Class<? extends ICommand<?>>, IBatchCommandHandler<TState, ? extends Serializable, ? extends ICommand<?>>> multiHandlers = new HashMap<>();

    public void addHandler(ICommandHandler<TState, ? extends Serializable, ? extends ICommand<?>> handler) {
        this.handlers.put(handler.getCommandClass(), handler);
    }

    public void addHandler(IBatchCommandHandler<TState, ? extends Serializable, ? extends ICommand<?>> handler) {
        this.multiHandlers.put(handler.getCommandClass(), handler);
    }

    private <TLocalReply extends Serializable, TLocalCommand extends ICommand<TLocalReply>> boolean processSingle(ICommandProcessor self, TState state, TLocalCommand command, ICallback<TLocalReply> callback) {
        ICommandHandler handler = this.handlers.get(command.getClass());
        if (handler == null) {
            return false;
        } else {
            try {
                handler.handle(self, state, command, callback);
            } catch (Exception exception) {
                callback.failure(exception);
            }
            return true;
        }
    }

    private <TLocalReply extends Serializable, TLocalCommand extends IBatchCommand<TLocalReply, ?>> boolean processBatch(TState state, TLocalCommand command, ICallback<TLocalReply> callback) {
        IBatchCommandHandler handler = this.multiHandlers.get(command.getClass());
        if (handler == null) {
            return false;
        } else {
            try {
                handler.handle(state, (IBatchCommand) command, callback);
            } catch (Exception exception) {
                callback.failure(exception);
            }
            return true;
        }
    }

    @Deprecated
    private void processStepSerialRecursive(ICommandProcessor self, TState state, final int index, final IBatchCommand multiCommand, final ReplyList.ISequentialMode replies, final ICallback callback,
                                            final boolean stopOnFailure, final ICommandHandler handler) {
        boolean ended = multiCommand.getCommands().size() == index;
        boolean stopBecauseOfFailure = stopOnFailure && !replies.getMultiReply().isAllSuccess();
        if (ended || stopBecauseOfFailure) {
            callback.success(replies.getMultiReply());
            return;
        } else {
            ICommand currentCommand = (ICommand) multiCommand.getCommands().get(index);
            handler.handle(self, state, currentCommand, new ICallback() {
                @Override
                public void success(Object reply) {
                    replies.add(SingleReply.success((Serializable) reply));
                                        /* Continue */
                    processStepSerialRecursive(self, state, index + 1, multiCommand, replies, callback, stopOnFailure, handler);
                }

                @Override
                public void failure(Exception exception) {
                    replies.add(SingleReply.failure(exception));
                    /* Continue */
                    processStepSerialRecursive(self, state, index + 1, multiCommand, replies, callback, stopOnFailure, handler);
                }
            });
        }
    }

    @Deprecated
    private <TReplyLocal extends Serializable, TLocalCommand extends IBatchCommand<TReplyLocal, ?>> boolean processBatchByUsingSingleHandler(ICommandProcessor self, TState state, TLocalCommand command, ICallback<TReplyLocal> callback) {
        ICommandHandler handler = this.handlers.get(command.getClass());
        if (handler == null) {
            /* No matching single handler found */
            return false;
        }
        IBatchCommand.Execution execution = command.getExecution();
        boolean stopOnFailure = false;
        switch (execution) {
            case inOrderStopOnFailure:
                stopOnFailure = true;
            case inOrderDoNotStopOnFailure:
                ReplyList.ISequentialMode<TReplyLocal> replies = ReplyList.sequential();
                processStepSerialRecursive(self, state, 0, command, replies, callback, stopOnFailure, handler);
                break;
            case parallel:
                final ReplyList.IParallelMode repliesParallel = ReplyList.parallel(command.getCommands().size());
                for (int i = 0; i < command.getCommands().size(); i++) {
                    final int finalIndex = i;
                    ICommand currentCommand = command.getCommands().get(i);
                    handler.handle(self, state, currentCommand, new ICallback() {
                        @Override
                        public void success(Object reply) {
                            repliesParallel.set(finalIndex, SingleReply.success((Serializable) reply));
                            if (repliesParallel.isDone())
                                callback.success((TReplyLocal) repliesParallel.getMultiReply());
                        }

                        @Override
                        public void failure(Exception exception) {
                            repliesParallel.set(finalIndex, SingleReply.failure(exception));
                            if (repliesParallel.isDone())
                                callback.success((TReplyLocal) repliesParallel.getMultiReply());
                        }
                    });
                }
                break;
        }

        return true;
    }

    private void processStepSerialRecursiveMulti(ICommandProcessor self, TState state, final int index, final IMultiCommand multiCommand, final ReplyList.ISequentialMode replies, final ICallback callback,
                                                 final boolean stopOnFailure, final List<ICommandHandler> handlerList) {
        boolean ended = multiCommand.getCommands().size() == index;
        boolean stopBecauseOfFailure = stopOnFailure && !replies.getMultiReply().isAllSuccess();
        if (ended || stopBecauseOfFailure) {
            callback.success(replies.getMultiReply());
            return;
        } else {
            ICommand currentCommand = (ICommand) multiCommand.getCommands().get(index);
            handlerList.get(index).handle(self, state, currentCommand, new ICallback() {
                @Override
                public void success(Object reply) {
                    replies.add(SingleReply.success((Serializable) reply));
                                        /* Continue */
                    processStepSerialRecursiveMulti(self, state, index + 1, multiCommand, replies, callback, stopOnFailure, handlerList);
                }

                @Override
                public void failure(Exception exception) {
                    replies.add(SingleReply.failure(exception));
                    /* Continue */
                    processStepSerialRecursiveMulti(self, state, index + 1, multiCommand, replies, callback, stopOnFailure, handlerList);
                }
            });
        }
    }


    private <TLocalReply extends IReplyList<?>, TLocalCommand extends IMultiCommand<?>> void processMultiCommand(ICommandProcessor self, TState state, TLocalCommand command, ICallback<TLocalReply> callback) {
        if (command.getCommands().isEmpty()) {
            /* Shortcut for empty */
            callback.success((TLocalReply) ReplyList.empty());
        } else {
            if (command.getCommands().size() == 1) {
                ICommand singleCommand = command.getCommands().get(0);
                process(self, state, singleCommand, callback);
            } else {
                /* It's important that we collect all handler in advance (since it should fail if one handler is missing) */
                List<ICommandHandler> handlerList = new ArrayList<>();
                for (ICommand commandEntry : command.getCommands()) {
                    ICommandHandler handlerForEntry = this.handlers.get(commandEntry.getClass());
                    if (handlerForEntry == null)
                        throw new IllegalArgumentException("No handler for class " + commandEntry.getClass() + " found.");
                    handlerList.add(handlerForEntry);
                }

                IMultiCommand.Execution execution = command.getExecution();
                boolean stopOnFailure = false;
                switch (execution) {
                    case inOrderStopOnFailure:
                        stopOnFailure = true;
                    case inOrderDoNotStopOnFailure:
                        ReplyList.ISequentialMode<TLocalReply> replies = ReplyList.sequential();
                        processStepSerialRecursiveMulti(self, state, 0, command, replies, callback, stopOnFailure, handlerList);
                        break;
                    case parallel:
                        final ReplyList.IParallelMode repliesParallel = ReplyList.parallel(command.getCommands().size());
                        for (int i = 0; i < command.getCommands().size(); i++) {
                            final int finalIndex = i;
                            ICommand currentCommand = command.getCommands().get(i);
                            handlerList.get(i).handle(self, state, currentCommand, new ICallback() {
                                @Override
                                public void success(Object reply) {
                                    repliesParallel.set(finalIndex, SingleReply.success((Serializable) reply));
                                    if (repliesParallel.isDone())
                                        callback.success((TLocalReply) repliesParallel.getMultiReply());
                                }

                                @Override
                                public void failure(Exception exception) {
                                    repliesParallel.set(finalIndex, SingleReply.failure(exception));
                                    if (repliesParallel.isDone())
                                        callback.success((TLocalReply) repliesParallel.getMultiReply());
                                }
                            });
                        }
                        break;
                }

                throw new IllegalArgumentException("NOTE: Not implemented");
            }
        }
    }

    protected <TLocalReply extends Serializable, TLocalCommand extends ICommand<TLocalReply>> void process(ICommandProcessor self, TState state, TLocalCommand command, ICallback<TLocalReply> callback) {
        if (command instanceof IBatchCommand) {
            IBatchCommand batchCommand = (BatchCommand) command;
            boolean isBatchHandled = processBatch(state, batchCommand, callback);
            if (isBatchHandled)
                return;
            /* Not handled, try to find a single handler */
            boolean isFallbackHandled = processBatchByUsingSingleHandler(self, state, batchCommand, callback);
            if (!isFallbackHandled) {
                /* Error */
                throw new IllegalArgumentException("No handler found for command " + batchCommand.getCommandClass());
            }
        } else {
            if (command instanceof IMultiCommand) {
                IMultiCommand multiCommand = (MultiCommand) command;
                ICallback<IReplyList> replyListICallback = (ICallback<IReplyList>) callback;
                processMultiCommand(self, state, multiCommand, replyListICallback);
            } else {
                /* Just a normal single command */
                boolean isSingleHandled = processSingle(self, state, command, callback);
                if (isSingleHandled)
                    return;
            /* Not handled, pack the single command into a multi handler and try to handle this. */
                BatchCommand multiCommand = new BatchCommand(IBatchCommand.Execution.inOrderStopOnFailure, command);
                process(self, state, multiCommand, callback);
            }
        }
    }

}
