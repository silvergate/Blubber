package com.dcrux.blubber.riak;

import com.dcrux.blubber.common.implementables.ICallable;

import java.util.Random;

/**
 * Created by caelis on 31/05/14.
 */
public class WorkExamples {
    public static ICallable<Void> longComputation() {
        return executor -> {
            Thread.sleep(200);
            return null;
        };
    }

    public static ICallable<Void> combinedComputation(int num) {
        return executor -> {
            if (num > 10) {
                return null;
            } else {
                Random random = new Random();
                final boolean first = random.nextBoolean();
                if (first) {
                    executor.call(combinedComputation(num + 1), longComputation(), longComputation());
                } else {
                    executor.call(longComputation(), combinedComputation(num + 1), longComputation());
                }
                return null;
            }
        };
    }
}
