package jToolkit4FixedPipeline.keyframeanim;

import jToolkit4FixedPipeline.common.GLUT;
import jToolkit4FixedPipeline.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: virus
 * Date: 9/18/13
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Joint {
    private static int pushMatrixCount;
    private int id;
    private Vector3f position;
    private float angle;
    private Vector3f rotation;

    private Vector3f rotationPosition;

    public Joint() {
        // default joint
    }

    public Joint(int id, Vector3f position, float angle, Vector3f rotation) {
        this.id = id;
        this.position = position;
        this.angle = angle;
        this.rotation = rotation;
    }

    public void draw (double radius, int slices, int stacks) {
        glTranslatef(rotationPosition.getX(), rotationPosition.getY(), rotationPosition.getZ());
        glTranslatef(position.getX(), position.getY(), position.getZ());
        glRotatef(angle, rotation.getX(), rotation.getY(), rotation.getZ());

        GLUT.glutWireSphere(radius, slices, stacks);
    }

    public void pushMatrix() {
        glPushMatrix();
        Joint.pushMatrixCount++;
    }

    public static void popMatrix() {
        while (Joint.pushMatrixCount > 0) {
            glPopMatrix();
            Joint.pushMatrixCount--;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getRotationPosition() {
        return rotationPosition;
    }

    public void setRotationPosition(Vector3f rotationPosition) {
        this.rotationPosition = rotationPosition;
    }
}
