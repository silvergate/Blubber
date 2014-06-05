package com.dcrux.blubber.iface.index;

import java.util.List;

/**
 * Created by caelis on 01/06/14.
 */
public class IndexDef {
    IIndexTarget indexTarget;

    MasterIndexFieldSource masterSource;
    //Additional fields -> note: Only index 0 is taken
    List<SecondaryIndexFieldSource> sourceFields;
}
