package jToolkit4FixedPipeline.vector;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 24.02.13
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public interface V4f<V> extends Vector<V> {
    public void setX(final float x);
    public float getX();
    public void setY(final float y);
    public float getY();
    public void setZ(final float z);
    public float getZ();
    public void setW(final float w);
    public float getW();
    public void setFloatArray(final float x, final float y, final float z, final float w);
    public V crossProduct(final V fvec, final V svec, final V tvec);
}
