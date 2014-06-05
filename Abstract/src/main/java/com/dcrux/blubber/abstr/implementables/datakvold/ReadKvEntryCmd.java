package com.dcrux.blubber.abstr.implementables.datakvold;

import com.dcrux.blubber.iface.command.IMultiCommand;
import org.jetbrains.annotations.Nullable;

/**
 * Created by caelis on 18/05/14.
 */
public class ReadKvEntryCmd implements IDataKvCommand<ReadKvEntryCmdReply> {

    private final byte[] bucket;
    @Nullable
    private final byte[] key;

    private final IMultiCommand<IKvReadOperationCommand> operations;

    public ReadKvEntryCmd(byte[] bucket, byte[] key, IMultiCommand<IKvReadOperationCommand> operations) {
        this.bucket = bucket;
        this.key = key;
        this.operations = operations;
    }
}
