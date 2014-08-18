package tasks;

import jToolkit4FixedPipeline.primitives.Box;
import jToolkit4FixedPipeline.primitives.Surface;
import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_RETURN;
import static org.lwjgl.opengl.GL11.glAccum;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 9/1/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccumulationBuffer {
    private float angle;
    private Box box;
    private Surface surface;

    private Vector3f[] stars;

    public AccumulationBuffer () {
        box = new Box(10, .0f, new Vector3f(), new Vector3f(), new Vector3f().makeIdentity(), new Vector3f(1, 0, 0));
        surface = new Surface();
        surface.drawChessBoard(2, 20, 16, .0f, new Vector3f(), new Vector3f(), new Vector3f().makeIdentity(), new Vector3f());
    }

    /**
     * Simulation of motion blur effect by accumulation buffer
     */
    public void motionBlurEffect () {
        glCallList(2);

        float dx = .1f, dy = 10.0f, dz = .1f;
        int max = 8;

        glClear(GL_ACCUM_BUFFER_BIT);
        glAccum(GL_LOAD, 1.f);

        for (int i = 0 ; i < max; i++) {

            box.setTranslate(new Vector3f(dx * (i * i), dy, dz));
            box.draw();

            if(i == 0)
                glAccum(GL_LOAD, 1.0f);
            else
                glAccum(GL_ACCUM, 1.f / max);
        }
        glAccum(GL_RETURN, 1.f);

    }

    public void generateStars (final int starsCount) {
        stars = new Vector3f[starsCount];

        for (Vector3f star : stars) {
            float rx = (float)(-1000 + Math.random() * 1000);
            float ry = (float)(-1000 + Math.random() * 1000);
            float rz = (float)(-1000 + Math.random() * 1000);
            star = new Vector3f(rx, ry, rz);

        }


    }

    public void bluredSpace () {



    }

    @Deprecated
    public void fullScreenAntialising (final boolean isEnabled) {

        if (isEnabled) {
            glClear(GL_ACCUM_BUFFER_BIT);

            float factor = .005f;

            for (int i = 1; i < 8; i++, factor += .005f) {

                glPushMatrix(); {
                    glTranslated(.0f + factor, .0f + factor, .0f + factor);
                    glCallList(2);
                }
                glPopMatrix();

//            glPushMatrix(); {
//                box.setTranslate(new Vector3f(box.getTranslate().getX() + factor , box.getTranslate().getY() * factor, box.getTranslate().getZ() * factor));
//                box.draw();
//            }
                glPopMatrix();
                glAccum(GL_ACCUM, 1.f/8);

            }
            glAccum(GL_RETURN, 1.0f);

       } else {
           glCallList(2);



       }

    }

    @Deprecated
    public void depthOfField () {
    }

}
