package jToolkit4FixedPipeline.primitives;

import jToolkit4FixedPipeline.common.GLUT;
import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Astemir Yeleev
 */
public class Axis {
    public void drawUnitAxes(Vector3f translate, Vector3f sphere, float axisRadius, float axisHeight, float arrowRadius, float arrowHeight, int slises, int stacks) {
        glPushMatrix();
        {
            glTranslatef(translate.getX(), translate.getY(), translate.getZ());
            
            glColor3f(0.0f, 0.0f, 1.0f);
            GLUT.glutSolidCylinder(axisRadius, axisHeight, slises, stacks);
            glPushMatrix();
            glTranslatef(0.0f, 0.0f, 1.0f);
//            GLUT.glutSolidCylinder(arrowRadius, arrowHeight, slises, stacks);
            glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
//            GLUT.glutSolidCylinder(axisRadius, arrowRadius, slises, stacks);
            glPopMatrix();

            glColor3f(1.0f, 0.0f, 0.0f);
            glPushMatrix();
            glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GLUT.glutSolidCylinder(axisRadius, axisHeight, slises, stacks);
            glPushMatrix();
            glTranslatef(0.0f, 0.0f, 1.0f);
//            GLUT.glutSolidCylinder(arrowRadius, arrowHeight, slises, stacks);
            glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
//            GLUT.glutSolidCylinder(axisRadius, arrowRadius, slises, stacks);
            glPopMatrix();
            glPopMatrix();

            glColor3f(0.0f, 1.0f, 0.0f);
            glPushMatrix();
            glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            GLUT.glutSolidCylinder(axisRadius, axisHeight, slises, stacks);
            glPushMatrix();
            glTranslatef(0.0f, 0.0f, 1.0f);
//            GLUT.glutSolidCylinder(arrowRadius, arrowHeight, slises, stacks);
            glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
//            GLUT.glutSolidCylinder(axisRadius, arrowRadius, slises, stacks);
            
            glPopMatrix();
            glPopMatrix();
            
            glColor3f(1.0f, 1.0f, 1.0f);
            GLUT.glutSolidSphere((double) sphere.getX(), (int) sphere.getY(), (int) sphere.getZ());
        }
        glPushMatrix();
    }


}
