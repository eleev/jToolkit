package jToolkit4FixedPipeline.vector;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 24.02.13
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public interface Vector <Vector>{
    public float[] getFloatArray();
    public Vector add(final Vector vec);
    public Vector add(final Vector fvec, final Vector svec);
    public Vector substract(final Vector vec);
    public Vector substract(final Vector fvec, final Vector svecs);
    public Vector scale(final float scalar);
    public Vector scale(final Vector vec, final float scalar);
    public Vector random(final float start, final float end);
    public Vector limit(final float val);
    public Vector normalize();
    public Vector makeIdentity();
    public Vector makeNull();
    public Vector divide(final float scalar);
    public Vector negate();
    public float determinant(final Vector vec);
    public float distance(final Vector vec);
    public float dotProduct(final Vector vec);
    public float magnitude();
}
