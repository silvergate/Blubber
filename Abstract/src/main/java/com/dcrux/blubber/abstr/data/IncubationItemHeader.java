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
public class IncubationItemHeader implements Serializable {
    private String userId;
    private Long incubationTime;
    @Nullable
    private byte[] domainId;
    private byte[] itemDefId;
    private long version;
    @Nullable
    private byte[] predecessor;
    @Nullable
    private byte[] rootItemId;
    private byte[] connectionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getIncubationTime() {
        return incubationTime;
    }

    public void setIncubationTime(Long incubationTime) {
        this.incubationTime = incubationTime;
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

    public static IncubationItemHeader deserialize(byte[] data) {
        return (IncubationItemHeader) SerializationUtils.deserialize(data);
    }

    public byte[] getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(byte[] connectionId) {
        this.connectionId = connectionId;
    }

    @Nullable
    public byte[] getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(@Nullable byte[] predecessor) {
        this.predecessor = predecessor;
    }
}
