package com.dcrux.blubber.abstr.implementables.keyvalue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caelis on 25/05/14.
 */
public class QueryResult implements Serializable {
    private final boolean hasMoreResults;
    private final List<byte[]> keys;

    public QueryResult(List<byte[]> keys, boolean hasMoreResults) {
        this.keys = keys;
        this.hasMoreResults = hasMoreResults;
    }

    public boolean isHasMoreResults() {
        return hasMoreResults;
    }

    public List<byte[]> getKeys() {
        return keys;
    }
}
