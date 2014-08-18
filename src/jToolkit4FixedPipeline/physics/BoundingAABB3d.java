package jToolkit4FixedPipeline.physics;

import jToolkit4FixedPipeline.vector.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Astmeir Eleev
 * Date: 24.02.13
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
public interface BoundingAABB3d<T> extends BoundingVolume <T>{
    public void setPosition(final Vector3f vec);
    public Vector3f getPosition();
    public void setHeight(final float height);
    public float getHeight();
    public void setWidth(final float width);
    public float getWidth();
    public void setDepth(final float depth);
    public float getDepth();

}
