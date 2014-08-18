package jToolkit4FixedPipeline.physics;

import jToolkit4FixedPipeline.vector.Vector2f;
import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Bounding circle for 2D collision detections
 *
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 22.02.13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class Circle implements BoundingRoundedVolume<Circle, Vector2f> {
    private Vector2f position;
    private float radius;

    public Circle(Vector2f position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public boolean intersects (final Circle that) {
        float[] faposThis = this.getPosition().getFloatArray();
        float[] faposThat = that.getPosition().getFloatArray();
        Vector2f posThis = new Vector2f();
        Vector2f posThat = new Vector2f();
        posThis.setFloatArray(faposThis[0], faposThis[1]);
        posThat.setFloatArray(faposThat[0], faposThat[1]);
        Vector2f distance = posThis.substract(posThat);
        float magnitude = (distance.getX() * distance.getX()) + (distance.getY() * distance.getY());
        float minDistance = this.getRadius() + that.getRadius();
        minDistance *= minDistance;
        if (magnitude < minDistance) return true;
        return false;
    }

    public boolean intersects (final AABB2d that) {
        float[] temp = that.getPosition().getFloatArray();
        Vector2f tpos = new Vector2f();
        tpos.setFloatArray(temp[0], temp[1]);

        Vector2f center = tpos.substract(this.getPosition());
        Vector2f closestPointOnTheAABB = new Vector2f();

        if (center.getX() < -that.getHeight()) {
            closestPointOnTheAABB.setX(-that.getHeight());
        } else if (center.getX() > that.getHeight()) {
            closestPointOnTheAABB.setX(that.getHeight());
        } else {
            closestPointOnTheAABB.setX(center.getX());
        }

        if (center.getY() < -that.getWidth()) {
            closestPointOnTheAABB.setY(-that.getWidth());
        } else if (center.getY() > that.getWidth()) {
            closestPointOnTheAABB.setY(that.getWidth());
        } else {
            closestPointOnTheAABB.setY(center.getY());
        }

        Vector2f distance = center.substract(closestPointOnTheAABB);
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY())) < (this.getRadius() * this.getRadius())) {
            return true;
        }

        return false;
    }

    public boolean intersects (final AABB3d that) {
        float[] temp = that.getPosition().getFloatArray();
        Vector3f tpos = new Vector3f();
        tpos.setFloatArray(temp[0], temp[1], temp[2]);

        Vector3f center = tpos.substract(new Vector3f(this.getPosition().getX(), this.getPosition().getY(), .0f));
        Vector3f closestPointOnTheAABB = new Vector3f();

        if (center.getX() < -that.getHeight()) {
            closestPointOnTheAABB.setX(-that.getHeight());
        } else if (center.getX() > that.getHeight()) {
            closestPointOnTheAABB.setX(that.getHeight());
        } else {
            closestPointOnTheAABB.setX(center.getX());
        }

        if (center.getY() < -that.getWidth()) {
            closestPointOnTheAABB.setY(-that.getWidth());
        } else if (center.getY() > that.getWidth()) {
            closestPointOnTheAABB.setY(that.getWidth());
        } else {
            closestPointOnTheAABB.setY(center.getY());
        }

        Vector3f distance = center.substract(closestPointOnTheAABB);
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY()) + (distance.getZ() * distance.getZ())) < (this.getRadius() * this.getRadius())) {
            return true;
        }

        return false;
    }

    @Deprecated
    public boolean intersects (final Sphere that) {
        // has not completed yet
        return false;
    }

    @Deprecated
    @Override
    public void drawSolidBV () {
        // has not completed yet
    }

    public void drawWireBV () {
        glPushMatrix();{
            glTranslatef(position.getX(), position.getY(), .0f);
            float temp = 0.0f;
            glColor3f(1.0f, 1.0f, 1.0f);

            glBegin(GL_LINE_STRIP);{
                for (float x = 0.0f; x < 360; x += 1.5f) {
                    temp = (float)(2.0f * Math.PI) * x / 180.0f;
                    glVertex2d(radius * Math.sin(temp), radius * Math.cos(temp));
                }
            }
            glEnd();
        }
        glPopMatrix();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "position=" + position +
                ", radius=" + radius +
                '}';
    }
}
