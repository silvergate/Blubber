package com.dcrux.blubber.iface.basecommands;

import com.dcrux.blubber.iface.command.IReplyList;
import com.dcrux.blubber.iface.item.ItemId;

import java.io.Serializable;

/**
 * Created by caelis on 13/05/14.
 */
public class CreateItemCmdReply implements Serializable {
    private ItemId itemId;
    private IReplyList<?> commandReplies;

    public CreateItemCmdReply(ItemId itemId) {
        this.itemId = itemId;
    }
}
