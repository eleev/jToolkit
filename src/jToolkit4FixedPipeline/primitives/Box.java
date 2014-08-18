package jToolkit4FixedPipeline.primitives;

import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Astemir Yeleev
 */
public class Box {
    private float halfOfSze;
    private final float DEFAULT_SIZE = 1;
    private float angleofRotation;
    private Vector3f translate;
    private Vector3f rotate;
    private Vector3f scale;
    private Vector3f color;

    public Box() {
        halfOfSze = DEFAULT_SIZE;
        halfOfSze /= 2;
        rotate = new Vector3f();
        translate = new Vector3f();
        scale = new Vector3f().makeIdentity();
    }

    public Box(float size, float angleofRotation, Vector3f rotate, Vector3f translate, Vector3f scale, Vector3f color) {
        halfOfSze = size / 2;
        this.angleofRotation = angleofRotation;
        this.rotate = rotate;
        this.translate = translate;
        this.scale = scale;
        this.color = color;
    }
    
    public void draw () {
        glPushMatrix();
        {
            glTranslatef(translate.getX(), translate.getY(), translate.getZ());
            glRotatef(angleofRotation, rotate.getX(), rotate.getY(), rotate.getZ());
            glScalef(scale.getX(), scale.getY(), scale.getZ());
            glEnable(GL_NORMALIZE);
            glDisable(GL_CULL_FACE);
            glColor4f(color.getX(), color.getY(), color.getZ(), 0.5f);

            glBegin(GL_QUADS);
            {
                glNormal3f(0.0f, 0.0f, 1.0f);
                glVertex3f(-halfOfSze, -halfOfSze, halfOfSze);
                glVertex3f(-halfOfSze, halfOfSze, halfOfSze);
                glVertex3f(halfOfSze, halfOfSze, halfOfSze);
                glVertex3f(halfOfSze, -halfOfSze, halfOfSze);
                
                glNormal3f(1.0f, 0.0f, 0.0f);
                glVertex3f(halfOfSze, -halfOfSze, halfOfSze);
                glVertex3f(halfOfSze, halfOfSze, halfOfSze);
                glVertex3f(halfOfSze, halfOfSze, -halfOfSze);
                glVertex3f(halfOfSze, -halfOfSze,-halfOfSze);
                
                glNormal3f(0.0f, 0.0f, -1.0f);
                glVertex3f(halfOfSze, -halfOfSze,-halfOfSze);
                glVertex3f(halfOfSze, halfOfSze, -halfOfSze);
                glVertex3f(-halfOfSze, halfOfSze, -halfOfSze);
                glVertex3f(-halfOfSze, -halfOfSze,-halfOfSze);
                
                glNormal3f(-1.0f, 0.0f, 0.0f);
                glVertex3f(-halfOfSze, -halfOfSze,-halfOfSze);
                glVertex3f(-halfOfSze, halfOfSze, -halfOfSze);
                glVertex3f(-halfOfSze, halfOfSze, halfOfSze);
                glVertex3f(-halfOfSze, -halfOfSze, halfOfSze);
                
                glNormal3f(0.0f, 1.0f, 0.0f);
                glVertex3f(halfOfSze, halfOfSze, halfOfSze);
                glVertex3f(-halfOfSze, halfOfSze, halfOfSze);
                glVertex3f(-halfOfSze, halfOfSze, -halfOfSze);
                glVertex3f(halfOfSze, halfOfSze, -halfOfSze);
                
                glNormal3f(0.0f, -1.0f, 0.0f);
                glVertex3f(-halfOfSze, -halfOfSze, halfOfSze);
                glVertex3f(halfOfSze, -halfOfSze, halfOfSze);
                glVertex3f(halfOfSze, -halfOfSze,-halfOfSze);
                glVertex3f(-halfOfSze, -halfOfSze,-halfOfSze);
            }
            glEnd();
            glEnable(GL_CULL_FACE);
        }
        glPopMatrix();
    }

    public void setColor(final Vector3f color) {
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getHalfOfSze() {
        return halfOfSze;
    }

    public void setHalfOfSze(float halfOfSze) {
        this.halfOfSze = halfOfSze;
    }

    public float getAngleofRotation() {
        return angleofRotation;
    }

    public void setAngleofRotation(float angleofRotation) {
        this.angleofRotation = angleofRotation;
    }

    public Vector3f getTranslate() {
        return translate;
    }

    public void setTranslate(Vector3f translate) {
        this.translate = translate;
    }

    public Vector3f getRotate() {
        return rotate;
    }

    public void setRotate(Vector3f rotate) {
        this.rotate = rotate;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
