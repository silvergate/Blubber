package com.dcrux.blubber.abstr.utils;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by caelis on 25/05/14.
 */
public class BinaryComparator implements Comparator<byte[]> {

    private BinaryComparator() {
    }

    public static final BinaryComparator SINGLETON = new BinaryComparator();

    @Override
    public int compare(byte[] o1, byte[] o2) {
        boolean equal = Arrays.equals(o1, o2);
        if (equal)
            return 0;

        int lengthDif = o1.length - o2.length;
        if (lengthDif != 0)
            return lengthDif;

        int minLen = Math.min(o1.length, o2.length);
        for (int i = 0; i < minLen; i++) {
            byte v1 = o1[i];
            byte v2 = o2[i];
            int difference = v1 - v2;
            if (difference != 0)
                return difference;
        }

        /* End with last byte */
        if (o1.length > o2.length)
            return o1[o2.length - 1];
        if (o2.length > o1.length)
            return o2[o1.length - 1];
        throw new IllegalStateException("Should never be reached");
    }
}
