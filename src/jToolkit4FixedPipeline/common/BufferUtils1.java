package jToolkit4FixedPipeline.common;

import java.nio.*;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 14.04.13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class BufferUtils1 {
    public static byte[] toArray (final ByteBuffer buffer) {
        byte[] array = new byte[buffer.limit()];
        buffer.get(array);
        return array;
    }

    public static char[] toArray (final CharBuffer buffer) {
        char[] array = new char[buffer.limit()];
        buffer.get(array);
        return array;
    }

    public static double[] toArray (final DoubleBuffer buffer) {
        double[] array = new double[buffer.limit()];
        buffer.get(array);
        return array;
    }

    public static float[] toArray (final FloatBuffer buffer) {
        float[] array = new float[buffer.limit()];
        buffer.get(array);
        return array;
    }

    public static int[] toArray (final IntBuffer buffer) {
        int[] array = new int[buffer.limit()];
        buffer.get(array);
        return array;
    }

    public static long[] toArray (final LongBuffer buffer) {
        long[] array = new long[buffer.limit()];
        buffer.get(array);
        return array;
    }

    public static short[] toArray (final ShortBuffer buffer) {
        short[] array = new short[buffer.limit()];
        buffer.get(array);
        return array;
    }
}
