package jToolkit4FixedPipeline.keyframeanim;

import jToolkit4FixedPipeline.vector.Vector3f;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 9/18/13
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Skelet {
    private ArrayList<Joint> joints;

    public Skelet() {
        this.joints = new ArrayList<Joint>();
    }

    public void rotateJoint (int index, float angle, Vector3f rotation) {
        if (index > joints.size() - 1 || index < 0) {
            Logger.getLogger(Skelet.class.getCanonicalName()).log(Level.WARNING, "Index of bounds exception");
            return;
        }
        Joint root = joints.get(index);
        root.setAngle(angle);
        root.setRotation(rotation);

        int childNodeIndex= index + 1;
        Vector3f beginOfLine = new Vector3f(root.getPosition());
        Vector3f endOfLine;

        if (childNodeIndex  < joints.size()) {

            Joint child = joints.get(childNodeIndex);
            child.setAngle(angle);
            child.setRotation(rotation);
            child.setRotationPosition(root.getPosition());

            endOfLine = new Vector3f(child.getPosition());
            drawBone(beginOfLine, endOfLine);
        }


    }

    public void beginAddingJoints (Joint joint) {
        joint.pushMatrix();
        joints.add(joint);
    }

    public void endAddingJoints () {
        Joint.popMatrix();
    }

    public ArrayList<Joint> getJoints() {
        return joints;
    }

    private void drawBone (Vector3f firstPoint, Vector3f secondPoint) {

        glColor3f(1, 1, 0);
        glBegin(GL_LINES); {
            glVertex3f(firstPoint.getX(), firstPoint.getY(), firstPoint.getZ());
            glVertex3f(secondPoint.getX(), secondPoint.getY(), secondPoint.getZ());
        }
        glEnd();
    }

}

