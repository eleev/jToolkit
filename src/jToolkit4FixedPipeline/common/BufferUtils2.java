package jToolkit4FixedPipeline.common;

import org.lwjgl.BufferUtils;

import java.nio.*;

/**
 * A collection of convenience methods that should be in BufferUtils but aren't
 * @author Astemir Eleev
 */
public class BufferUtils2 {
    public static ByteBuffer toBuffer(byte[] src) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(src.length);
        buffer.put(src);
        buffer.rewind();
        return buffer;
    }

    public static CharBuffer toBuffer(char[] src) {
        CharBuffer buffer = BufferUtils.createCharBuffer(src.length);
        buffer.put(src);
        buffer.rewind();
        return buffer;
    }

    public static DoubleBuffer toBuffer(double[] src) {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(src.length);
        buffer.put(src);
        buffer.rewind();
        return buffer;
    }

    public static FloatBuffer toBuffer(float[] src) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(src.length);
        buffer.put(src);
        buffer.rewind();
        return buffer;
    }

    public static IntBuffer toBuffer(int[] src) {
        IntBuffer buffer = BufferUtils.createIntBuffer(src.length);
        buffer.put(src);
        buffer.rewind();
        return buffer;
    }

    public static LongBuffer toBuffer(long[] src) {
        LongBuffer buffer = BufferUtils.createLongBuffer(src.length);
        buffer.put(src);
        buffer.rewind();
        return buffer;
    }

    public static ShortBuffer toBuffer(short[] src) {
        ShortBuffer buffer = BufferUtils.createShortBuffer(src.length);
        buffer.put(src);
        buffer.rewind();
        return buffer;
    }
}
