package com.dcrux.blubber.abstr.command;

import com.dcrux.blubber.iface.command.IReplyList;
import com.dcrux.blubber.iface.command.ISingleReply;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by caelis on 17/05/14.
 */
public class ReplyList<TReply extends Serializable> implements IReplyList<TReply> {

    private static final IReplyList EMPTY_REPLY_LIST = new IReplyList() {
        @Override
        public boolean isAllSuccess() {
            return true;
        }

        @Override
        public List<ISingleReply> getReplies() {
            return Collections.emptyList();
        }

        @Override
        public Exception getException() {
            throw new IllegalStateException("Cannot call this method on reply lists that have allSuccess = true.");
        }

        @Override
        public ISingleReply getSingle() {
            throw new IllegalStateException("(this.replies.size()!=1)");
        }

        @Override
        public Serializable getSingleSuccess() {
            throw new IllegalStateException("(this.replies.size()!=1)");
        }
    };
    private static SingleReply PENDING = SingleReply.failure(new IllegalStateException("Operation still pending"));
    private List<ISingleReply<TReply>> replies = new ArrayList<>();
    private List<ISingleReply<TReply>> unmodifiableReplies = Collections.unmodifiableList(this.replies);
    private boolean allSuccess;

    private ReplyList() {
    }

    public static <TReplyLocal extends Serializable> IReplyList<TReplyLocal> empty() {
        return EMPTY_REPLY_LIST;
    }

    public static <TReplyLocal extends Serializable> ISequentialMode<TReplyLocal> sequential() {
        final ReplyList<TReplyLocal> multiReply = new ReplyList<TReplyLocal>();
        multiReply.allSuccess = true;
        return new ISequentialMode<TReplyLocal>() {
            @Override
            public void add(ISingleReply<TReplyLocal> singleReply) {
                if (!singleReply.isSuccess())
                    multiReply.allSuccess = false;
                multiReply.replies.add(singleReply);
            }

            @Override
            public IReplyList<TReplyLocal> getMultiReply() {
                return multiReply;
            }
        };
    }

    public static <TReplyLocal extends Serializable> IParallelMode<TReplyLocal> parallel(final int numberOfReplies) {
        final ReplyList<TReplyLocal> multiReply = new ReplyList<TReplyLocal>();
        multiReply.allSuccess = true;
        for (int i = 0; i < numberOfReplies; i++) {
            multiReply.replies.add(PENDING);
        }

        final List<ISingleReply<TReplyLocal>> syncedReplies = Collections.synchronizedList(multiReply.replies);
        return new IParallelMode<TReplyLocal>() {
            int numberProcessed;

            @Override
            public void set(int index, ISingleReply<TReplyLocal> singleReply) {
                numberProcessed++;
                syncedReplies.set(index, singleReply);
            }

            @Override
            public IReplyList<TReplyLocal> getMultiReply() {
                return multiReply;
            }

            @Override
            public boolean isDone() {
                return numberProcessed == numberOfReplies;
            }
        };
    }

    @Override
    public boolean isAllSuccess() {
        return this.allSuccess;
    }

    @Override
    public List<ISingleReply<TReply>> getReplies() {
        return this.unmodifiableReplies;
    }

    @Override
    public Exception getException() {
        if (isAllSuccess()) {
            throw new IllegalStateException("Cannot call this method on reply lists that have allSuccess = true.");
        }
        //TODO: Not only return one exception. If more than one exception, return an exception that combines all exceptions.
        for (int i = this.replies.size() - 1; i >= 0; i++) {
            ISingleReply<TReply> reply = this.replies.get(i);
            if (!reply.isSuccess())
                return reply.getFailure();
        }
        throw new IllegalStateException("This code should be unreachable");
    }

    @Override
    public ISingleReply<TReply> getSingle() {
        if (this.replies.size() != 1)
            throw new IllegalStateException("(this.replies.size()!=1)");
        return this.replies.get(0);
    }

    @Override
    public TReply getSingleSuccess() {
        if (!isAllSuccess())
            throw new IllegalStateException("isAllSuccess is not true");
        if (this.replies.size() != 1)
            throw new IllegalStateException("(this.replies.size()!=1)");
        return this.replies.get(0).getSuccess();
    }

    public interface ISequentialMode<TReply extends Serializable> {
        void add(ISingleReply<TReply> singleReply);

        IReplyList<TReply> getMultiReply();
    }

    public interface IParallelMode<TReply extends Serializable> {
        void set(int index, ISingleReply<TReply> singleReply);

        IReplyList<TReply> getMultiReply();

        boolean isDone();
    }
}
