package jToolkit4FixedPipeline.quaterion;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 26.02.13
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class Quaterion3f implements Q3f<Quaterion3f> {
    private float x;
    private float y;
    private float z;

    public Quaterion3f () {
        // do nothing
    }

    public Quaterion3f (final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Quaterion3f add(Quaterion3f quat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f add(Quaterion3f fquat, Quaterion3f squat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f sub(Quaterion3f quat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f sub(Quaterion3f fquat, Quaterion3f squat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f mul(Quaterion3f quat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f mul(Quaterion3f fquat, Quaterion3f squat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float magnitude() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f norm() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f makeNull() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f makeIdentity() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f scale(float scalar) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f divide(float scalar) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f random(float start, float end) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion3f limit(float value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quaterion3f that = (Quaterion3f) o;

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
        return result;
    }

    @Override
    public String toString() {
        return "Quaterion3f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}
