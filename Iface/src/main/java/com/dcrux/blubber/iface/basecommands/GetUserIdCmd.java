package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.security.UserId;

/**
 * Created by caelis on 18/05/14.
 */
public class GetUserIdCmd implements IBaseCommand<UserId> {
    public static final GetUserIdCmd SINGLETON = new GetUserIdCmd();
}
