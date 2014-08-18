package jToolkit4FixedPipeline.physics;

import jToolkit4FixedPipeline.common.GLUT;
import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Bounding sphere for 3D collision detections
 *
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 22.02.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class Sphere implements BoundingRoundedVolume<Sphere, Vector3f> {
    private Vector3f position;
    private float radius;

    public Sphere() {
        // do nothing
    }

    public Sphere(Vector3f position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public boolean intersects (final Sphere that) {
        final float[] faposThis = this.position.getFloatArray();
        final float[] faposThat = that.getPosition().getFloatArray();
        Vector3f posThis = new Vector3f();
        Vector3f posThat = new Vector3f();
        posThis.setFloatArray(faposThis[0], faposThis[1], faposThis[2]);
        posThat.setFloatArray(faposThat[0], faposThat[1], faposThat[2]);
        Vector3f distance = posThis.substract(posThat);
        final float magnitude = (distance.getX() * distance.getX()) + (distance.getY() * distance.getY()) + (distance.getZ() * distance.getZ());
        float minDistance = this.getRadius() + that.getRadius();
        minDistance *= minDistance;
        if (magnitude < minDistance) return true;
        return false;
    }

    public boolean intersects (final AABB3d that) {
        float[] temp = position.getFloatArray();
        Vector3f tpos = new Vector3f();
        tpos.setFloatArray(temp[0], temp[1], temp[2]);

        Vector3f center = tpos.substract(that.getPosition());

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

        if (center.getZ() < -that.getDepth()) {
            closestPointOnTheAABB.setZ(-that.getDepth());
        } else if (center.getZ() > that.getDepth()) {
            closestPointOnTheAABB.setZ(that.getDepth());
        } else {
            closestPointOnTheAABB.setZ(center.getZ());
        }

        Vector3f distance = center.substract(closestPointOnTheAABB);
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY()) + (distance.getZ() * distance.getZ())) < (this.radius * this.radius)) {
            return true;
        }

        return false;
    }

    public boolean intersects (final AABB2d that) {
        float[] temp = that.getPosition().getFloatArray();
        Vector3f tpos = new Vector3f();
        tpos.setFloatArray(temp[0], temp[1], .0f);

        Vector3f center = tpos.substract(this.getPosition());
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
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY()) + (distance.getZ() * distance.getZ())) < (this.getRadius()* this.getRadius())) {
            return true;
        }

        return false;
    }

    @Deprecated
    public boolean intersects (final Circle that) {
        // has not completed yet

        return false;
    }

    public void drawSolidBV () {
        glPushMatrix();
        glColor3f(.1f, .1f, .1f);
        glTranslatef(position.getX(), position.getY(), position.getZ());
        GLUT.glutSolidSphere(radius, 25, 25);
        glPopMatrix();
    }

    public void drawWireBV () {
        glPushMatrix();
        glTranslatef(position.getX(), position.getY(), position.getZ());
        GLUT.glutWireSphere(radius, 25, 25);
        glPopMatrix();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
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
        return "Sphere{" +
                "position=" + position +
                ", radius=" + radius +
                '}';
    }
}
