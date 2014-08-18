package jToolkit4FixedPipeline.physics;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 24.02.13
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public interface BoundingVolume<T> {
    public boolean intersects(final T shape);
    public void drawSolidBV();
    public void drawWireBV();
}
