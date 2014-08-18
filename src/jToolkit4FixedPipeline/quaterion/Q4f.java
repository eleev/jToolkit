package jToolkit4FixedPipeline.quaterion;

import jToolkit4FixedPipeline.matrix.Matrix4f;
import jToolkit4FixedPipeline.vector.Vector4f;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 26.02.13
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public interface Q4f<Q> extends Quaterion<Q> {
    public Matrix4f toMatrix();
    public Vector4f toVector();
    public float[] toArray();
    public float getX();
    public void setX(final float x);
    public float getY();
    public void setY(final float y);
    public float getZ();
    public void setZ(final float z);
    public float getW();
    public void setW(final float w);
}
