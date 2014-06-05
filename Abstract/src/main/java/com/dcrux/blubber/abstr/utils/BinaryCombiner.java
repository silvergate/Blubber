package com.dcrux.blubber.abstr.utils;

/**
 * Created by caelis on 26/05/14.
 */
@Deprecated
public class BinaryCombiner {
    public static byte[] combine(byte[]... components) {
        if (components.length == 1)
            return components[0];
        else {
            if (components.length > Byte.MAX_VALUE)
                throw new IllegalArgumentException("Too many components");

            /* Calculate length */
            int requiredLen = 0;
            requiredLen++;
            requiredLen += components.length;
            for (int i = 0; i < components.length; i++) {
                requiredLen += components[i].length;
            }

            byte[] header = new byte[requiredLen];

            /* Combine */
            header[0] = (byte) components.length;
            int pos = 1;
            for (int i = 0; i < components.length; i++) {
                byte[] data = components[i];
                header[pos] = (byte) (data.length - Byte.MIN_VALUE);
                pos++;
                System.arraycopy(data, 0, header, pos, data.length);
                pos += data.length;
            }

            return header;
        }
    }

}
