package jToolkit4FixedPipeline.quaterion;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 26.02.13
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public interface Quaterion<Q> {
    public Q add(final Q quat);
    public Q add(final Q fquat, final Q squat);
    public Q sub(final Q quat);
    public Q sub(final Q fquat, final Q squat);
    public Q mul(final Q quat);
    public Q mul(final Q fquat, final Q squat);
    public float magnitude();
    public Q norm();
    public Q makeNull();
    public Q makeIdentity();
    public Q scale(final float scalar);
    public Q divide(final float scalar);
    public Q random(final float start, final float end);
    public Q limit(final float value);

}
