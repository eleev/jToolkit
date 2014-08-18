package jToolkit4FixedPipeline.point;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 11.02.13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public class Point4f {
    private float x;
    private float y;
    private float z;
    private float w;

    public Point4f() {
        // do nothing
    }

    public Point4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float[] toFloatArray () {
        return new float[]{x, y, z, w};
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point4f point4f = (Point4f) o;

        if (Float.compare(point4f.w, w) != 0) return false;
        if (Float.compare(point4f.x, x) != 0) return false;
        if (Float.compare(point4f.y, y) != 0) return false;
        if (Float.compare(point4f.z, z) != 0) return false;

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
        return "Point4f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
