package com.dcrux.blubber.abstr.implementables.datakvold;

import com.dcrux.blubber.iface.command.IMultiCommand;
import com.dcrux.blubber.iface.command.MultiCommand;
import org.jetbrains.annotations.Nullable;

/**
 * Created by caelis on 18/05/14.
 */
public class CreateKvEntryCmd implements IDataKvCommand<CreateKvEntryCmdReply> {

    private final byte[] bucket;
    @Nullable
    private final byte[] key;

    private final IMultiCommand<IKvOperationCommand> operations;

    public CreateKvEntryCmd(byte[] bucket, byte[] key, IMultiCommand<IKvOperationCommand> operations) {
        this.bucket = bucket;
        this.key = key;
        this.operations = operations;
    }

    public CreateKvEntryCmd(byte[] bucket, byte[] key) {
        this(bucket, key, MultiCommand.none());
    }
}
