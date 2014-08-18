package jToolkit4FixedPipeline.color;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 11.02.13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class ColorRGBA {
    private float r;
    private float g;
    private float b;
    private float a;

    public ColorRGBA() {
        // do nothing
    }

    public ColorRGBA(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorRGBA colorRGBA = (ColorRGBA) o;

        if (Float.compare(colorRGBA.a, a) != 0) return false;
        if (Float.compare(colorRGBA.b, b) != 0) return false;
        if (Float.compare(colorRGBA.g, g) != 0) return false;
        if (Float.compare(colorRGBA.r, r) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (r != +0.0f ? Float.floatToIntBits(r) : 0);
        result = 31 * result + (g != +0.0f ? Float.floatToIntBits(g) : 0);
        result = 31 * result + (b != +0.0f ? Float.floatToIntBits(b) : 0);
        result = 31 * result + (a != +0.0f ? Float.floatToIntBits(a) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ColorRGBA{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }
}
