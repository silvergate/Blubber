package com.dcrux.blubber.abstr.implementables.datakvold;

import org.jetbrains.annotations.Nullable;

/**
 * Created by caelis on 18/05/14.
 */
public class DeleteKvEntryCmd implements IDataKvCommand<CreateKvEntryCmdReply> {

    private final byte[] bucket;
    @Nullable
    private final byte[] key;


    public DeleteKvEntryCmd(byte[] bucket, byte[] key) {
        this.bucket = bucket;
        this.key = key;
    }

}
