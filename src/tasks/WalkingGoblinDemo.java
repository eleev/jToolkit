package tasks;

import jToolkit4FixedPipeline.keyframeanim.Character;
import jToolkit4FixedPipeline.primitives.Plane;
import jToolkit4FixedPipeline.primitives.Surface;
import jToolkit4FixedPipeline.vector.Vector3f;
import jToolkit4FixedPipeline.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 9/24/13
 * Time: 1:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class WalkingGoblinDemo {
    private jToolkit4FixedPipeline.keyframeanim.Character goblin;
    private Surface floor;
    private Surface wall;
    private Plane floorCover;
    private Plane wallCover;
    private float rotationAngle;


    public WalkingGoblinDemo () {

        floor = new Surface();
        wall = new Surface();

        floorCover = new Plane(10, 0.0f, new Vector3f(), new Vector3f(0, -0.001f, 0), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));
        wallCover = new Plane(10, 90.0f, new Vector3f(0, 0, 1), new Vector3f(-10.001f, 8, 0), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));

        floor.drawChessBoard(100, 10, 16, .0f, new Vector3f(), new Vector3f(), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));
        wall.drawChessBoard(101, 10, 16, -90.0f, new Vector3f(0, 0, 1), new Vector3f(-10, 8, 0), new Vector3f().makeIdentity(), new Vector3f(1, 1, 1));

        goblin = new Character();
        goblin.setPosition(new Vector3f().makeNull());
        goblin.setRotationAngle(.0f);
        goblin.setRotation(new Vector3f().makeNull());
        goblin.setScale(new Vector3f(2, 2, 2));
        goblin.setColor(new Vector4f(1, 1, 1, 1));


        goblin.createCharacter();

    }

    public void draw () {
        // outside this plane reflected objects won't be rendered
        glColorMask(false, false, false, false);
        glDisable(GL_DEPTH_TEST);

        glEnable(GL_STENCIL_TEST);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);

        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilMask(0xFF);
        glClear(GL_STENCIL_BUFFER_BIT);

        glPushMatrix(); {
            glCallList(100);
            glCallList(101);
        }
        glPopMatrix();

        glColorMask(true, true, true, true);
        glEnable(GL_DEPTH_TEST);

        glStencilMask(0x00);
        glStencilFunc(GL_EQUAL, 1, 0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);


        // drawing reflected object
        glPushMatrix(); {
            glEnable(GL_TEXTURE_2D);
            glBlendFunc(GL_ONE, GL_ONE);

            goblin.setPosition(new Vector3f(.0f, -6.0f, .0f));
            goblin.setScale(new Vector3f(2, -2, 2));
            goblin.setRotationAngle(rotationAngle);
            goblin.setRotation(new Vector3f(0, 1, 0));
            goblin.setColor(new Vector4f(1, 1, 1, 1));

            goblin.draw();

            goblin.setPosition(new Vector3f(-12.0f, 6.0f, .0f));
            goblin.setScale(new Vector3f(-2f, 2f, 2f));

            goblin.draw();

            glDisable(GL_TEXTURE_2D);
        }
        glPopMatrix();

        glDisable(GL_STENCIL_TEST);

        // drawing blended floor
        glDisable(GL_LIGHTING);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glPushMatrix(); {
            glCallList(100);
            glCallList(101);
        }
        glPopMatrix();

        glPushMatrix(); {
            floorCover.draw();
            wallCover.draw();
        }
        glPopMatrix();

        glDisable(GL_BLEND);
        glEnable(GL_LIGHTING);

        // drawing actual object
        glPushMatrix(); {
            goblin.setPosition(new Vector3f(.0f, 6.0f, .0f));
            goblin.setScale(new Vector3f(2, 2, 2));
            goblin.setRotationAngle(rotationAngle);
            goblin.setRotation(new Vector3f(0, 1, 0));

            goblin.setColor(new Vector4f(1, 1, 1, 1));

            goblin.draw();
        }
        glPopMatrix();

        goblin.motionListener();
        rotationAngle += .5f;

    }
}
