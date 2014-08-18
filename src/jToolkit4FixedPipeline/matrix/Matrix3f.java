package jToolkit4FixedPipeline.matrix;

import jToolkit4FixedPipeline.vector.Vector3f;

import java.util.Arrays;

/**
 * 16.08.2012 17:17
 * @author Astemir Eleev
 */
public class Matrix3f {
    private float matr[];
    private final int MATRIX_LENGHT = 9;
    
    public Matrix3f() {
        matr = new float[MATRIX_LENGHT];
    }
    
    /**
     * Transpose current matrix
     * @return transpose matrix 
     */
    public Matrix3f transpose () {
        float tmp = 0.0f;
        
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 3; j++) {
                tmp = getElem(i, j);
                setElem(i, j, getElem(j, i));
                setElem(j, i, tmp);
            }
        }
        return this;
    }
    
    /**
     * Transpose some matrix. Not current
     * @param matrix which I'd like to transpose
     * @return matrix which was transposed
     */
    public Matrix3f transpose (final Matrix3f matrix) {
        float tmp = 0.0f;
        
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 3; j++) {
                tmp = matrix.getElem(i, j);
                matrix.setElem(i, j, matrix.getElem(j, i));
                matrix.setElem(j, i, tmp);
                setElem(i, j, matrix.getElem(i, j));
            }
        }
        return this;
    }
    
    /**
     * Summ two matrix
     * @param matrix 
     * @return summ of two matrix
     */
    public Matrix3f summ (final Matrix3f matrix) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {    
                this.setElem(i, j, this.getElem(i, j) + matrix.getElem(i, j));
            }
        }
        return this;
    }
    
    public Matrix3f summ (final Matrix3f fmatr, final Matrix3f smatr) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                setElem(i, j, fmatr.getElem(i, j) + smatr.getElem(i, j));
            }
        }
        return this;
    }
    
    public Matrix3f scalar (float scalar) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {    
                this.setElem(i, j, this.getElem(i, j) * scalar);
            }
        }
        return this;
    }
    
    public Matrix3f scalar (float scalar, Matrix3f out) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                setElem(i, j, out.getElem(i, j) * scalar);
            }
        }
        return this;
    }
    
    public Matrix3f multiply (Matrix3f m) {
        // a x b and b x c = a x c
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    this.setElem(i, j, this.getElem(i, j) + this.getElem(i, k) * m.getElem(k, j));
                }   
            }
        }
        return this;
    }
    
    public Matrix3f multiply (Matrix3f fmatr, Matrix3f smatr) {
        // a x b and b x c = a x c
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    setElem(i, j, fmatr.getElem(i, j) + fmatr.getElem(i, k) * smatr.getElem(k, j));
                }
            }
        }
        return this;
    }
    
    public float determinant() {
        return (getElem(0, 0) * (getElem(1, 1) * getElem(2, 2) - getElem(2, 1) * getElem(1, 2))
                + getElem(0, 1) * (getElem(2, 0) * getElem(1, 2) - getElem(1, 0) * getElem(2, 2))
                + getElem(0, 2) * (getElem(1, 0) * getElem(2, 1) - getElem(2, 0) * getElem(1, 1)));
    }
    
    public Matrix3f makeIdentity() {
        for (int i = 0; i < 3; i++) {
            this.setElem(i, i, 1);
        }
        return this;
    }
    
    public Matrix3f makeNull () {
        this.setElem(0, 0, 0);
        this.setElem(0, 1, 0);
        this.setElem(0, 2, 0);
        this.setElem(1, 0, 0);
        this.setElem(1, 1, 0);
        this.setElem(1, 2, 0);
        this.setElem(2, 0, 0);
        this.setElem(2, 1, 0);
        this.setElem(2, 2, 0);
        return this;
    }
    
    public float getElem (int i, int j) {
        return matr[3 * i + j];
    }
    
    public void setElem (int i, int j, float value) {
        matr[3 * i + j] = value;
    }
    
    public float[][] getMatrix () {
        float [][] m = new float[3][3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m[i][j] = getElem(i, j);
            }
        }
        return m;
    }
    
    public void setColumn (int ind, Vector3f vector) {
        setElem(0, ind, vector.getX());
        setElem(1, ind, vector.getY());
        setElem(2, ind, vector.getZ());
    }
    
    public void setRow (int ind, Vector3f vector) {
        setElem(ind, 0, vector.getX());
        setElem(ind, 1, vector.getY());
        setElem(ind, 2, vector.getZ());
    }
    
    public Vector3f getColumn (int ind) {
        return new Vector3f(getElem(0, ind), getElem(1, ind), getElem(2, ind));
    }
           
    public Vector3f getRow (int ind) {
        return new Vector3f(getElem(ind, 0), getElem(ind, 1), getElem(ind, 2));
    }
    
    public Matrix3f rotate (float angle, Vector3f rotate) {
        float vecLength, sinSave, cosSave, oneMinusCos;
        float xx, yy, zz, xy, yz, zx, xs, ys, zs;
        Vector3f vec = new Vector3f();
        
        if (rotate.getX() == 0.0f && rotate.getY() == 0.0f && rotate.getZ() == 0.0f) {
            return null;
        }

        vecLength = (float) Math.sqrt(rotate.getX() * rotate.getX() + rotate.getY() * rotate.getY() + rotate.getZ() * rotate.getZ());

        vec.setX(rotate.getX() / vecLength);
        vec.setY(rotate.getY() / vecLength);
        vec.setZ(rotate.getZ() / vecLength);

        sinSave = (float) Math.sin(angle);
        cosSave = (float) Math.cos(angle);
        oneMinusCos = 1.0f - cosSave;

        xx = vec.getX() * vec.getX();
        yy = vec.getY() * vec.getY();
        zz = vec.getZ() * vec.getZ();
        xy = vec.getX() * vec.getY();
        yz = vec.getY() * vec.getZ();
        zx = vec.getZ() * vec.getX();
        xs = vec.getX() * sinSave;
        ys = vec.getY() * sinSave;
        zs = vec.getZ() * sinSave;
        
        setElem(0, 0, (oneMinusCos * xx) + cosSave);
        setElem(1, 0, (oneMinusCos * xy) - zs);
        setElem(2, 0, (oneMinusCos * zx) + ys);
        setElem(3, 0, 0.0f);
        
        setElem(0, 1, (oneMinusCos * xy) + zs);
        setElem(1, 1, (oneMinusCos * yy) + cosSave);
        setElem(2, 1, (oneMinusCos * yz) - xs);
        setElem(3, 1, 0.0f);
        
        setElem(0, 2, (oneMinusCos * zx) - ys);
        setElem(1, 2, (oneMinusCos * yz) + xs);
        setElem(2, 2, (oneMinusCos * zz) + cosSave);
        setElem(3, 2, 0.0f);
        
        return this;
    }
    
    public Matrix3f scale (Vector3f scale) {
        setElem(0, 0, scale.getX());
        setElem(1, 1, scale.getY());
        setElem(2, 2, scale.getZ());
        
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix3f matrix3f = (Matrix3f) o;

        if (MATRIX_LENGHT != matrix3f.MATRIX_LENGHT) return false;
        if (!Arrays.equals(matr, matrix3f.matr)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = matr != null ? Arrays.hashCode(matr) : 0;
        result = 31 * result + MATRIX_LENGHT;
        return result;
    }

    @Override
    public String toString() {
        return "Matrix3f{" +
                "matr=" + matr +
                ", MATRIX_LENGHT=" + MATRIX_LENGHT +
                '}';
    }
}
