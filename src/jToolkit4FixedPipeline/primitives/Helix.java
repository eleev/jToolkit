package jToolkit4FixedPipeline.primitives;

import jToolkit4FixedPipeline.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: virus
 * Date: 9/16/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Helix {

    public void draw (final float scaleFactor, final float pointSize, final float zFactor,  final Vector3f location, final float angle, final Vector3f rotation, final Vector3f color) {
        glPushMatrix(); {
            glTranslated(location.getX(), location.getY(), location.getZ());
            glRotated(angle, rotation.getX(), rotation.getY(), rotation.getZ());
            glColor3f(color.getX(), color.getY(), color.getZ());

            glPointSize(pointSize);
            glBegin(GL_POINTS); {
                float z = .0f;

                for (float i = .0f; i < (2 * Math.PI) * scaleFactor; i += .1f, z += zFactor)
                    glVertex3f((float) Math.sin(i), (float)Math.cos(i), z);

            }
            glEnd();
            glPointSize(1);
        }
        glPopMatrix();

    }

}
