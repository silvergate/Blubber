package com.dcrux.blubber.abstr.basecommands.itemmodifycommands;

import com.dcrux.blubber.iface.basecommands.itemmodifycommands.SetFieldCmd;
import com.dcrux.blubber.iface.command.ICommandHandler;
import com.dcrux.blubber.iface.command.ICommandProcessor;
import com.dcrux.blubber.iface.common.ICallback;
import com.dcrux.blubber.iface.common.VoidReply;

/**
 * Created by caelis on 21/05/14.
 */
public class SetFieldCmdHandler implements ICommandHandler<ItemModifyState, VoidReply, SetFieldCmd> {

    @Override
    public Class<SetFieldCmd> getCommandClass() {
        return SetFieldCmd.class;
    }


    @Override
    public void handle(ICommandProcessor self, ItemModifyState itemModifyState, SetFieldCmd command, ICallback<VoidReply> callback) {

    }
}
