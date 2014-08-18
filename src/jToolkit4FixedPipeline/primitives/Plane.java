package jToolkit4FixedPipeline.primitives;

import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Astemir Yeleev
 */
public class Plane {
    private float size;
    private Vector3f translation;
    private Vector3f scaling;
    private Vector3f rotation;
    private float angleOfRotation;
    private Vector3f color;

    public Plane() {
        size = 10;
        translation = new Vector3f();
        scaling = new Vector3f().makeIdentity();
        rotation = new Vector3f();
        angleOfRotation = 0.0f;
        color = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public Plane (float size, float angleOfRotation, Vector3f rotation, Vector3f translation, Vector3f scaling, Vector3f color) {
        this.size = size;
        this.angleOfRotation = angleOfRotation;
        this.rotation = rotation;
        this.translation = translation;
        this.scaling = scaling;
        this.color = color;
    }

    public void draw () {
        glPushMatrix();
        {
            glTranslated(translation.getX(), translation.getY(), translation.getZ());
            glScalef(scaling.getX(), scaling.getY(), scaling.getY());
            glRotatef(angleOfRotation, rotation.getX(), rotation.getY(), rotation.getZ());
            glColor3f(color.getX(), color.getY(), color.getZ());
            glEnable(GL_NORMALIZE);
            glDisable(GL_CULL_FACE);
            glDisable(GL_LIGHTING);

            glBegin(GL_QUADS);
            {
                glNormal3f(0.0f, 1.0f, 0.0f);
                glVertex3f(-size, 0.0f, size);
                glVertex3f(-size, 0.0f, -size);
                glVertex3f(size, 0.0f, -size);
                glVertex3f(size, 0.0f, size);
            }
            glEnd();
            glEnable(GL_CULL_FACE);
            glEnable(GL_LIGHTING);
        }
        glPopMatrix();
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public Vector3f getScaling() {
        return scaling;
    }

    public void setScaling(Vector3f scaling) {
        this.scaling = scaling;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
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


