package jToolkit4FixedPipeline.point;

import jToolkit4FixedPipeline.quaterion.Quaterion;
import jToolkit4FixedPipeline.vector.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 26.02.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public interface Point {
    public float[] toArray();
    public Vector toVector();
    public Quaterion toQuaterion();
}
