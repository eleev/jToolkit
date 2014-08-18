package jToolkit4FixedPipeline.quaterion;

import jToolkit4FixedPipeline.matrix.Matrix4f;
import jToolkit4FixedPipeline.vector.Vector4f;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 26.02.13
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
public class Quaterion4f implements Q4f<Quaterion4f> {
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaterion4f (){
        // do nothing
    }

    public Quaterion4f (final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public Quaterion4f add(Quaterion4f quat) {
        x += quat.getX();
        y += quat.getY();
        z += quat.getZ();
        w += quat.getW();
        return this;
    }

    @Override
    public Quaterion4f add(Quaterion4f fquat, Quaterion4f squat) {
        x = fquat.getX() + squat.getX();
        y = fquat.getY() + squat.getY();
        z = fquat.getZ() + squat.getZ();
        w = fquat.getW() + squat.getW();
        return this;
    }

    @Override
    public Quaterion4f sub(Quaterion4f quat) {
        x -= quat.getX();
        y -= quat.getY();
        z -= quat.getZ();
        w -= quat.getW();
        return this;
    }

    @Override
    public Quaterion4f sub(Quaterion4f fquat, Quaterion4f squat) {
        x = fquat.getX() - squat.getX();
        y = fquat.getY() - squat.getY();
        z = fquat.getZ() - squat.getZ();
        w = fquat.getW() - squat.getW();
        return this;
    }

    @Override
    public Quaterion4f mul(Quaterion4f quat) {
        x = ((x * quat.getW()) + (w * quat.getX())) + ((y * quat.getZ()) - (z * quat.getY()));
        y = ((y * quat.getW()) + (w * quat.getY())) + ((x * quat.getZ()) - (z * quat.getX()));
        z = ((z * quat.getW()) + (w * quat.getZ())) + ((x * quat.getZ()) - (z * quat.getX()));
        w = ((w * quat.getW()) - (x * quat.getX())) - ((y * quat.getY()) - (z * quat.getZ()));
        return this;
    }

    @Override
    public Quaterion4f mul(Quaterion4f fquat, Quaterion4f squat) {
        x = ((fquat.getX() * squat.getW()) + (fquat.getW() * squat.getX())) + ((fquat.getY() * squat.getZ()) - (fquat.getZ() * squat.getY()));
        y = ((fquat.getY() * squat.getW()) + (fquat.getW() * squat.getY())) + ((fquat.getX() * squat.getZ()) - (fquat.getZ() * squat.getX()));
        z = ((fquat.getZ() * squat.getW()) + (fquat.getW() * squat.getZ())) + ((fquat.getX() * squat.getZ()) - (fquat.getZ() * squat.getX()));
        w = ((fquat.getW() * squat.getW()) - (fquat.getX() * squat.getX())) - ((fquat.getY() * squat.getY()) - (fquat.getZ() * squat.getZ()));
        return this;
    }

    @Override
    public float magnitude() {
        return (float) (Math.sqrt((x * x) + (y * y) + (z * z) + (w * w)));
    }

    @Override
    public Quaterion4f norm() {
        float mag = magnitude();
        x /= mag;
        y /= mag;
        z /= mag;
        w /= mag;
        return this;
    }

    @Override
    public Quaterion4f makeNull() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
        return this;
    }

    @Override
    public Quaterion4f makeIdentity() {
        x = 1.0f;
        y = 1.0f;
        z = 1.0f;
        w = 1.0f;
        return this;
    }

    @Override
    public Quaterion4f scale(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    @Override
    public Quaterion4f divide(float scalar) {
        if (scalar == 0) {
            System.err.println("Divide by zero is not valid operation");
            return null;
        }
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    @Override
    public Quaterion4f random(float start, float end) {
        x = (float)(start + Math.random() * end);
        y = (float)(start + Math.random() * end);
        z = (float)(start + Math.random() * end);
        w = (float)(start + Math.random() * end);
        return this;
    }

    @Override
    public Quaterion4f limit(float value) {
        x %= value;
        y %= value;
        z %= value;
        w %= value;
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
    @Deprecated
    public Matrix4f toMatrix() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vector4f toVector() {
        return new Vector4f(x, y, z, w);
    }

    @Override
    public float[] toArray() {
        return new float[] {x, y ,z, w};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quaterion4f that = (Quaterion4f) o;

        if (Float.compare(that.w, w) != 0) return false;
        if (Float.compare(that.x, x) != 0) return false;
        if (Float.compare(that.y, y) != 0) return false;
        if (Float.compare(that.z, z) != 0) return false;

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
        return "Quaterion4f{" +
                "w=" + w +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
