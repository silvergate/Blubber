package com.dcrux.blubber.abstr.implementation;

import com.dcrux.blubber.abstr.implementables.IUserSvc;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.iface.common.LightException;
import com.dcrux.blubber.iface.security.UserId;

/**
 * Created by caelis on 29/05/14.
 */
public class UserSvc implements IUserSvc {

    private static final String USERID_ID = "userId";

    @Override
    public ICallable<Void> setRequestUser(UserId user) {
        return executor -> {
            executor.state().set(UserSvc.class, USERID_ID, user);
            return null;
        };
    }

    @Override
    public ICallable<UserId> getRequestUser() {
        return executor -> {
            final UserId userId = (UserId) executor.state().get(UserSvc.class, USERID_ID);
            if (userId == null) {
                throw new LightException("No request user is set!");
            } else {
                return userId;
            }
        };
    }
}
