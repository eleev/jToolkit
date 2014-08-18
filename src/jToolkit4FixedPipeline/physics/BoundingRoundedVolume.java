package jToolkit4FixedPipeline.physics;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 24.02.13
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public interface BoundingRoundedVolume<T, V> extends BoundingVolume <T>{
    public void setPosition(final V vec);
    public V getPosition();
    public void setRadius(final float radius);
    public float getRadius();
}
