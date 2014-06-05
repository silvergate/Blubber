package com.dcrux.blubber.abstr.data;

import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDefId;
import com.dcrux.blubber.iface.security.DomainId;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Created by caelis on 14/05/14.
 */
public class LiveItemHeader implements Serializable {
    private String userId;
    private Long commitTime;
    @Nullable
    private byte[] domainId;
    private byte[] itemDefId;
    private long version;
    @Nullable
    private byte[] predecessor;
    @Nullable
    private byte[] rootItemId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Long commitTime) {
        this.commitTime = commitTime;
    }

    public DomainId getDomainId() {
        return new DomainId(domainId);
    }

    public void setDomainId(DomainId domainId) {
        this.domainId = domainId.getId();
    }

    public ItemDefId getItemDefId() {
        return new ItemDefId(itemDefId);
    }

    public void setItemDefId(ItemDefId itemDefId) {
        this.itemDefId = itemDefId.getId();
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Nullable
    public ItemId getRootItemId() {
        if (rootItemId != null)
            return new ItemId(rootItemId);
        else
            return null;
    }

    public void setRootItemId(@Nullable ItemId rootItemId) {
        if (rootItemId == null)
            this.rootItemId = null;
        else
            this.rootItemId = rootItemId.getId();
    }

    public byte[] serialize() {
        return SerializationUtils.serialize(this);
    }

    public static LiveItemHeader deserialize(byte[] data) {
        return (LiveItemHeader) SerializationUtils.deserialize(data);
    }

    @Nullable
    public byte[] getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(@Nullable byte[] predecessor) {
        this.predecessor = predecessor;
    }
}
