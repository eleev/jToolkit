package jToolkit4FixedPipeline.quaterion;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 26.02.13
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class Quaterion2f implements Q2f<Quaterion2f>{
    private float x;
    private float y;

    public Quaterion2f () {
        // do nothing
    }

    public Quaterion2f (final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Quaterion2f add(Quaterion2f quat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f add(Quaterion2f fquat, Quaterion2f squat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f sub(Quaterion2f quat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f sub(Quaterion2f fquat, Quaterion2f squat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f mul(Quaterion2f quat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f mul(Quaterion2f fquat, Quaterion2f squat) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float magnitude() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f norm() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f makeNull() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f makeIdentity() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f scale(float scalar) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f divide(float scalar) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f random(float start, float end) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Quaterion2f limit(float value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quaterion2f that = (Quaterion2f) o;

        if (Float.compare(that.x, x) != 0) return false;
        if (Float.compare(that.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quaterion2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


}
