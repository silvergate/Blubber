package com.dcrux.blubber.abstr.basecommands;

import com.dcrux.blubber.iface.basecommands.GetUserIdCmd;
import com.dcrux.blubber.iface.command.ICommandHandler;
import com.dcrux.blubber.iface.command.ICommandProcessor;
import com.dcrux.blubber.iface.common.ICallback;
import com.dcrux.blubber.iface.security.UserId;

/**
 * Created by caelis on 18/05/14.
 */
//NOTE: Only a demo-user
public class GetUserIdCmdHandler implements ICommandHandler<Void, UserId, GetUserIdCmd> {

    private final UserId USERID_DEMO = new UserId("demo@demo.com");

    @Override
    public Class<GetUserIdCmd> getCommandClass() {
        return GetUserIdCmd.class;
    }

    @Override
    public void handle(ICommandProcessor self, Void state, GetUserIdCmd command, ICallback<UserId> callback) {
        callback.success(USERID_DEMO);
    }
}
