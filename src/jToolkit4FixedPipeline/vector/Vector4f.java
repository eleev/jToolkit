package jToolkit4FixedPipeline.vector;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 11.02.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public class Vector4f implements V4f<Vector4f> {
    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4f() {
        // do nothing
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public Vector4f add (Vector4f vec) {
        x += vec.getX();
        y += vec.getY();
        z += vec.getZ();
        w += vec.getW();
        return this;
    }

    @Override
    public Vector4f add (Vector4f fvec, Vector4f svec) {
        x = fvec.getX() + svec.getX();
        y = fvec.getY() + svec.getY();
        z = fvec.getZ() + svec.getZ();
        w = fvec.getW() + svec.getW();
        return this;
    }

    @Override
    public Vector4f substract (Vector4f vector) {
        x -= vector.getX();
        y -= vector.getY();
        z -= vector.getZ();
        w -= vector.getW();
        return this;
    }

    @Override
    public Vector4f substract (Vector4f fvec, Vector4f svec) {
        x = fvec.getX() - svec.getX();
        y = fvec.getY() - svec.getY();
        z = fvec.getZ() - svec.getZ();
        w = fvec.getW() - svec.getW();
         return this;
    }

    @Override
    public Vector4f random (float start, float end) {
        x = (float)(start + Math.random() * end);
        y = (float)(start + Math.random() * end);
        z = (float)(start + Math.random() * end);
        w = (float)(start + Math.random() * end);
        return this;
    }

    @Override
    public Vector4f divide (float scalar) {
        if (scalar == 0) {
            System.err.println("Scalar can't be equal zero");
            return null;
        }

        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    @Override
    public Vector4f negate () {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    @Override
    public Vector4f limit (float lim) {
        x %= lim;
        y %= lim;
        z %= lim;
        w %= lim;
        return this;
    }

    @Override
    public float determinant (final Vector4f that) {
        return (x * that.getY()) - (y * that.getX()) + (x * that.getZ()) - (z * that.getZ()) + (x * that.getW()) - (w * that.getW()) +
                (y * that.getZ()) - (z * that.getY()) + (y * that.getW()) - (w * that.getY()) + (z * that.getW()) - (w * that.getZ());
    }

    @Override
    public float distance (Vector4f point) {
        return (float) Math.sqrt(Math.pow(x - point.getX(), 2) + Math.pow(y - point.getY(), 2) + Math.pow(z - point.getZ(), 2) + Math.pow(w - point.getW(), 2));
    }

    @Override
    public float dotProduct (Vector4f vector) {
        return (x * vector.getX()) + (y * vector.getY()) + (z * vector.getZ() + (w * vector.getW()));
    }

    @Override
    public Vector4f crossProduct (Vector4f fvec, Vector4f svec, Vector4f tvec) {
        // intv - intermediate value
        float intva, intvb, intvc, intvd, intve, intvf;

        intva = (fvec.getX() * svec.getY()) - (fvec.getY() * svec.getX());
        intvb = (fvec.getX() * svec.getZ()) - (fvec.getZ() * svec.getX());
        intvc = (fvec.getX() * svec.getW()) - (fvec.getW() * svec.getX());
        intvd = (fvec.getY() * svec.getZ()) - (fvec.getZ() * svec.getY());
        intve = (fvec.getY() * svec.getW()) - (fvec.getW() * svec.getY());
        intvf = (fvec.getZ() * svec.getW()) - (fvec.getW() * svec.getZ());

        x = (tvec.getY() * intvf) - (tvec.getZ() * intve) + (tvec.getW() * intvd);
        y = -(tvec.getX() * intvf) + (tvec.getZ() * intvc) - (tvec.getW() * intvb);
        z = (tvec.getX() * intve) - (tvec.getW() * intvc) + (tvec.getW() * intva);
        w = -(tvec.getX() * intvd) + (tvec.getY() * intvb) - (tvec.getZ() * intva);

        return this;
    }

    @Override
    public Vector4f scale (float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    @Override
    public Vector4f scale (final Vector4f vec, final float scalar) {
        x = vec.getX() * x;
        y = vec.getY() * y;
        z = vec.getZ() * z;
        w = vec.getW() * w;
        return this;
    }

    @Override
    public float magnitude () {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z) + (w * w));
    }

    @Override
    public Vector4f normalize () {
        float normal = magnitude();
        x /= normal;
        y /= normal;
        z /= normal;
        w /= normal;
        return this;
    }

    @Override
    public float[] getFloatArray () {
        return new float[]{x, y, z, w};
    }

    @Override
    public void setFloatArray (final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public Vector4f makeNull () {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
        return this;
    }

    @Override
    public Vector4f makeIdentity () {
        x = 1.0f;
        y = 1.0f;
        z = 1.0f;
        w = 1.0f;
        return this;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public float getW() {
        return w;
    }

    @Override
    public void setW(float w) {
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector4f vector4f = (Vector4f) o;

        if (Float.compare(vector4f.w, w) != 0) return false;
        if (Float.compare(vector4f.x, x) != 0) return false;
        if (Float.compare(vector4f.y, y) != 0) return false;
        if (Float.compare(vector4f.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vector4f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
