package jToolkit4FixedPipeline.physics;

import jToolkit4FixedPipeline.vector.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: Virus
 * Date: 24.02.13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public interface BoundingAABB2d<T> extends BoundingVolume<T> {
    public void setPosition(final Vector2f vec);
    public Vector2f getPosition();
    public void setHeight(final float height);
    public float getHeight();
    public void setWidth(final float width);
    public float getWidth();
}
