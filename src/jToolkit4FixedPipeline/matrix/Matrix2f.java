/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jToolkit4FixedPipeline.matrix;

import jToolkit4FixedPipeline.vector.Vector2f;

import java.util.Arrays;

/**
 *
 * @author Astemir Eleev
 */
public class Matrix2f {
    private float[] matr;
    private final int MATRIX_LENGHT = 4;

    public Matrix2f() {
        matr = new float[MATRIX_LENGHT];
    }


    public Matrix2f transpase () {
        float temp = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                temp = getElem(i, j);
                setElem(i, j, getElem(j, i));
                setElem(j, i, temp);
            }
        }
        return this;
    }

    public Matrix2f transpose (final Matrix2f matr) {
        float temp = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                temp = matr.getElem(i, j);
                matr.setElem(i, j, matr.getElem(j, i));
                matr.setElem(j, i, temp);
                setElem(i, j, matr.getElem(i, j));
            }
        }
        return this;
    }

    public Matrix2f summ (final Matrix2f matr) {
        setElem(0, 0, getElem(0, 0) + matr.getElem(0, 0));
        setElem(0, 1, getElem(0, 1) + matr.getElem(0, 1));
        setElem(1, 0, getElem(1, 0) + matr.getElem(1, 0));
        setElem(1, 1, getElem(1, 1) + matr.getElem(1, 1));
        return this;
    }

    public Matrix2f summ (final Matrix2f fmatr, final Matrix2f smatr) {
        setElem(0, 0, fmatr.getElem(0, 0) + smatr.getElem(0, 0));
        setElem(0, 1, fmatr.getElem(0, 1) + smatr.getElem(0, 1));
        setElem(1, 0, fmatr.getElem(1, 0) + smatr.getElem(1, 0));
        setElem(1, 1, fmatr.getElem(1, 1) + smatr.getElem(1, 1));
        return this;
    }

    public Matrix2f scalar (final float scalar) {
        setElem(0, 0, getElem(0, 0) * scalar);
        setElem(0, 1, getElem(0, 1) * scalar);
        setElem(1, 0, getElem(1, 0) * scalar);
        setElem(1, 1, getElem(1, 1) * scalar);
        return this;
    }

    public Matrix2f scalar (final float scalar, final Matrix2f matr) {
        setElem(0, 0, matr.getElem(0, 0) * scalar);
        setElem(1, 0, matr.getElem(1, 0) * scalar);
        setElem(0, 1, matr.getElem(0, 1) * scalar);
        setElem(1, 1, matr.getElem(1, 1) * scalar);
        return this;
    }

    public Matrix2f multiply (final Matrix2f matr) {
        setElem(0, 0, getElem(0, 0) * matr.getElem(0, 0) + getElem(0, 1) * matr.getElem(1, 0));
        setElem(0, 1, getElem(0, 0) * matr.getElem(0, 1) + getElem(0, 1) * matr.getElem(1, 1));
        setElem(1, 0, getElem(1, 0) * matr.getElem(0, 0) + getElem(1, 1) * matr.getElem(1, 0));
        setElem(1, 1, getElem(1, 0) * matr.getElem(0, 1) + getElem(1, 1) * matr.getElem(1, 1));
        return this;
    }

    public Matrix2f multiply (final Matrix2f fmatr, final Matrix2f smatr) {
        setElem(0, 0, fmatr.getElem(0, 0) * smatr.getElem(0, 0) + fmatr.getElem(0, 1) * smatr.getElem(1, 0));
        setElem(0, 1, fmatr.getElem(0, 0) * smatr.getElem(0, 1) + fmatr.getElem(0, 1) * smatr.getElem(1, 1));
        setElem(1, 0, fmatr.getElem(1, 0) * smatr.getElem(0, 0) + fmatr.getElem(1, 1) * smatr.getElem(1, 0));
        setElem(1, 1, fmatr.getElem(1, 0) * smatr.getElem(0, 1) + fmatr.getElem(1, 1) * smatr.getElem(1, 1));
        return this;
    }

    public float det () {
        return (getElem(0, 0) * getElem(1, 1)) - (getElem(0, 1) * getElem(1, 0));
    }

    public Matrix2f makeIdentity () {
        setElem(0, 0, 1);
        setElem(1, 1, 1);
        setElem(0, 1, 0);
        setElem(1, 0, 0);
        return this;
    }

    public Matrix2f makeNull () {
        setElem(0, 0, 0);
        setElem(0, 1, 0);
        setElem(1, 0, 0);
        setElem(1, 1, 0);
        return this;
    }

    public float[][] getMatrix () {
        return new float[][] {{getElem(0, 0), getElem(0, 1)}, {getElem(1, 0), getElem(1, 1)}};
    }

    public void setColumn (int ind, Vector2f vector) {
        setElem(0, ind, vector.getX());
        setElem(1, ind, vector.getY());
    }

    public void setRow (int ind, Vector2f vector) {
        setElem(ind, 0, vector.getX());
        setElem(ind, 1, vector.getY());
    }

    public Vector2f getColumn (int ind) {
        return new Vector2f(getElem(0, ind), getElem(1, ind));
    }

    public Vector2f getRow (int ind) {
        return new Vector2f(getElem(ind, 0), getElem(ind, 1));
    }

    public Matrix2f rotate (final float angle, final DIRECTION_OF_ROTATION state) {
        setElem(0, 0, (float) Math.cos(angle));
        setElem(1, 1, (float) Math.cos(angle));

        if (state.getState()) {
            setElem(0, 1, (float) Math.sin(-angle));
            setElem(1, 0, (float) Math.sin(angle));
        } else {
            setElem(0, 1, (float) Math.sin(angle));
            setElem(1, 0, (float) Math.sin(-angle));
        }
        return this;
    }

    public Matrix2f scale (Vector2f scale) {
        setElem(0, 0, scale.getX());
        setElem(1, 1, scale.getY());

        return this;
    }

    public float getElem (int i, int j) {
        return matr[2 * i + j];
    }

    public void setElem (int i, int j, float value) {
        matr[2 * i + j] = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix2f matrix2f = (Matrix2f) o;

        if (MATRIX_LENGHT != matrix2f.MATRIX_LENGHT) return false;
        if (!Arrays.equals(matr, matrix2f.matr)) return false;

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
        return "Matrix2f{" +
                "matr=" + matr +
                ", MATRIX_LENGHT=" + MATRIX_LENGHT +
                '}';
    }
}
