package jToolkit4FixedPipeline.vector;

/**
 * 
 * @author Astemir Eleev
 */
public class Vector2f implements V2f<Vector2f> {
    public float x;
    public float y;

    public Vector2f() {
        // do nothing
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2f add (Vector2f vector) {
        x += vector.getX();
        y += vector.getY();
        return this;
    }

    @Override
    public Vector2f add (Vector2f first, Vector2f second) {
        x = first.getX() + second.getX();
        y = first.getY() + second.getY();
        return this;
    }

    @Override
    public Vector2f substract (Vector2f vec) {
        x -= vec.getX();
        y -= vec.getY();
        return this;
    }

    @Override
    public Vector2f substract (Vector2f first, Vector2f second) {
        x = first.getX() - second.getX();
        y = first.getX() - second.getY();
        return this;
    }

    @Override
    public Vector2f scale (float scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    @Override
    public Vector2f scale (Vector2f vec, float scalar) {
        x = vec.getX() * scalar;
        y = vec.getY() * scalar;
        return this;
    }

    @Override
    public float determinant (final Vector2f that) {
        return (x * that.getY()) - (y * that.getY());
    }

    @Override
    public float distance (Vector2f point) {
        return (float) Math.sqrt(Math.pow(x - point.getX(), 2) + Math.pow(y - point.getY(), 2));
    }

    @Override
    public float dotProduct (Vector2f vec) {
        return (x * vec.getX()) + (y * vec.getY());
    }

    @Override
    public float magnitude () {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    @Override
    public Vector2f limit (float lim) {
        x %= lim;
        y %= lim;
        return this;
    }

    @Override
    public Vector2f normalize () {
        float norm = magnitude();
        x /= norm;
        y /= norm;
        return this;
    }

    @Override
    public Vector2f divide (float scalar) {
        if (scalar == 0) {
            System.err.println("Scalat can't be equal zero");
            return null;
        }
        x /= scalar;
        y /= scalar;

        return this;
    }

    @Override
    public Vector2f negate () {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public float[] getFloatArray () {
        return new float[] {x, y};
    }

    @Override
    public void setFloatArray (final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2f random (float start, float end) {
        x = (float)(start + Math.random() * end);
        y = (float)(start + Math.random() * end);
        return this;
    }

    @Override
    public Vector2f makeNull () {
        x = 0.0f;
        y = 0.0f;
        return this;
    }

    @Override
    public Vector2f makeIdentity () {
        x = 1.0f;
        y = 1.0f;
        return this;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals (Object anObject) {
        if (this == anObject) return true;
        if (anObject == null) return false;
        if (this.getClass() != anObject.getClass()) return false;
        Vector2f that = (Vector2f)anObject;

        if (this.getX() != that.getX()) return false;
        if (this.getY() != that.getY()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Float.floatToIntBits(this.getX());
        hash = 17 * hash + Float.floatToIntBits(this.getY());
        return hash;
    }

    @Override
    public String toString() {
        return "Vector2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
