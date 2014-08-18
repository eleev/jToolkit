package jToolkit4FixedPipeline.color;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 11.02.13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class ColorRGB {
    private float r;
    private float g;
    private float b;

    public ColorRGB() {
        // do nothing
    }

    public ColorRGB(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorRGB colorRGB = (ColorRGB) o;

        if (Float.compare(colorRGB.b, b) != 0) return false;
        if (Float.compare(colorRGB.g, g) != 0) return false;
        if (Float.compare(colorRGB.r, r) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (r != +0.0f ? Float.floatToIntBits(r) : 0);
        result = 31 * result + (g != +0.0f ? Float.floatToIntBits(g) : 0);
        result = 31 * result + (b != +0.0f ? Float.floatToIntBits(b) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ColorRGB{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
