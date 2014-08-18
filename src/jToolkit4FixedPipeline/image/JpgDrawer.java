package jToolkit4FixedPipeline.image;

import jToolkit4FixedPipeline.common.BufferUtils2;
import jToolkit4FixedPipeline.image.reader.ImageLoader;
import org.lwjgl.opengl.GL12;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Virus
 * Date: 29.01.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class JpgDrawer {
    public static int w = 16;
    public static int h = 16;
    private final int ZERO = 0;
    private final int ONE = 1;
    private ImageLoader iLoader;
    private ByteBuffer bBuffer;
    private final String WIDE = "WIDE";
    private boolean hasAlpha;
    private final int TEXTURE_INDEX;

    public JpgDrawer(final String pathtoFile, final ASPECT_RATIO ratio, final int textureIndex) {
        iLoader = new ImageLoader(pathtoFile);
        bBuffer = iLoader.bufferedImageToByteBuffer();
        hasAlpha = iLoader.hasAlpha();
        TEXTURE_INDEX = textureIndex;

        if (ratio.name().equals(WIDE)) {
            h = 9;
        }
    }

    public void draw () {
        glPushMatrix(); {
            glTranslatef(ZERO, w, ZERO);
            glRotatef(180.0f, ONE, ZERO, ZERO);

            glEnable(GL_TEXTURE_2D);
            glDisable(GL_CULL_FACE);
            glBindTexture(GL_TEXTURE_2D, TEXTURE_INDEX);
            if (hasAlpha) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, iLoader.getImageWidth(), iLoader.getImageHeight(), 0, GL12.GL_BGRA, GL_UNSIGNED_BYTE, bBuffer);
            } else {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, iLoader.getImageWidth(), iLoader.getImageHeight(), 0, GL12.GL_BGR, GL_UNSIGNED_BYTE, bBuffer);
            }

            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);

//            FloatBuffer fbuffer = BufferUtils2.toBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
//            glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_BLEND);
//            glTexEnv(GL_TEXTURE_ENV, GL_TEXTURE_ENV_COLOR, fbuffer);
            glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

            glBegin(GL_QUADS); {
                glTexCoord2f(ZERO, ZERO); glVertex2f(ZERO, ZERO);
                glTexCoord2f(ZERO, ONE); glVertex2f(ZERO, h);
                glTexCoord2f(ONE, ONE); glVertex2f(w, h);
                glTexCoord2f(ONE, ZERO); glVertex2f(w, ZERO);
            }
            glEnd();

            glEnable(GL_CULL_FACE);
            glDisable(GL_TEXTURE_2D);
        }
        glPopMatrix();
    }

    private FloatBuffer getToonTable () {
        return BufferUtils2.toBuffer(new float[]{0, 32, 0, 0, 64, 0, 0, 128, 0, 0, 198, 0});
    }

}
