package jToolkit4FixedPipeline.point;

/**
 *
 * @author Astemir Yeleev
 */
public class Point3f {
    private float x;
    private float z;
    private float y;

    public Point3f() {
        // do nothing
    }

    public Point3f(float x, float z, float y) {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public float distance (Point3f point) {
        return (float) Math.sqrt(Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2) + Math.pow(point.getZ() - z, 2));
    }

    public float distance (Point3f fpoint, Point3f spoint) {
        return (float) Math.sqrt(Math.pow(spoint.getX() - fpoint.getX(), 2) + Math.pow(spoint.getY() - fpoint.getY(), 2) + Math.pow(spoint.getZ() - fpoint.getZ(), 2));
    }

    public float[] toFloatArray () {
        return new float[]{x, y, z};
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3f point3f = (Point3f) o;

        if (Float.compare(point3f.x, x) != 0) return false;
        if (Float.compare(point3f.y, y) != 0) return false;
        if (Float.compare(point3f.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Point3f{" +
                "x=" + x +
                ", z=" + z +
                ", y=" + y +
                '}';
    }
}
