package jToolkit4FixedPipeline.common;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

/**
 *
 * @author Astemir Eleev
 */
public class GLUT {

    public static void glutWireSphere(double radius, int slices, int stacks) {
        sphere((float) radius, slices, stacks, GLU_LINE);
    }

    public static void glutSolidSphere(double radius, int slices, int stacks) {
        sphere((float) radius, slices, stacks, GLU_FILL);
    }

    private static void sphere(float radius, int slices, int stacks, int style) {
        Sphere s = new Sphere();
        s.setDrawStyle(style);
        s.setNormals(GLU_SMOOTH);
        s.draw(radius, slices, stacks);
    }

    private static void cylinder(float baseRadius, float topRadius, float height,
        int slices, int stacks, int style) {
        Cylinder c = new Cylinder();
        c.setDrawStyle(style);
        c.setNormals(GLU_SMOOTH);
        c.draw(baseRadius, topRadius, height, slices, stacks);
    }

    public static void glutWireCone(double base, double height, int slices, int stacks) {
        cylinder((float) base, 0, (float) height, slices, stacks, GLU_LINE);
    }

    public static void glutSolidCone(double base, double height, int slices, int stacks) {
        cylinder((float) base, 0, (float) height, slices, stacks, GLU_FILL);
    }

    public static void glutWireCylinder(double radius, double height, int slices, int stacks) {
        cylinder((float) radius, (float) radius, (float) height, slices, stacks, GLU_LINE);
    }

    public static void glutSolidCylinder(double radius, double height, int slices, int stacks, boolean drawCaps) {
        cylinder((float) radius, (float) radius, (float) height, slices, stacks, GLU_FILL);
        if (drawCaps) {
            glutSolidCylinderCaps(radius, height, slices, stacks);
        }
    }

    public static void glutSolidCylinder(double radius, double height, int slices, int stacks) {
        glutSolidCylinder(radius, height, slices, stacks, true);
    }

    // Disk objects might be better and would allow for holes in the caps
    public static void glutSolidCylinderCaps(double radius, double height, int slices, int stacks) {
        // Prepare table of points for drawing end caps
        double[] x = new double[slices];
        double[] y = new double[slices];
        double angleDelta = Math.PI * 2 / slices;
        double angle = 0;

        for (int i = 0; i < slices; i++) {
            angle = i * angleDelta;
            x[i] = Math.cos(angle) * radius;
            y[i] = Math.sin(angle) * radius;
        }

        // Draw bottom cap
        glBegin(GL_TRIANGLE_FAN);
        glNormal3d(0, 0, -1);
        glVertex3d(0, 0, 0);
        for (int i = 0; i < slices; i++) {
            glVertex3d(x[i], y[i], 0);
        }
        glVertex3d(x[0], y[0], 0);
        glEnd();

        // Draw top cap
        glBegin(GL_TRIANGLE_FAN);
        glNormal3d(0, 0, 1);
        glVertex3d(0, 0, height);
        for (int i = 0; i < slices; i++) {
            glVertex3d(x[i], y[i], height);
        }
        glVertex3d(x[0], y[0], height);
        glEnd();
    }

    public static void glutWireCube(float size) {
        drawBox(size, GL_LINE_LOOP);
    }

    public static void glutSolidCube(float size) {
        drawBox(size, GL_QUADS);
    }

    public static void glutWireTorus(double innerRadius, double outerRadius,
            int nsides, int rings) {
        glPushAttrib(GL_POLYGON_BIT);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        doughnut(innerRadius, outerRadius, nsides, rings);
        glPopAttrib();
    }

    public static void glutSolidTorus(double innerRadius, double outerRadius,
            int nsides, int rings) {
        doughnut(innerRadius, outerRadius, nsides, rings);
    }

    public static void glutWireDodecahedron() {
        dodecahedron(GL_LINE_LOOP);
    }

    public static void glutSolidDodecahedron() {
        dodecahedron(GL_TRIANGLE_FAN);
    }

    public static void glutWireOctahedron() {
        octahedron(GL_LINE_LOOP);
    }

    public static void glutSolidOctahedron() {
        octahedron(GL_TRIANGLES);
    }

    public static void glutWireIcosahedron() {
        icosahedron(GL_LINE_LOOP);
    }

    public static void glutSolidIcosahedron() {
        icosahedron(GL_TRIANGLES);
    }

    public static void glutWireTetrahedron() {
        tetrahedron(GL_LINE_LOOP);
    }

    public static void glutSolidTetrahedron() {
        tetrahedron(GL_TRIANGLES);
    }

    public static void glutSolidTeapot(double scale) {
        glutSolidTeapot(scale, true);
    }

    public static void glutSolidTeapot(double scale, boolean cStyle) {
        teapot(14, scale, GL_FILL, cStyle);
    }

    public static void glutWireTeapot(double scale) {
        glutWireTeapot(scale, true);
    }

    public static void glutWireTeapot(double scale, boolean cStyle) {
        teapot(10, scale, GL_LINE, cStyle);
    }

    public static void glutWireRhombicDodecahedron() {
        rhombicDodecahedron(GL_LINE_LOOP);
    }

    public static void glutSolidRhombicDodecahedron() {
        rhombicDodecahedron(GL_QUADS);
    }

    private static void rhombicDodecahedron(int mode) {
        for (int i = 0; i < 12; i++) {
            glBegin(mode);
            glNormal3d(rdod_n[i][0], rdod_n[i][1], rdod_n[i][2]);
            for (int j = 0; j <= 3; j++) {
                glVertex3d(rdod_r[rdod_v[i][j]][0], rdod_r[rdod_v[i][j]][1], rdod_r[rdod_v[i][j]][2]);
            }
            glEnd();
        }
    }

    // Implementations
    private static void doughnut(double r, double R, int nsides, int rings) {
        int i, j;
        float theta, phi, theta1;
        float cosTheta, sinTheta;
        float cosTheta1, sinTheta1;
        float ringDelta, sideDelta;

        ringDelta = (float) (2.0 * Math.PI / rings);
        sideDelta = (float) (2.0 * Math.PI / nsides);

        theta = 0.0f;
        cosTheta = 1.0f;
        sinTheta = 0.0f;
        for (i = rings - 1; i >= 0; i--) {
            theta1 = theta + ringDelta;
            cosTheta1 = (float) Math.cos(theta1);
            sinTheta1 = (float) Math.sin(theta1);
            glBegin(GL_QUAD_STRIP);
            phi = 0.0f;
            for (j = nsides; j >= 0; j--) {
                float cosPhi, sinPhi, dist;

                phi += sideDelta;
                cosPhi = (float) Math.cos(phi);
                sinPhi = (float) Math.sin(phi);
                dist = (float) (R + r * cosPhi);

                glNormal3f(cosTheta1 * cosPhi, -sinTheta1 * cosPhi, sinPhi);
                glVertex3f(cosTheta1 * dist, -sinTheta1 * dist, (float) r * sinPhi);
                glNormal3f(cosTheta * cosPhi, -sinTheta * cosPhi, sinPhi);
                glVertex3f(cosTheta * dist, -sinTheta * dist, (float) r * sinPhi);
            }
            glEnd();
            theta = theta1;
            cosTheta = cosTheta1;
            sinTheta = sinTheta1;
        }
    }
    private static float[][] boxVertices;
    private static final float[][] boxNormals = {
        {-1.0f, 0.0f, 0.0f},
        {0.0f, 1.0f, 0.0f},
        {1.0f, 0.0f, 0.0f},
        {0.0f, -1.0f, 0.0f},
        {0.0f, 0.0f, 1.0f},
        {0.0f, 0.0f, -1.0f}
    };
    private static final int[][] boxFaces = {
        {0, 1, 2, 3},
        {3, 2, 6, 7},
        {7, 6, 5, 4},
        {4, 5, 1, 0},
        {5, 6, 2, 1},
        {7, 4, 0, 3}
    };

    private static void drawBox(float size, int type) {
        if (boxVertices == null) {
            float[][] v = new float[8][];
            for (int i = 0; i < 8; i++) {
                v[i] = new float[3];
            }
            v[0][0] = v[1][0] = v[2][0] = v[3][0] = -0.5f;
            v[4][0] = v[5][0] = v[6][0] = v[7][0] = 0.5f;
            v[0][1] = v[1][1] = v[4][1] = v[5][1] = -0.5f;
            v[2][1] = v[3][1] = v[6][1] = v[7][1] = 0.5f;
            v[0][2] = v[3][2] = v[4][2] = v[7][2] = -0.5f;
            v[1][2] = v[2][2] = v[5][2] = v[6][2] = 0.5f;
            boxVertices = v;
        }
        float[][] v = boxVertices;
        float[][] n = boxNormals;
        int[][] faces = boxFaces;
        for (int i = 5; i >= 0; i--) {
            glBegin(type);
            glNormal3f(n[i][0], n[i][1], n[i][2]);
            float[] vt = v[faces[i][0]];
            glVertex3f(vt[0] * size, vt[1] * size, vt[2] * size);
            vt = v[faces[i][1]];
            glVertex3f(vt[0] * size, vt[1] * size, vt[2] * size);
            vt = v[faces[i][2]];
            glVertex3f(vt[0] * size, vt[1] * size, vt[2] * size);
            vt = v[faces[i][3]];
            glVertex3f(vt[0] * size, vt[1] * size, vt[2] * size);
            glEnd();
        }
    }
    private static float[][] dodec = buildDodacahedron();

    private static float[][] buildDodacahedron() {
        float[][] dd = new float[20][];
        for (int i = 0; i < dd.length; i++) {
            dd[i] = new float[3];
        }

        float alpha, beta;

        alpha = (float) Math.sqrt(2.0f / (3.0f + Math.sqrt(5.0)));
        beta = 1.0f + (float) Math.sqrt(6.0 / (3.0 + Math.sqrt(5.0))
                - 2.0 + 2.0 * Math.sqrt(2.0 / (3.0 + Math.sqrt(5.0))));
        dd[0][0] = -alpha;
        dd[0][1] = 0;
        dd[0][2] = beta;
        dd[1][0] = alpha;
        dd[1][1] = 0;
        dd[1][2] = beta;
        dd[2][0] = -1;
        dd[2][1] = -1;
        dd[2][2] = -1;
        dd[3][0] = -1;
        dd[3][1] = -1;
        dd[3][2] = 1;
        dd[4][0] = -1;
        dd[4][1] = 1;
        dd[4][2] = -1;
        dd[5][0] = -1;
        dd[5][1] = 1;
        dd[5][2] = 1;
        dd[6][0] = 1;
        dd[6][1] = -1;
        dd[6][2] = -1;
        dd[7][0] = 1;
        dd[7][1] = -1;
        dd[7][2] = 1;
        dd[8][0] = 1;
        dd[8][1] = 1;
        dd[8][2] = -1;
        dd[9][0] = 1;
        dd[9][1] = 1;
        dd[9][2] = 1;
        dd[10][0] = beta;
        dd[10][1] = alpha;
        dd[10][2] = 0;
        dd[11][0] = beta;
        dd[11][1] = -alpha;
        dd[11][2] = 0;
        dd[12][0] = -beta;
        dd[12][1] = alpha;
        dd[12][2] = 0;
        dd[13][0] = -beta;
        dd[13][1] = -alpha;
        dd[13][2] = 0;
        dd[14][0] = -alpha;
        dd[14][1] = 0;
        dd[14][2] = -beta;
        dd[15][0] = alpha;
        dd[15][1] = 0;
        dd[15][2] = -beta;
        dd[16][0] = 0;
        dd[16][1] = beta;
        dd[16][2] = alpha;
        dd[17][0] = 0;
        dd[17][1] = beta;
        dd[17][2] = -alpha;
        dd[18][0] = 0;
        dd[18][1] = -beta;
        dd[18][2] = alpha;
        dd[19][0] = 0;
        dd[19][1] = -beta;
        dd[19][2] = -alpha;

        return dd;
    }

    private static void diff3(float[] a, float[] b, float[] c) {
        c[0] = a[0] - b[0];
        c[1] = a[1] - b[1];
        c[2] = a[2] - b[2];
    }

    private static void crossprod(float[] v1, float[] v2, float[] prod) {
        float[] p = new float[3];         /*
         * in case prod == v1 or v2
         */

        p[0] = v1[1] * v2[2] - v2[1] * v1[2];
        p[1] = v1[2] * v2[0] - v2[2] * v1[0];
        p[2] = v1[0] * v2[1] - v2[0] * v1[1];
        prod[0] = p[0];
        prod[1] = p[1];
        prod[2] = p[2];
    }

    private static void normalize(float[] v) {
        float d;

        d = (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
        if (d == 0.0) {
            v[0] = d = 1.0f;
        }
        d = 1 / d;
        v[0] *= d;
        v[1] *= d;
        v[2] *= d;
    }

    private static void pentagon(int a, int b, int c, int d, int e, int shadeType) {
        float[] n0 = new float[3];
        float[] d1 = new float[3];
        float[] d2 = new float[3];

        diff3(dodec[a], dodec[b], d1);
        diff3(dodec[b], dodec[c], d2);
        crossprod(d1, d2, n0);
        normalize(n0);

        glBegin(shadeType);
        glNormal3f(n0[0], n0[1], n0[2]);
        glVertex3f(dodec[a][0], dodec[a][1], dodec[a][2]);
        glVertex3f(dodec[b][0], dodec[b][1], dodec[b][2]);
        glVertex3f(dodec[c][0], dodec[c][1], dodec[c][2]);
        glVertex3f(dodec[d][0], dodec[d][1], dodec[d][2]);
        glVertex3f(dodec[e][0], dodec[e][1], dodec[e][2]);
        glEnd();
    }

    private static void dodecahedron(int type) {
        pentagon(0, 1, 9, 16, 5, type);
        pentagon(1, 0, 3, 18, 7, type);
        pentagon(1, 7, 11, 10, 9, type);
        pentagon(11, 7, 18, 19, 6, type);
        pentagon(8, 17, 16, 9, 10, type);
        pentagon(2, 14, 15, 6, 19, type);
        pentagon(2, 13, 12, 4, 14, type);
        pentagon(2, 19, 18, 3, 13, type);
        pentagon(3, 0, 5, 12, 13, type);
        pentagon(6, 15, 8, 10, 11, type);
        pentagon(4, 17, 8, 15, 14, type);
        pentagon(4, 12, 5, 16, 17, type);
    }

    private static void recorditem(float[] n1, float[] n2, float[] n3, int shadeType) {
        float[] q0 = new float[3];
        float[] q1 = new float[3];

        diff3(n1, n2, q0);
        diff3(n2, n3, q1);
        crossprod(q0, q1, q1);
        normalize(q1);

        glBegin(shadeType);
        glNormal3f(q1[0], q1[1], q1[2]);
        glVertex3f(n1[0], n1[1], n1[2]);
        glVertex3f(n2[0], n2[1], n2[2]);
        glVertex3f(n3[0], n3[1], n3[2]);
        glEnd();
    }

    private static void subdivide(float[] v0, float[] v1, float[] v2, int shadeType) {
        int depth;
        float[] w0 = new float[3];
        float[] w1 = new float[3];
        float[] w2 = new float[3];
        float l;
        int i, j, k, n;

        depth = 1;
        for (i = 0; i < depth; i++) {
            for (j = 0; i + j < depth; j++) {
                k = depth - i - j;
                for (n = 0; n < 3; n++) {
                    w0[n] = (i * v0[n] + j * v1[n] + k * v2[n]) / depth;
                    w1[n] = ((i + 1) * v0[n] + j * v1[n] + (k - 1) * v2[n])
                            / depth;
                    w2[n] = (i * v0[n] + (j + 1) * v1[n] + (k - 1) * v2[n])
                            / depth;
                }
                l = (float) Math.sqrt(w0[0] * w0[0] + w0[1] * w0[1] + w0[2] * w0[2]);
                w0[0] /= l;
                w0[1] /= l;
                w0[2] /= l;
                l = (float) Math.sqrt(w1[0] * w1[0] + w1[1] * w1[1] + w1[2] * w1[2]);
                w1[0] /= l;
                w1[1] /= l;
                w1[2] /= l;
                l = (float) Math.sqrt(w2[0] * w2[0] + w2[1] * w2[1] + w2[2] * w2[2]);
                w2[0] /= l;
                w2[1] /= l;
                w2[2] /= l;
                recorditem(w1, w0, w2, shadeType);
            }
        }
    }

    private static void drawtriangle(int i, float[][] data, int[][] ndx, int shadeType) {
        float[] x0 = data[ndx[i][0]];
        float[] x1 = data[ndx[i][1]];
        float[] x2 = data[ndx[i][2]];
        subdivide(x0, x1, x2, shadeType);
    }

    /*
     * octahedron data: The octahedron produced is centered at the origin and
     * has radius 1.0
     */
    private static final float[][] odata = {
        {1.0f, 0.0f, 0.0f},
        {-1.0f, 0.0f, 0.0f},
        {0.0f, 1.0f, 0.0f},
        {0.0f, -1.0f, 0.0f},
        {0.0f, 0.0f, 1.0f},
        {0.0f, 0.0f, -1.0f}
    };
    private static final int[][] ondex = {
        {0, 4, 2},
        {1, 2, 4},
        {0, 3, 4},
        {1, 4, 3},
        {0, 2, 5},
        {1, 5, 2},
        {0, 5, 3},
        {1, 3, 5}
    };

    private static void octahedron(int shadeType) {
        for (int i = 7; i >= 0; i--) {
            drawtriangle(i, odata, ondex, shadeType);
        }
    }

    /*
     * icosahedron data: These numbers are rigged to make an icosahedron of
     * radius 1.0
     */
    private static final float X = .525731112119133606f;
    private static final float Z = .850650808352039932f;
    private static final float[][] idata = {
        {-X, 0, Z},
        {X, 0, Z},
        {-X, 0, -Z},
        {X, 0, -Z},
        {0, Z, X},
        {0, Z, -X},
        {0, -Z, X},
        {0, -Z, -X},
        {Z, X, 0},
        {-Z, X, 0},
        {Z, -X, 0},
        {-Z, -X, 0}
    };
    private static final int[][] index = {
        {0, 4, 1},
        {0, 9, 4},
        {9, 5, 4},
        {4, 5, 8},
        {4, 8, 1},
        {8, 10, 1},
        {8, 3, 10},
        {5, 3, 8},
        {5, 2, 3},
        {2, 7, 3},
        {7, 10, 3},
        {7, 6, 10},
        {7, 11, 6},
        {11, 0, 6},
        {0, 1, 6},
        {6, 1, 10},
        {9, 0, 11},
        {9, 11, 2},
        {9, 2, 5},
        {7, 2, 11},};

    private static void icosahedron(int shadeType) {
        for (int i = 19; i >= 0; i--) {
            drawtriangle(i, idata, index, shadeType);
        }
    }

    /*
     * rhombic dodecahedron data:
     */
    private static final double rdod_r[][] = {
        {0.0, 0.0, 1.0},
        {0.707106781187, 0.000000000000, 0.5},
        {0.000000000000, 0.707106781187, 0.5},
        {-0.707106781187, 0.000000000000, 0.5},
        {0.000000000000, -0.707106781187, 0.5},
        {0.707106781187, 0.707106781187, 0.0},
        {-0.707106781187, 0.707106781187, 0.0},
        {-0.707106781187, -0.707106781187, 0.0},
        {0.707106781187, -0.707106781187, 0.0},
        {0.707106781187, 0.000000000000, -0.5},
        {0.000000000000, 0.707106781187, -0.5},
        {-0.707106781187, 0.000000000000, -0.5},
        {0.000000000000, -0.707106781187, -0.5},
        {0.0, 0.0, -1.0}
    };
    private static final int rdod_v[][] = {
        {0, 1, 5, 2},
        {0, 2, 6, 3},
        {0, 3, 7, 4},
        {0, 4, 8, 1},
        {5, 10, 6, 2},
        {6, 11, 7, 3},
        {7, 12, 8, 4},
        {8, 9, 5, 1},
        {5, 9, 13, 10},
        {6, 10, 13, 11},
        {7, 11, 13, 12},
        {8, 12, 13, 9}
    };
    private static final double rdod_n[][] = {
        {0.353553390594, 0.353553390594, 0.5},
        {-0.353553390594, 0.353553390594, 0.5},
        {-0.353553390594, -0.353553390594, 0.5},
        {0.353553390594, -0.353553390594, 0.5},
        {0.000000000000, 1.000000000000, 0.0},
        {-1.000000000000, 0.000000000000, 0.0},
        {0.000000000000, -1.000000000000, 0.0},
        {1.000000000000, 0.000000000000, 0.0},
        {0.353553390594, 0.353553390594, -0.5},
        {-0.353553390594, 0.353553390594, -0.5},
        {-0.353553390594, -0.353553390594, -0.5},
        {0.353553390594, -0.353553390594, -0.5}
    };

    /*
     * tetrahedron data:
     */
    private static final float T = 1.73205080756887729f;
    private static final float[][] tdata = {
        {T, T, T},
        {T, -T, -T},
        {-T, T, -T},
        {-T, -T, T}
    };
    private static final int[][] tndex = {
        {0, 1, 3},
        {2, 1, 0},
        {3, 2, 0},
        {1, 2, 3}
    };

    private static void tetrahedron(int shadeType) {
        for (int i = 3; i >= 0; i--) {
            drawtriangle(i, tdata, tndex, shadeType);
        }
    }
    // Teapot implementation (a modified port of glut_teapot.c)
    //
    // Rim, body, lid, and bottom data must be reflected in x and
    // y; handle and spout data across the y axis only.
    private static final int[][] teapotPatchData = {
        /*
         * rim
         */
        {102, 103, 104, 105, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
        /*
         * body
         */
        {12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27},
        {24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40},
        /*
         * lid
         */
        {96, 96, 96, 96, 97, 98, 99, 100, 101, 101, 101, 101, 0, 1, 2, 3,},
        {0, 1, 2, 3, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117},
        /*
         * bottom
         */
        {118, 118, 118, 118, 124, 122, 119, 121, 123, 126, 125, 120, 40, 39, 38, 37},
        /*
         * handle
         */
        {41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56},
        {53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 28, 65, 66, 67},
        /*
         * spout
         */
        {68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83},
        {80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95}
    };
    private static final float[][] teapotCPData = {
        {0.2f, 0f, 2.7f},
        {0.2f, -0.112f, 2.7f},
        {0.112f, -0.2f, 2.7f},
        {0f, -0.2f, 2.7f},
        {1.3375f, 0f, 2.53125f},
        {1.3375f, -0.749f, 2.53125f},
        {0.749f, -1.3375f, 2.53125f},
        {0f, -1.3375f, 2.53125f},
        {1.4375f, 0f, 2.53125f},
        {1.4375f, -0.805f, 2.53125f},
        {0.805f, -1.4375f, 2.53125f},
        {0f, -1.4375f, 2.53125f},
        {1.5f, 0f, 2.4f},
        {1.5f, -0.84f, 2.4f},
        {0.84f, -1.5f, 2.4f},
        {0f, -1.5f, 2.4f},
        {1.75f, 0f, 1.875f},
        {1.75f, -0.98f, 1.875f},
        {0.98f, -1.75f, 1.875f},
        {0f, -1.75f, 1.875f},
        {2f, 0f, 1.35f},
        {2f, -1.12f, 1.35f},
        {1.12f, -2f, 1.35f},
        {0f, -2f, 1.35f},
        {2f, 0f, 0.9f},
        {2f, -1.12f, 0.9f},
        {1.12f, -2f, 0.9f},
        {0f, -2f, 0.9f},
        {-2f, 0f, 0.9f},
        {2f, 0f, 0.45f},
        {2f, -1.12f, 0.45f},
        {1.12f, -2f, 0.45f},
        {0f, -2f, 0.45f},
        {1.5f, 0f, 0.225f},
        {1.5f, -0.84f, 0.225f},
        {0.84f, -1.5f, 0.225f},
        {0f, -1.5f, 0.225f},
        {1.5f, 0f, 0.15f},
        {1.5f, -0.84f, 0.15f},
        {0.84f, -1.5f, 0.15f},
        {0f, -1.5f, 0.15f},
        {-1.6f, 0f, 2.025f},
        {-1.6f, -0.3f, 2.025f},
        {-1.5f, -0.3f, 2.25f},
        {-1.5f, 0f, 2.25f},
        {-2.3f, 0f, 2.025f},
        {-2.3f, -0.3f, 2.025f},
        {-2.5f, -0.3f, 2.25f},
        {-2.5f, 0f, 2.25f},
        {-2.7f, 0f, 2.025f},
        {-2.7f, -0.3f, 2.025f},
        {-3f, -0.3f, 2.25f},
        {-3f, 0f, 2.25f},
        {-2.7f, 0f, 1.8f},
        {-2.7f, -0.3f, 1.8f},
        {-3f, -0.3f, 1.8f},
        {-3f, 0f, 1.8f},
        {-2.7f, 0f, 1.575f},
        {-2.7f, -0.3f, 1.575f},
        {-3f, -0.3f, 1.35f},
        {-3f, 0f, 1.35f},
        {-2.5f, 0f, 1.125f},
        {-2.5f, -0.3f, 1.125f},
        {-2.65f, -0.3f, 0.9375f},
        {-2.65f, 0f, 0.9375f},
        {-2f, -0.3f, 0.9f},
        {-1.9f, -0.3f, 0.6f},
        {-1.9f, 0f, 0.6f},
        {1.7f, 0f, 1.425f},
        {1.7f, -0.66f, 1.425f},
        {1.7f, -0.66f, 0.6f},
        {1.7f, 0f, 0.6f},
        {2.6f, 0f, 1.425f},
        {2.6f, -0.66f, 1.425f},
        {3.1f, -0.66f, 0.825f},
        {3.1f, 0f, 0.825f},
        {2.3f, 0f, 2.1f},
        {2.3f, -0.25f, 2.1f},
        {2.4f, -0.25f, 2.025f},
        {2.4f, 0f, 2.025f},
        {2.7f, 0f, 2.4f},
        {2.7f, -0.25f, 2.4f},
        {3.3f, -0.25f, 2.4f},
        {3.3f, 0f, 2.4f},
        {2.8f, 0f, 2.475f},
        {2.8f, -0.25f, 2.475f},
        {3.525f, -0.25f, 2.49375f},
        {3.525f, 0f, 2.49375f},
        {2.9f, 0f, 2.475f},
        {2.9f, -0.15f, 2.475f},
        {3.45f, -0.15f, 2.5125f},
        {3.45f, 0f, 2.5125f},
        {2.8f, 0f, 2.4f},
        {2.8f, -0.15f, 2.4f},
        {3.2f, -0.15f, 2.4f},
        {3.2f, 0f, 2.4f},
        {0f, 0f, 3.15f},
        {0.8f, 0f, 3.15f},
        {0.8f, -0.45f, 3.15f},
        {0.45f, -0.8f, 3.15f},
        {0f, -0.8f, 3.15f},
        {0f, 0f, 2.85f},
        {1.4f, 0f, 2.4f},
        {1.4f, -0.784f, 2.4f},
        {0.784f, -1.4f, 2.4f},
        {0f, -1.4f, 2.4f},
        {0.4f, 0f, 2.55f},
        {0.4f, -0.224f, 2.55f},
        {0.224f, -0.4f, 2.55f},
        {0f, -0.4f, 2.55f},
        {1.3f, 0f, 2.55f},
        {1.3f, -0.728f, 2.55f},
        {0.728f, -1.3f, 2.55f},
        {0f, -1.3f, 2.55f},
        {1.3f, 0f, 2.4f},
        {1.3f, -0.728f, 2.4f},
        {0.728f, -1.3f, 2.4f},
        {0f, -1.3f, 2.4f},
        {0f, 0f, 0f},
        {1.425f, -0.798f, 0f},
        {1.5f, 0f, 0.075f},
        {1.425f, 0f, 0f},
        {0.798f, -1.425f, 0f},
        {0f, -1.5f, 0.075f},
        {0f, -1.425f, 0f},
        {1.5f, -0.84f, 0.075f},
        {0.84f, -1.5f, 0.075f}
    };
    // Since glMap2f expects a packed array of floats, we must convert
    // from a 3-dimensional array to a 1-dimensional array
    private static final float[] teapotTex = {
        0, 0, 1, 0, 0, 1, 1, 1
    };

    private static void teapot(int grid, double scale, int type, boolean backCompatible) {
        // As mentioned above, glMap2f expects a packed array of floats
        float[] p = new float[4 * 4 * 3];
        float[] q = new float[4 * 4 * 3];
        float[] r = new float[4 * 4 * 3];
        float[] s = new float[4 * 4 * 3];
        int i, j, k, l;

        glPushAttrib(GL_ENABLE_BIT | GL_EVAL_BIT | GL_POLYGON_BIT);
        glEnable(GL_AUTO_NORMAL);
        glEnable(GL_MAP2_VERTEX_3);
        glEnable(GL_MAP2_TEXTURE_COORD_2);
        glPushMatrix();
        if (!backCompatible) {
            // The time has come to have the teapot no longer be inside out
            glFrontFace(GL_CW);
            glScaled(0.5 * scale, 0.5 * scale, 0.5 * scale);
        } else {
            // We want the teapot in it's backward compatible position and
            // orientation
            glRotatef(270.0f, 1, 0, 0);
            glScalef((float) (0.5 * scale),
                    (float) (0.5 * scale),
                    (float) (0.5 * scale));
            glTranslatef(0.0f, 0.0f, -1.5f);
        }
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 4; j++) {
                for (k = 0; k < 4; k++) {
                    for (l = 0; l < 3; l++) {
                        p[(j * 4 + k) * 3 + l] = teapotCPData[teapotPatchData[i][j * 4 + k]][l];
                        q[(j * 4 + k) * 3 + l] =
                                teapotCPData[teapotPatchData[i][j * 4 + (3 - k)]][l];
                        if (l == 1) {
                            q[(j * 4 + k) * 3 + l] *= -1.0;
                        }
                        if (i < 6) {
                            r[(j * 4 + k) * 3 + l] =
                                    teapotCPData[teapotPatchData[i][j * 4 + (3 - k)]][l];
                            if (l == 0) {
                                r[(j * 4 + k) * 3 + l] *= -1.0;
                            }
                            s[(j * 4 + k) * 3 + l] = teapotCPData[teapotPatchData[i][j * 4 + k]][l];
                            if (l == 0) {
                                s[(j * 4 + k) * 3 + l] *= -1.0;
                            }
                            if (l == 1) {
                                s[(j * 4 + k) * 3 + l] *= -1.0;
                            }
                        }
                    }
                }
            }

            glMap2f(GL_MAP2_TEXTURE_COORD_2, 0.0f, 1.0f, 2, 2, 0.0f, 1.0f, 4, 2, BufferUtils2.toBuffer(teapotTex));
            glMap2f(GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, BufferUtils2.toBuffer(p));
            glMapGrid2f(grid, 0.0f, 1.0f, grid, 0.0f, 1.0f);
            evaluateTeapotMesh(grid, type, i, !backCompatible);
            glMap2f(GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, BufferUtils2.toBuffer(q));
            evaluateTeapotMesh(grid, type, i, !backCompatible);
            if (i < 6) {
                glMap2f(GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, BufferUtils2.toBuffer(r));
                evaluateTeapotMesh(grid, type, i, !backCompatible);
                glMap2f(GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, BufferUtils2.toBuffer(s));
                evaluateTeapotMesh(grid, type, i, !backCompatible);
            }
        }
        glPopMatrix();
        glPopAttrib();
    }

    private static void evaluateTeapotMesh(int grid, int type, int partNum, boolean repairSingularities) {
        if (repairSingularities && (partNum == 5 || partNum == 3)) {
            // Instead of using evaluators that give bad results at singularities,
            // evaluate by hand
            glPolygonMode(GL_FRONT_AND_BACK, type);
            for (int nv = 0; nv < grid; nv++) {
                if (nv == 0) {
                    // Draw a small triangle-fan to fill the hole
                    glDisable(GL_AUTO_NORMAL);
                    glNormal3f(0, 0, partNum == 3 ? 1 : -1);
                    glBegin(GL_TRIANGLE_FAN);
                    {
                        glEvalCoord2f(0, 0);
                        // Note that we draw in clock-wise order to match the evaluator
                        // method
                        for (int nu = 0; nu <= grid; nu++) {
                            glEvalCoord2f(nu / (float) grid, (1f / grid) / (float) grid);
                        }
                    }
                    glEnd();
                    glEnable(GL_AUTO_NORMAL);
                }
                // Draw the rest of the piece as an evaluated quad-strip
                glBegin(GL_QUAD_STRIP);
                {
                    // Note that we draw in clock-wise order to match the evaluator method
                    for (int nu = grid; nu >= 0; nu--) {
                        glEvalCoord2f(nu / (float) grid, (nv + 1) / (float) grid);
                        glEvalCoord2f(nu / (float) grid, Math.max(nv, 1f / grid)
                                / (float) grid);
                    }
                }
                glEnd();
            }
        } else {
            glEvalMesh2(type, 0, grid, 0, grid);
        }
    }
}
