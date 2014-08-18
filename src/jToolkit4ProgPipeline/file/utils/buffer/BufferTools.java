package jToolkit4ProgPipeline.file.utils.buffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * @author Astemir Yeleev
 */
public class BufferTools {

    public static float[] toArray(Vector2f vector2f) {
        return new float[]{vector2f.x, vector2f.y};
    }

    public static float[] toArray(Vector3f vector3f) {
        return new float[]{vector3f.x, vector3f.y, vector3f.z};
    }

    public static float[] toArray(Vector4f vector4f) {
        return new float[]{vector4f.x, vector4f.y, vector4f.z, vector4f.w};
    }

    public static float[] toArray(Matrix2f matrix2f) {
        return new float[]{ matrix2f.m00, matrix2f.m01,
                            matrix2f.m10, matrix2f.m11};
    }

    public static float[] toArray(Matrix3f matrix3f) {
        return new float[]{ matrix3f.m00, matrix3f.m01, matrix3f.m02,
                            matrix3f.m10, matrix3f.m11, matrix3f.m12,
                            matrix3f.m20, matrix3f.m21, matrix3f.m22};
    }

    public static float[] toArray(Matrix4f matrix4f) {
        return new float[]{ matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m13,
                            matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m13,
                            matrix4f.m20, matrix4f.m21, matrix4f.m22, matrix4f.m23,
                            matrix4f.m30, matrix4f.m31, matrix4f.m32, matrix4f.m33};
    }

    public static ByteBuffer toByteBuffer(byte... values) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(values.length);
        buffer.put(values);
        return buffer;
    }

    public static FloatBuffer toFloatBuffer(Matrix4f matrix4f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix4f.store(buffer);
        return buffer;
    }

    public static FloatBuffer toFloatBuffer(Matrix3f matrix3f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(12);
        matrix3f.store(buffer);
        return buffer;
    }

    public static FloatBuffer toFloatBuffer(Matrix2f matrix2f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        matrix2f.store(buffer);
        return buffer;
    }

    public static FloatBuffer toFloatBuffer(float array[]) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(buffer);
        return buffer;
    }

    public static ByteBuffer toFlippedByteBuffer(byte values[]) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer toFlippedFloatBuffer(Matrix4f matrix4f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix4f.store(buffer);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer toFlippedFloatBuffer(Matrix3f matrix3f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(12);
        matrix3f.store(buffer);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer toFlippedFloatBuffer(Matrix2f matrix2f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        matrix2f.store(buffer);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer toFlippedFloatBuffer(Vector3f vector3f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
        vector3f.store(buffer);
        buffer.flip();
        return buffer;
    }


    public static FloatBuffer toFlippedFloatBuffer(Vector4f vector4f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        vector4f.store(buffer);
        buffer.flip();
        return buffer;
    }


    public static FloatBuffer toFlippedFloatBuffer(Vector2f vector2f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(2);
        vector2f.store(buffer);
        buffer.flip();
        return buffer;
    }


    public static FloatBuffer toFlippedFloatBuffer(float values[]) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer toIntBuffer(int values[]) {
        IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
        buffer.put(values);
        return buffer;
    }

    public static IntBuffer toFlippedIntBuffer(int values[]) {
        IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

    public static String toString(FloatBuffer buffer, int size) {
        StringBuilder bufferString = new StringBuilder();
        for (int i = 0; i < size; i++) bufferString.append(" ").append(buffer.get(i));
        return bufferString.toString();
    }

    public static boolean bufferEquals(FloatBuffer bufferOne, FloatBuffer bufferTwo, int size) {
        for (int i = 0; i < size; i++) {
            if (bufferOne.get(i) != bufferTwo.get(i)) return false;
        }
        return true;
    }

    public static FloatBuffer reserveData(int amountOfElements) {
        return BufferUtils.createFloatBuffer(amountOfElements);
    }
}
