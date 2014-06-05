package com.dcrux.blubber.abstr.basecommands.createitemcmd;

import com.dcrux.blubber.abstr.implementables.ICreateItemSvc;
import com.dcrux.blubber.iface.basecommands.CreateItemCmd;
import com.dcrux.blubber.iface.basecommands.CreateItemCmdReply;
import com.dcrux.blubber.iface.command.ICommandHandler;
import com.dcrux.blubber.iface.command.ICommandProcessor;
import com.dcrux.blubber.iface.common.ICallback;
import com.google.inject.Inject;

import java.util.logging.Logger;

/**
 * Created by caelis on 14/05/14.
 */
public class CreateItemHandler implements ICommandHandler<Void, CreateItemCmdReply, CreateItemCmd> {

    private static final Logger LOG = Logger.getLogger(CreateItemHandler.class.getName());

    @Inject
    private ICreateItemSvc createItemSvc;

    @Override
    public Class<CreateItemCmd> getCommandClass() {
        return CreateItemCmd.class;
    }

    @Override
    public void handle(ICommandProcessor self, Void state, CreateItemCmd command, ICallback<CreateItemCmdReply> callback) {
        //this.createItemSvc
    }

}
