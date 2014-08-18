package tasks;

import jToolkit4FixedPipeline.primitives.Box;
import jToolkit4FixedPipeline.primitives.Plane;
import jToolkit4FixedPipeline.primitives.Surface;
import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 9/1/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class StencilBuffer {
    private float rotationAngle;
    private Box box;
    private Surface surface;
    private Surface wallSurface;
    private Plane bottonCoverSurface;
    private Plane wallCoverSurface;

    public StencilBuffer () {
        box = new Box(10, .0f, new Vector3f(), new Vector3f(), new Vector3f().makeIdentity(), new Vector3f(1, 0, 1));
        surface = new Surface();
        wallSurface = new Surface();

        bottonCoverSurface = new Plane(10, 0.0f, new Vector3f(), new Vector3f(0, -0.001f, 0), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));
        wallCoverSurface = new Plane(10, 90.0f, new Vector3f(0, 0, 1), new Vector3f(-10.001f, 8, 0), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));

        surface.drawChessBoard(2, 10, 16, .0f, new Vector3f(), new Vector3f(), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));
        wallSurface.drawChessBoard(3, 10, 16, -90.0f, new Vector3f(0, 0, 1), new Vector3f(-10, 8, 0), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));
    }

    /**
     * Simulation of reflections by stencil buffer and blending equations
     */
    public void stencilPlannarReflections () {

        // outside this plane reflected objects won't be rendered
        glColorMask(false, false, false, false);
        glDisable(GL_DEPTH_TEST);

        glEnable(GL_STENCIL_TEST);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);

        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilMask(0xFF);
        glClear(GL_STENCIL_BUFFER_BIT);

        glPushMatrix(); {
            glCallList(2);
            glCallList(3);
        }
        glPopMatrix();

        glColorMask(true, true, true, true);
        glEnable(GL_DEPTH_TEST);

        glStencilMask(0x00);
        glStencilFunc(GL_EQUAL, 1, 0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);


        // drawing reflected object
        glPushMatrix(); {
            box.setTranslate(new Vector3f(.0f, -10.0f, .0f));
            box.setScale(new Vector3f(1.0f, -1.0f, 1.0f));
            box.setColor(new Vector3f(1.0f, .0f, .0f));
            box.setAngleofRotation(rotationAngle);
            box.setRotate(new Vector3f(1,1,1));

            box.draw();

            box.setTranslate(new Vector3f(-20.0f, 10.0f, .0f));
            box.setScale(new Vector3f(-1.0f, 1.0f, 1.0f));
            box.setColor(new Vector3f(1.0f, .0f, .0f));
            box.setAngleofRotation(rotationAngle);
            box.setRotate(new Vector3f(1,1,1));

            box.draw();
        }
        glPopMatrix();

        glDisable(GL_STENCIL_TEST);

        // drawing blended floor
        glDisable(GL_LIGHTING);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glPushMatrix(); {
            glCallList(2);
            glCallList(3);
        }
        glPopMatrix();

        glPushMatrix(); {
            bottonCoverSurface.draw();
            wallCoverSurface.draw();
        }
        glPopMatrix();

        glDisable(GL_BLEND);
        glEnable(GL_LIGHTING);

        // drawing actual box
        glPushMatrix(); {
            box.setTranslate(new Vector3f(.0f, 10.0f, .0f));
            box.setScale(new Vector3f().makeIdentity());
            box.draw();
        }
        glPopMatrix();

        rotationAngle += .5f;
    }

}
