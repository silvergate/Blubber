package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.command.IReplyList;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
public class ReadItemCmdReply implements Serializable {
    private IReplyList<?> commandReplies;
}
