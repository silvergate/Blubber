package com.dcrux.blubber.iface.index;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caelis on 02/06/14.
 */
public class IndexesDef implements Serializable {
    private Map<Byte, IndexDef> keyToIndexMap = new HashMap<>();
    private Map<String, IndexDef> nameToIndexMap = new HashMap<>();

    public void addIndexDef(IndexDef indexDef) {
        //TODO: CHeck for duplicates
        //this.keyToIndexMap.put(indexDef.getIndexId(), indexDef);
        //this.nameToIndexMap.put(indexDef.getIndexName().getName(), indexDef);
    }

}
