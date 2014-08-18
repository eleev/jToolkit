package jToolkit4FixedPipeline.primitives;

import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Needs some improvements
 * @author Astemir Eleev
 */
public class Sphere {
    private float size;
    private Vector3f translate;
    private Vector3f scale;
    private Vector3f rotate;
    private float angleOfRotation;
    private Vector3f color;

    public Sphere () {
        size = 10;
        angleOfRotation = 0.0f;
        translate = new Vector3f();
        scale = new Vector3f().makeIdentity();
        rotate = new Vector3f();
        color = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public Sphere(int size, float angleOfRotation, Vector3f rotate, Vector3f translate, Vector3f scale, Vector3f color) {
        this.size = size;
        this.angleOfRotation = angleOfRotation;
        this.rotate = rotate;
        this.translate = translate;
        this.scale = scale;
        this.color = color;

    }

    public void draw () {
        float x = 0, y = 0, z = 0, X = (float) -Math.PI, Y = 0.0f, Z = 0.0f;
        Vector3f first = new Vector3f();
        Vector3f second = new Vector3f();
        Vector3f thrird = new Vector3f();

        glPushMatrix();
        {
            glTranslatef(translate.getX(), translate.getY(), translate.getZ());
            glRotatef(angleOfRotation, rotate.getX(), rotate.getY(), rotate.getZ());
            glScalef(scale.getX(), scale.getY(), scale.getZ());
            glColor3f(color.getX(), color.getY(), color.getY());
            glDisable(GL_CULL_FACE);
            glLineWidth(10);

            glBegin(GL_LINE_STRIP);
            {
                while (X < 2 * Math.PI) {
                    while (Y < 2 * Math.PI) {
                        x = size * (float) Math.cos(X) * (float) Math.cos(Y);
                        y = size * (float) Math.cos(X) * (float) Math.sin(Y);
                        z = size * (float) Math.sin(X);
                        glVertex3f(x, y, z);

                        first.setX(x);
                        first.setY(y);
                        first.setZ(z);

                        x = size * (float) Math.cos(X) * (float) Math.cos(Y);
                        y = size * (float) Math.cos(X) * (float) Math.sin(Y);
                        z = size * (float) Math.sin(X);
                        glVertex3f(x, y, z);

                        second.setX(x);
                        second.setY(y);
                        second.setZ(z);

                        x = size * (float) Math.cos(X) * (float) Math.cos(Y);
                        y = size * (float) Math.cos(X) * (float) Math.sin(Y);
                        z = size * (float) Math.sin(X);
                        glVertex3f(x, y, z);
                        Y += 0.1;

                        thrird.setX(x);
                        thrird.setY(y);
                        thrird.setZ(z);

                        first = thrird.crossProduct(first, second, thrird);
                        glNormal3f(first.getX(), first.getY(), first.getZ());
                    }
                    Y = 0;
                    X += 0.1;
                }
            }
            glEnd();
            glEnable(GL_CULL_FACE);
            glLineWidth(1);
        }
        glPopMatrix();

    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Vector3f getTranslate() {
        return translate;
    }

    public void setTranslate(Vector3f translate) {
        this.translate = translate;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getRotate() {
        return rotate;
    }

    public void setRotate(Vector3f rotate) {
        this.rotate = rotate;
    }

    public float getAngleOfRotation() {
        return angleOfRotation;
    }

    public void setAngleOfRotation(float angleOfRotation) {
        this.angleOfRotation = angleOfRotation;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
