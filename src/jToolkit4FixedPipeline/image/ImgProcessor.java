package jToolkit4FixedPipeline.image;

import jToolkit4FixedPipeline.common.BufferUtils2;
import org.lwjgl.opengl.ARBImaging;
import org.lwjgl.util.vector.Vector3f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 29.01.13
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class ImgProcessor {
    public enum ImageType {RGB, RGBA};
    public enum ColorIntensityLevel {MIN, MID, MAX};

    public void channelScaling (final float r, final float g, final float b) {
        glPixelTransferf(GL_RED_SCALE, r);
        glPixelTransferf(GL_GREEN_SCALE, g);
        glPixelTransferf(GL_BLUE_SCALE, b);
    }

    public void channelScaling (final float r, final float g, final float b, final float a) {
        glPixelTransferf(GL_RED_SCALE, r);
        glPixelTransferf(GL_GREEN_SCALE, g);
        glPixelTransferf(GL_BLUE_SCALE, b);
        glPixelTransferf(GL_ALPHA_SCALE, a);
    }

    public void invertImageChannels (final int invertIntensity, final boolean invertR, final boolean invertG, final boolean invertB) {
        FloatBuffer invertedMap = null;

        if (invertB || invertG || invertR) {
            glPixelTransferf(GL_MAP_COLOR, GL_TRUE);
            invertedMap = getInvertMap(invertIntensity);
        }
        if (invertR) {
            glPixelMap(GL_PIXEL_MAP_R_TO_R, invertedMap);
        }
        if (invertG) {
            glPixelMap(GL_PIXEL_MAP_G_TO_G, invertedMap);
        }
        if (invertB) {
            glPixelMap(GL_PIXEL_MAP_B_TO_B, invertedMap);
        }
    }

    public void invertImageChannels (final int invertIntensity, final boolean invertR, final boolean invertG, final boolean invertB, final boolean invertA) {
        FloatBuffer invertedMap = null;

        if (invertA || invertB || invertG || invertR) {
            glPixelTransferf(GL_MAP_COLOR, GL_TRUE);
            invertedMap = getInvertMap(invertIntensity);
        }
        if (invertR) {
            glPixelMap(GL_PIXEL_MAP_R_TO_R, invertedMap);
        }
        if (invertG) {
            glPixelMap(GL_PIXEL_MAP_G_TO_G, invertedMap);
        }
        if (invertB) {
            glPixelMap(GL_PIXEL_MAP_B_TO_B, invertedMap);
        }
        if (invertA) {
            glPixelMap(GL_PIXEL_MAP_A_TO_A, invertedMap);
        }
    }

    public void scaleColorMatrix (final Vector3f scale) {
        glMatrixMode(GL_COLOR);
        glLoadIdentity();
        glScalef(scale.getX(), scale.getY(), scale.getZ());
        glMatrixMode(GL_MODELVIEW);
    }

    public void loadColorMatrx (final FloatBuffer fMatrix) {
        glMatrixMode(GL_COLOR);
        glLoadIdentity();
        glLoadMatrix(fMatrix);
        glMatrixMode(GL_MODELVIEW);
    }

    public FloatBuffer getBlackAndWhiteEffect () {
        return BufferUtils2.toBuffer(new float[]{0.30f, 0.30f, .30f, 0f, .59f, .59f, .59f, .0f, .11f, .11f, .11f, .0f, .0f, .0f, .0f, 1.0f});
    }

    public FloatBuffer getSharpenEffect () {
        return BufferUtils2.toBuffer(new float[]{.0f, -1.f, .0f, .0f, -1.f, 5.f, -1.f, .0f, 0.f, -1.f, 0.f, 0.f, .0f, .0f, .0f, 1.0f});
    }

    public FloatBuffer getEmbossEffect () {
        return BufferUtils2.toBuffer(new float[]{2.0f, .0f, .0f, .0f, 0.0f, -1.0f, .0f, .0f, 0.f, 0.0f, -1.f, .0f, 0.0f, .0f, .0f, -1.0f});
    }

    public void invertImageColorTable (final ImageType type, final ColorIntensityLevel level) {
        int bytes = 0;

        if (level.equals(ColorIntensityLevel.MIN)) {
            bytes = 128;
        } else if (level.equals(ColorIntensityLevel.MID)) {
            bytes = 256;
        }  else if (level.equals(ColorIntensityLevel.MAX)) {
            bytes = 512;
        }
        glEnable(ARBImaging.GL_COLOR_TABLE);

        if (type.equals(ImageType.RGB)) {
            ARBImaging.glColorTable(ARBImaging.GL_COLOR_TABLE, GL_RGB, bytes, GL_RGB, GL_UNSIGNED_BYTE, getColorTable(type, level));
        } else if (type.equals(ImageType.RGBA)) {
            ARBImaging.glColorTable(ARBImaging.GL_COLOR_TABLE, GL_RGBA, bytes, GL_RGBA, GL_UNSIGNED_BYTE, getColorTable(type, level));
        }
    }

    private static ByteBuffer getColorTable (final ImageType type, final ColorIntensityLevel level) {
        int bytes = 0;

        if (level.equals(ColorIntensityLevel.MIN)) {
            bytes = 128;
        } else if (level.equals(ColorIntensityLevel.MID)) {
            bytes = 256;
        }  else if (level.equals(ColorIntensityLevel.MAX)) {
            bytes = 512;
        }
        int colorRange = 0;

        if (type.equals(ImageType.RGB)) {
            colorRange = 3;
        } else if (type.equals(ImageType.RGBA)) {
            colorRange = 4;
        }
        colorRange *= bytes;

        byte[] data = new byte[colorRange];

        for (int i = 0; i < colorRange; i++) {
            data[i] = (byte) (colorRange - i);
        }
        return BufferUtils2.toBuffer(data);
    }

    private FloatBuffer getInvertMap (final int invertIntensity) {
        final int COLOR_RANGE = 256;
        float[] data = new float[COLOR_RANGE];

        for (int i = 0; i < COLOR_RANGE; i++) {
            data[i] = 1.0f - (1.0f / invertIntensity * (float)i);
        }
        return BufferUtils2.toBuffer(data);
    }

}
