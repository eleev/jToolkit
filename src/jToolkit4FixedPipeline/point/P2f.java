package jToolkit4FixedPipeline.point;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 26.02.13
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public interface P2f extends Point {
    public float getX();
    public void setX(final float x);
    public float getY();
    public void setY(final float y);
    public float lerp(final P2f p1, final P2f p2, final float x);
    public P2f lerp(final P2f p1, final P2f p2);
}
