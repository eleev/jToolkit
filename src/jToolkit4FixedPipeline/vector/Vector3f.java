package jToolkit4FixedPipeline.vector;

/**
 *
 * @author Astemir Eleev
 */
public class Vector3f implements V3f<Vector3f> {
    public float x;
    public float y;
    public float z;

    public Vector3f() {
        // do nothing
    }

    public Vector3f (final Vector3f vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Vector3f add (Vector3f vector) {
        x += vector.getX();
        y += vector.getY();
        z += vector.getZ();
        return this;
    }

    @Override
    public Vector3f add (Vector3f first, Vector3f second) {
        x = first.getX() + second.getX();
        y = first.getY() + second.getY();
        z = first.getZ() + second.getZ();
        
        return this; 
    }

    @Override
    public Vector3f random (float start, float end) {
        x = (float)(start + Math.random() * end);
        y = (float)(start + Math.random() * end);
        z = (float)(start + Math.random() * end);
        
        return this;
    }

    @Override
    public Vector3f substract (Vector3f vector) {
        x -= vector.getX();
        y -= vector.getY();
        z -= vector.getZ();
        return this;
    }

    @Override
    public Vector3f substract (Vector3f vectorOne, Vector3f vectorTwo) {
        x = vectorOne.getX() - vectorTwo.getX();
        y = vectorOne.getY() - vectorTwo.getY();
        z = vectorOne.getZ() - vectorTwo.getZ();
        return this;
    }

    @Override
    public Vector3f divide (float scalar) {
        if (scalar == 0) {
            System.err.println("Scalar can't be equal zero");
            return null;
        }
        x /= scalar;
        y /= scalar;
        z /= scalar;
        return this;
    }

    @Override
    public Vector3f negate () {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    @Override
    public Vector3f limit (float lim) {
        x %= lim;
        y %= lim;
        z %= lim;
        return this;
    }

    @Override
    public float determinant (final Vector3f that) {
        return (y * that.getZ() - z * that.getY()) + (x * that.getZ() - z * that.getX()) + (x * that.getY() - y * that.getX());
    }

    @Override
    public float distance (Vector3f point) {
        return (float) Math.sqrt(Math.pow(x - point.getX(), 2) + Math.pow(y - point.getY(), 2) + Math.pow(z - point.getZ(), 2));
    }

    @Override
    public float dotProduct (Vector3f vector) {
        return (x * vector.getX()) + (y * vector.getY()) + (z * vector.getZ());
    }

    @Override
    public Vector3f crossProduct (Vector3f vec) {
        Vector3f temp = new Vector3f();
        temp.setX((y * vec.getZ()) - (z * vec.getY()));
        temp.setY((z * vec.getX()) - (x * vec.getZ()));
        temp.setZ((x * vec.getY()) - (y * vec.getX()));
        x = temp.getX();
        y = temp.getY();
        z = temp.getZ();
        return this;
    }

    @Override
    public Vector3f crossProduct (Vector3f fvec, Vector3f svec) {
        x = (fvec.getY() * svec.getZ()) - (fvec.getZ() * svec.getY());
        y = (fvec.getZ() * svec.getX()) - (fvec.getX() * svec.getZ());
        z = (fvec.getX() * svec.getY()) - (fvec.getY() * svec.getX());
        return this;
    }

    @Override
    public Vector3f crossProduct (Vector3f pointOne, Vector3f pointTwo, Vector3f pointThree) {
        Vector3f firstVec = substract(pointTwo, pointOne);
        Vector3f secondVec = substract(pointThree, pointOne);
        Vector3f out = crossProduct(firstVec, secondVec);
        
        return out.normalize();
    }

    @Override
    public Vector3f scale (float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    @Override
    public Vector3f scale (Vector3f vec, float scalar) {
        x = vec.getX() * scalar;
        y = vec.getY() * scalar;
        z = vec.getZ() * scalar;
        return this;
    }

    @Override
    public float magnitude() {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    @Override
    public Vector3f normalize () {
        float normal = magnitude();
        
        x /= normal;
        y /= normal;
        z /= normal;
        return this;
    }

    @Override
    public float[] getFloatArray () {
        return new float[]{x, y, z};
    }

    @Override
    public void setFloatArray (final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public float getZ() {
        return z;
    }

    @Override
    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public Vector3f makeNull () {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        return this;
    }

    @Override
    public Vector3f makeIdentity () {
        x = 1.0f;
        y = 1.0f;
        z = 1.0f;
        return this;
    }
    
    @Override
    public boolean equals (Object anObject) {
        if (this == anObject) return true;
        if (anObject == null) return false;
        if (this.getClass() != anObject.getClass()) return false;
        Vector3f that = (Vector3f)anObject;
        
        if (this.getX() != that.getX()) return false; 
        if (this.getY() != that.getY()) return false;
        if (this.getZ() != that.getZ()) return false; 
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Float.floatToIntBits(this.getX());
        hash = 17 * hash + Float.floatToIntBits(this.getY());
        hash = 17 * hash + Float.floatToIntBits(this.getZ());
        return hash;
    }

    @Override
    public String toString() {
        return "Vector3f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
