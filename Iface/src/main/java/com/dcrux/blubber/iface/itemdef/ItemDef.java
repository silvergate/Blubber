package com.dcrux.blubber.iface.itemdef;

import com.dcrux.blubber.iface.index.IndexesDef;

import java.io.Serializable;

/**
 * Created by caelis on 18/05/14.
 */
public class ItemDef implements Serializable {
    String description;
    FieldsDef fields;
    IndexesDef indexes;

    public String getDescription() {
        return description;
    }

    public FieldsDef getFields() {
        if (this.fields == null)
            this.fields = new FieldsDef();
        return fields;
    }

    public IndexesDef getIndexes() {
        if (this.indexes == null)
            this.indexes = new IndexesDef();
        return this.indexes;
    }
}
