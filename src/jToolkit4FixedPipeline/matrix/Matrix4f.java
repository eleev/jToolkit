package jToolkit4FixedPipeline.matrix;

import jToolkit4FixedPipeline.vector.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

/**
 * Math data structure
 * Float matrix 4 to 4 elements
 *
 * @author Astemir Eleev
 */
public class Matrix4f {
    private float matr[];
    private final int MATRIX_LENGHT = 16;

    public Matrix4f() {
        matr = new float[MATRIX_LENGHT];
    }

    public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13,
                    float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        matr = new float[MATRIX_LENGHT];
        matr[0] = m00;
        matr[1] = m01;
        matr[2] = m02;
        matr[3] = m03;
        matr[4] = m10;
        matr[5] = m11;
        matr[6] = m12;
        matr[7] = m13;
        matr[8] = m20;
        matr[9] = m21;
        matr[10] = m22;
        matr[11] = m23;
        matr[12] = m30;
        matr[13] = m31;
        matr[14] = m32;
        matr[15] = m33;
    }

    public Matrix4f transpose () {
        float tmp = 0;
        
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                tmp = getElem(i, j);
                setElem(i, j, getElem(j, i));
                setElem(j, i, tmp);
            }
        }
        return this;
    }
    
    public float getElem (int i, int j) {
        return matr[4 * i + j];
    }
    
    public void setElem (int i, int j, float value) {
        matr[4 * i + j] = value;
    }
    
    public Matrix4f makeIdentity () {
        for (int i = 0; i < 4; i++) {
            this.setElem(i, i, 1);
        }
        return this;
    }
    
    public Matrix4f makeIdentity (Matrix4f mMatrix) {
        Matrix4f temp = mMatrix;
        
        for (int i = 0; i < 4; i++) {
            temp.setElem(i, i, 1);
        }
        return temp;
    }

    @Deprecated
    public static Matrix4f makeLookAt(Vector3f eye, Vector3f center, Vector3f up) {
        return null;
    }

    public static Matrix4f makePerspective(float fov, float aspect, float nearPlane, float farPlane) {
        float yScale = (float)(1.0f / Math.tan((fov * (Math.PI/180)) / 2.0f));
        float xScale = yScale / aspect;
        float frustumLength = farPlane - nearPlane;

        Matrix4f perspective = new Matrix4f();

        perspective.setElem(0, 0, xScale);
        perspective.setElem(1, 0, 0);
        perspective.setElem(2, 0, 0);
        perspective.setElem(3, 0, 0);

        perspective.setElem(0, 1, 0);
        perspective.setElem(1, 1, yScale);
        perspective.setElem(2, 1, 0);
        perspective.setElem(3, 1, 0);

        perspective.setElem(0, 2, 0);
        perspective.setElem(1, 2, 0);
        perspective.setElem(2, 2, -((farPlane + nearPlane) / frustumLength));
        perspective.setElem(3, 2, -1);

        perspective.setElem(0, 3, 0);
        perspective.setElem(1, 3, 0);
        perspective.setElem(2, 3, -((2f * nearPlane * farPlane) / frustumLength));
        perspective.setElem(3, 3, 0);

        return perspective;
    }

    public static Matrix4f makeOrthographic(float left, float right, float bottom, float top, float nearZ, float farZ) {
        Matrix4f ortho = new Matrix4f();

        ortho.setElem(0, 0, 2 / (right = left));
        ortho.setElem(1, 0, 0);
        ortho.setElem(2, 0, 0);
        ortho.setElem(3, 0, 0);

        ortho.setElem(0, 1, 0);
        ortho.setElem(1, 1, 2 / (top / bottom));
        ortho.setElem(2, 1, 0);
        ortho.setElem(3, 1, 0);

        ortho.setElem(0, 2, 0);
        ortho.setElem(1, 2, 0);
        ortho.setElem(2, 2, -2 / (farZ - nearZ));
        ortho.setElem(3, 2, 0);

        ortho.setElem(0, 3, -(right + left) / (right - left));
        ortho.setElem(1, 3, -(top + bottom) / (top - bottom));
        ortho.setElem(2, 3, -(farZ + nearZ) / (farZ - nearZ));
        ortho.setElem(3, 3, 1);

        return ortho;
    }
    
    public Matrix4f rotation(float angle, Vector3f rotate) {
        float vecLength, sinSave, cosSave, oneMinusCos;
        float xx, yy, zz, xy, yz, zx, xs, ys, zs;
        Vector3f vec = new Vector3f();
        
        // If NULL vector passed in, this will blow up...
        if (rotate.getX() == 0.0f && rotate.getY() == 0.0f && rotate.getZ() == 0.0f) {
            return null;
        }

        // Scale vector
        vecLength = (float) Math.sqrt(rotate.getX() * rotate.getX() + rotate.getY() * rotate.getY() + rotate.getZ() * rotate.getZ());

        // Rotation matrix is normalized
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
        
        setElem(0, 3, 0.0f);
        setElem(1, 3, 0.0f);
        setElem(2, 3, 0.0f);
        setElem(3, 3, 1.0f);
        
        return this;
    }
    
    public Matrix4f translate (Vector3f translate) {
        setElem(0, 4, translate.getX());
        setElem(1, 4, translate.getX());
        setElem(2, 4, translate.getX());
        setElem(3, 4, 1.0f);
        return this;
    }
    
    public Matrix4f scale (Vector3f scale) {
        setElem(0, 0, scale.getX());
        setElem(1, 1, scale.getY());
        setElem(2, 2, scale.getZ());
        setElem(3, 3, 1.0f);
        return this;
    }
    
    public float det () {
        float det01 = ((getElem(1, 1) * getElem(2, 2) * getElem(3, 3))
                - (getElem(1, 1) * getElem(2, 3) * getElem(3, 2))
                - (getElem(1, 2) * getElem(2, 1) * getElem(3, 3))
                + (getElem(1, 2) * getElem(2, 3) * getElem(3, 1))
                + (getElem(1, 3) * getElem(2, 1) * getElem(3, 2))
                - (getElem(1, 3) * getElem(2, 2) * getElem(3, 1)));
        float det02 = ((getElem(1, 0) * getElem(2, 2) * getElem(3, 3))
                - (getElem(1, 0) * getElem(2, 3) * getElem(3, 2))
                - (getElem(1, 2) * getElem(2, 0) * getElem(3, 3))
                + (getElem(1, 2) * getElem(2, 3) * getElem(3, 0))
                + (getElem(1, 3) * getElem(2, 0) * getElem(3, 2))
                - (getElem(1, 3) * getElem(2, 2) * getElem(3, 0)));
        float det03 = ((getElem(1, 0) * getElem(2, 1) * getElem(3, 3))
                - (getElem(1, 0) * getElem(2, 3) * getElem(3, 1))
                - (getElem(1, 1) * getElem(2, 0) * getElem(3, 3))
                + (getElem(1, 1) * getElem(2, 3) * getElem(3, 0))
                + (getElem(1, 3) * getElem(2, 0) * getElem(3, 1))
                - (getElem(1, 3) * getElem(2, 1) * getElem(3, 0)));
        float det04 = ((getElem(1, 0) * getElem(2, 1) * getElem(3, 2))
                - (getElem(1, 0) * getElem(2, 2) * getElem(3, 1))
                - (getElem(1, 1) * getElem(2, 0) * getElem(3, 2))
                + (getElem(1, 1) * getElem(2, 2) * getElem(3, 0))
                + (getElem(1, 2) * getElem(2, 0) * getElem(3, 1))
                - (getElem(1, 2) * getElem(2, 1) * getElem(3, 0)));
        
        return det01 * getElem(0, 0) - det02 * getElem(0, 1) + det03 * getElem(0, 2) - det04 * getElem(0, 3);
    }
    
    public Matrix4f summ (Matrix4f matr) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                setElem(i, j, getElem(i, j) + matr.getElem(i, j));
            }
        }
        return this;
    }
    
    public Matrix4f multiply (Matrix3f matr) {
         for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    this.setElem(i, j, this.getElem(i, j) + this.getElem(i, k) * matr.getElem(k, j));
                }   
            }
        }
        return this;
    }
    
    public Matrix4f multiply (Matrix4f matr, Matrix4f out) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    out.setElem(i, j, this.getElem(i, j) + this.getElem(i, k) * matr.getElem(k, j));
                }   
            }
        }
        return out;
    }
    
    public Matrix4f summ (Matrix4f first, Matrix4f second) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                setElem(i, j, first.getElem(i, j) + second.getElem(i, j));
            }
        }
        return this;
    }

    public Matrix4f dotProduct (Vector3f vec) {
        setElem(0, 0, getElem(0, 0) * vec.getX());
        setElem(0, 1, getElem(0, 1) * vec.getY());
        setElem(0, 2, getElem(0, 2) * vec.getZ());
        setElem(0, 3, 0.0f);
        
        setElem(1, 0, getElem(1, 0) * vec.getX());
        setElem(1, 1, getElem(1, 1) * vec.getY());
        setElem(1, 2, getElem(1, 2) * vec.getZ());
        setElem(1, 3, 0.0f);
        
        setElem(2, 0, getElem(2, 0) * vec.getX());
        setElem(2, 1, getElem(2, 1) * vec.getY());
        setElem(2, 2, getElem(2, 2) * vec.getZ());
        setElem(2, 3, 0.0f);
        
        setElem(3, 0, getElem(3, 0) * vec.getX());
        setElem(3, 1, getElem(3, 1) * vec.getY());
        setElem(3, 2, getElem(3, 2) * vec.getZ());
        setElem(3, 3, 0.0f);
        
        return this;
    }
    
    public Matrix4f substract (Vector3f vec) {
        setElem(0, 0, getElem(0, 0) - vec.getX());
        setElem(0, 1, getElem(0, 1) - vec.getY());
        setElem(0, 2, getElem(0, 2) - vec.getZ());
        setElem(0, 3, 0.0f);
        
        setElem(1, 0, getElem(1, 0) - vec.getX());
        setElem(1, 1, getElem(1, 1) - vec.getY());
        setElem(1, 2, getElem(1, 2) - vec.getZ());
        setElem(1, 3, 0.0f);
        
        setElem(2, 0, getElem(2, 0) - vec.getX());
        setElem(2, 1, getElem(2, 1) - vec.getY());
        setElem(2, 2, getElem(2, 2) - vec.getZ());
        setElem(2, 3, 0.0f);
        
        setElem(3, 0, getElem(3, 0) - vec.getX());
        setElem(3, 1, getElem(3, 1) - vec.getY());
        setElem(3, 2, getElem(3, 2) - vec.getZ());
        setElem(3, 3, 0.0f);
        
        return this;
    }
    
    public Matrix4f substract (Matrix4f matr) {
        setElem(0, 0, getElem(0, 0) - matr.getElem(0, 0));
        setElem(0, 1, getElem(0, 1) - matr.getElem(0, 1));
        setElem(0, 2, getElem(0, 2) - matr.getElem(0, 2));
        setElem(0, 3, getElem(0, 3) - matr.getElem(0, 3));
        
        setElem(1, 0, getElem(1, 0) - matr.getElem(1, 0));
        setElem(1, 1, getElem(1, 1) - matr.getElem(1, 1));
        setElem(1, 2, getElem(1, 2) - matr.getElem(1, 2));
        setElem(1, 3, getElem(1, 3) - matr.getElem(1, 3));
        
        setElem(2, 0, getElem(2, 0) - matr.getElem(2, 0));
        setElem(2, 1, getElem(2, 1) - matr.getElem(2, 1));
        setElem(2, 2, getElem(2, 2) - matr.getElem(2, 2));
        setElem(2, 3, getElem(2, 3) - matr.getElem(2, 3));
        
        setElem(3, 0, getElem(3, 0) - matr.getElem(3, 0));
        setElem(3, 1, getElem(3, 1) - matr.getElem(3, 1));
        setElem(3, 2, getElem(3, 2) - matr.getElem(3, 2));
        setElem(3, 3, getElem(3, 3) - matr.getElem(3, 3));
        
        return this;
    }
    
    public void setMatrix4f (Matrix4f matr) {
        setElem(0, 0, matr.getElem(0, 0));
        setElem(0, 1, matr.getElem(0, 1));
        setElem(0, 2, matr.getElem(0, 2));
        setElem(0, 3, matr.getElem(0, 3));
        
        setElem(1, 0, matr.getElem(1, 0));
        setElem(1, 1, matr.getElem(1, 1));
        setElem(1, 2, matr.getElem(1, 2));
        setElem(1, 3, matr.getElem(1, 3));
        
        setElem(2, 0, matr.getElem(2, 0));
        setElem(2, 1, matr.getElem(2, 1));
        setElem(2, 2, matr.getElem(2, 2));
        setElem(2, 3, matr.getElem(2, 3));
        
        setElem(3, 0, matr.getElem(3, 0));
        setElem(3, 1, matr.getElem(3, 1));
        setElem(3, 2, matr.getElem(3, 2));
        setElem(3, 3, matr.getElem(3, 3));
    }
    
    public Matrix4f getMatrix4f () {
        return this;
    }
    
    public float[] toFloatArray () {
        return matr;
    }
    
    public float[][] toFloatMatrix() {
        return new float[][]{new float[]{getElem(0, 0), getElem(0, 1), getElem(0, 2), getElem(0, 3)},
                    new float[]{getElem(1, 0), getElem(1, 1), getElem(1, 2), getElem(1, 3)},
                    new float[]{getElem(2, 0), getElem(2, 1), getElem(2, 2), getElem(2, 3)},
                    new float[]{getElem(3, 0), getElem(3, 1), getElem(3, 2), getElem(3, 3)}};
    }

    public Matrix4f setFloatMatrix (final float[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4) {
            System.err.println("Matrix length is not appropriate");
            return null;
        }

        setElem(0, 0, matrix[0][0]);
        setElem(0, 1, matrix[0][1]);
        setElem(0, 2, matrix[0][2]);
        setElem(0, 3, matrix[0][3]);

        setElem(1, 0, matrix[1][0]);
        setElem(1, 1, matrix[1][1]);
        setElem(1, 2, matrix[1][2]);
        setElem(1, 3, matrix[1][3]);

        setElem(2, 0, matrix[2][0]);
        setElem(2, 1, matrix[2][1]);
        setElem(2, 2, matrix[2][2]);
        setElem(2, 3, matrix[2][3]);

        setElem(3, 0, matrix[3][0]);
        setElem(3, 1, matrix[3][1]);
        setElem(3, 2, matrix[3][2]);
        setElem(3, 3, matrix[3][3]);

        return this;
    }

    public Matrix4f calcReflectionMatrix(float planeHeight, float playerHeight) {
        makeIdentity();
        setElem(1, 3, 2f * (-playerHeight + planeHeight));
        setElem(1, 1, -1.0f);
        return this;
    }

    public FloatBuffer convertToFloateBuffer () {
        FloatBuffer fb = BufferUtils.createFloatBuffer(MATRIX_LENGHT);
        fb.put(matr);
        fb.position(0);
        return fb;    
    }

    public FloatBuffer convertToFloateBuffer (final Matrix4f that) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(that.MATRIX_LENGHT);
        fb.put(that.toFloatArray());
        fb.position(0);
        return fb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix4f matrix4f = (Matrix4f) o;

        if (MATRIX_LENGHT != matrix4f.MATRIX_LENGHT) return false;
        if (!Arrays.equals(matr, matrix4f.matr)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(matr);
        result = 31 * result + MATRIX_LENGHT;
        return result;
    }

    @Override
    public String toString() {
        return getElem(0, 0) + " " + getElem(0, 1) + " " + getElem(0, 2) + " " + getElem(0, 3) + "\n"
                + getElem(1, 0) + " " + getElem(1, 1) + " " + getElem(1, 2) + " " + getElem(1, 3) + "\n"
                + getElem(2, 0) + " " + getElem(2, 1) + " " + getElem(2, 2) + " " + getElem(2, 3) + "\n"
                + getElem(3, 0) + " " + getElem(3, 1) + " " + getElem(3, 2) + " " + getElem(3, 3) + "\n";
    }
    
}
