package jToolkit4FixedPipeline.point;

import jToolkit4FixedPipeline.quaterion.Quaterion;
import jToolkit4FixedPipeline.quaterion.Quaterion2f;
import jToolkit4FixedPipeline.vector.Vector;
import jToolkit4FixedPipeline.vector.Vector2f;

/**
 *
 * @author Astemir Eleev
 */
public class Point2f implements P2f {
    private float x;
    private float y;

    public Point2f() {
        // do nothing
    }

    public Point2f(float x, float y) {
        this.x = x;
        this.y = y;
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
    public float lerp(P2f p1, P2f p2, float x) {
        float a = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        float b = p1.getY() - a * p1.getX();
        return a * x + b;
    }

    @Override
    public P2f lerp(P2f p1, P2f p2) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float[] toArray() {
        return new float[] {x, y};
    }

    @Override
    public Vector toVector() {
        return new Vector2f(x, y);
    }

    @Override
    public Quaterion toQuaterion() {
        return new Quaterion2f(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2f point2f = (Point2f) o;

        if (Float.compare(point2f.x, x) != 0) return false;
        if (Float.compare(point2f.y, y) != 0) return false;

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
        return "Point2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}


