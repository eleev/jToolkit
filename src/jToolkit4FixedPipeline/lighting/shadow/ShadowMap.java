package jToolkit4FixedPipeline.lighting.shadow;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.ARBShadowAmbient.GL_TEXTURE_COMPARE_FAIL_VALUE_ARB;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Astemir Eleev
 */
public class ShadowMap {
    // This represents if the clients computer has the ambient shadow extention
    private static boolean ambientShadowsAvailable = false;
    // Disable this if your computer doesn't support the FBO extension
    private static boolean useFBO = true;

    // The amount of polygon offset to use
    private static float factor = 4;
    private static float shadowTransparanceIntensity = 0.1f;
    public static int maxTextureSize;
    private static float sceneBoundingRadius = 90.0F;
    private static int shadowWidth = 512;
    private static int shadowHeight = 512;
    private static int frameBuffer;
    private static int renderBuffer;

    private static FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
    private static FloatBuffer tempBuffer = BufferUtils.createFloatBuffer(4);
    private static FloatBuffer lightModelView = BufferUtils.createFloatBuffer(16);
    private static FloatBuffer lightProjection = BufferUtils.createFloatBuffer(16);

    private static Matrix4f lightProjectionTemp = new Matrix4f();
    private static Matrix4f lightModelViewTemp = new Matrix4f();
    private static Matrix4f textureMatrix = new Matrix4f();

    public static void initShadow (final int shadowWidth, final int shadowHeight, final float shadowTransparanceIntensity, final float polyOffset, final float sceneBoundingRadius, final float[] lPosition) {
        ShadowMap.shadowWidth = shadowWidth;
        ShadowMap.shadowHeight = shadowHeight;
        ShadowMap.shadowTransparanceIntensity = shadowTransparanceIntensity;
        ShadowMap.factor = polyOffset;
        ShadowMap.sceneBoundingRadius = sceneBoundingRadius;
        lightPosition.put(lPosition);
        lightPosition.flip();
    }

    /**
     * Sets up the OpenGL states.
     */
    public static void initGLForShadowMapping() {
        int maxRenderbufferSize = glGetInteger(GL_MAX_RENDERBUFFER_SIZE_EXT);
        StringBuilder loggerERROR = new StringBuilder();
        StringBuilder loggerINFO = new StringBuilder();

        if (!GLContext.getCapabilities().OpenGL14 && GLContext.getCapabilities().GL_ARB_shadow) {
            loggerERROR.append("\nCan't create shadows at all. Requires OpenGL 1.4 or the GL_ARB_shadow extension");
            System.exit(0);
        }
        if (GLContext.getCapabilities().GL_ARB_shadow_ambient) {
            ambientShadowsAvailable = true;
            loggerINFO.append("\nAmbient Shadows are available");
        } else {
            loggerERROR.append("\nGL_ARB_shadow_ambient extension not available.\n An extra rendering pass will be required.");
        }
        if (GLContext.getCapabilities().OpenGL20|| GLContext.getCapabilities().GL_EXT_framebuffer_object) {
            loggerINFO.append("\nHigher quality shadows are available");
        }
        maxTextureSize = glGetInteger(GL_MAX_TEXTURE_SIZE);

        // Check to see if the maximum texture size is bigger than 8192. Performance drops too much if it much bigger than that.
        if (maxTextureSize > 8192) {
            maxTextureSize = 8192;
            if (maxRenderbufferSize < maxTextureSize) {
                maxTextureSize = maxRenderbufferSize;
            }
        }

        if ((shadowWidth > maxTextureSize || shadowHeight > maxRenderbufferSize) ||
                (shadowWidth > maxRenderbufferSize && shadowHeight > maxRenderbufferSize)) {
            shadowHeight = maxRenderbufferSize;
            shadowWidth = maxRenderbufferSize;
        }

        if (useFBO) {
            shadowWidth = maxTextureSize;
            shadowHeight = maxTextureSize;
        }
        glClearColor(0.0F, 0.0F, 0.0F, 1.0F);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glPolygonOffset(factor, 0.0F);

        glShadeModel(GL_SMOOTH);
        glEnable(GL_LIGHTING);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_NORMALIZE);
//        glEnable(GL_LIGHT0);

        // Setup some texture states
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_DEPTH_TEXTURE_MODE, GL_INTENSITY);

        // If ambient shadows are availible then we can skip a rendering pass.
        if (ambientShadowsAvailable) {
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FAIL_VALUE_ARB, shadowTransparanceIntensity);
        }
        glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
        glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
        glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
        glTexGeni(GL_Q, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);

        // If we are using a FBO, we need to setup the framebuffer.
        if (useFBO) {
            frameBuffer = glGenFramebuffersEXT();
            glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBuffer);
            renderBuffer = glGenRenderbuffersEXT();
            glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, renderBuffer);
            glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT32, maxTextureSize, maxTextureSize);
            glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, renderBuffer);

            glDrawBuffer(GL_NONE);
            glReadBuffer(GL_NONE);

            int FBOStatus = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);

            if (FBOStatus != GL_FRAMEBUFFER_COMPLETE_EXT) {
                loggerERROR.append("\nFramebuffer error");
            }
            glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        }

        loggerINFO.append("\nMaximum texture size: ").append(maxTextureSize).append("\nMaximum renderbuffer size: ").append(maxRenderbufferSize);
        Logger.getLogger(ShadowMap.class.getName()).log(Level.INFO, loggerINFO.toString());

        if (loggerERROR.length() != 0) {
            Logger.getLogger(ShadowMap.class.getName()).log(Level.WARNING, loggerERROR.toString());
        }
    }


    public static void startGenerate () {
        float lightToSceneDistance, nearPlane, fieldOfView;

        lightToSceneDistance = (float) Math.sqrt(lightPosition.get(0) * lightPosition.get(0) + lightPosition.get(1) *
                                                 lightPosition.get(1) + lightPosition.get(2) * lightPosition.get(2));
        nearPlane = lightToSceneDistance - sceneBoundingRadius;
        fieldOfView = (float) Math.toDegrees(2.0F * Math.atan(sceneBoundingRadius / lightToSceneDistance));

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fieldOfView, 1.0F, nearPlane, nearPlane + (2.0F * sceneBoundingRadius));
        glGetFloat(GL_PROJECTION_MATRIX, lightProjection);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(lightPosition.get(0), lightPosition.get(1), lightPosition.get(2), 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F);
        glGetFloat(GL_MODELVIEW_MATRIX, lightModelView);
        glViewport(0, 0, shadowWidth, shadowHeight);

        if (useFBO) glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBuffer);

        glClear(GL_DEPTH_BUFFER_BIT);

        // Set rendering states to the minimum required, for speed.
//        glShadeModel(GL_FLAT);
        glShadeModel(GL_SMOOTH);
        glDisable(GL_LIGHTING);
        glDisable(GL_COLOR_MATERIAL);
        glDisable(GL_NORMALIZE);
        glColorMask(false, false, false, false);

        glEnable(GL_POLYGON_OFFSET_FILL);
    }

    public static void endGenerate () {
        // instead of GL_DEPTH_COMPONENT_32 there was just GL_DEPTH_COMPONENT
        glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, 0, 0, shadowWidth, shadowHeight, 0);

        // Unbind the framebuffer if we are using them.
        if (useFBO) glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

        // Setup the rendering states.
        glShadeModel(GL_SMOOTH);
        glEnable(GL_LIGHTING);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_NORMALIZE);
        glColorMask(true, true, true, true);
        glDisable(GL_POLYGON_OFFSET_FILL);

        lightProjectionTemp.load(lightProjection);
        lightModelViewTemp.load(lightModelView);
        lightProjection.flip();
        lightModelView.flip();

        Matrix4f tempMatrix = new Matrix4f();
        tempMatrix.setIdentity();
        tempMatrix.translate(new Vector3f(0.5F, 0.5F, 0.5F));
        tempMatrix.scale(new Vector3f(0.5F, 0.5F, 0.5F));
        Matrix4f.mul(tempMatrix, lightProjectionTemp, textureMatrix);
        Matrix4f.mul(textureMatrix, lightModelViewTemp, tempMatrix);
        Matrix4f.transpose(tempMatrix, textureMatrix);

    }

    public static void startRender () {
        glEnable(GL_TEXTURE_2D);
        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);

        glEnable(GL_TEXTURE_GEN_S);
        glEnable(GL_TEXTURE_GEN_T);
        glEnable(GL_TEXTURE_GEN_R);
        glEnable(GL_TEXTURE_GEN_Q);

        tempBuffer.put(0, textureMatrix.m00);
        tempBuffer.put(1, textureMatrix.m01);
        tempBuffer.put(2, textureMatrix.m02);
        tempBuffer.put(3, textureMatrix.m03);

        glTexGen(GL_S, GL_EYE_PLANE, tempBuffer);

        tempBuffer.put(0, textureMatrix.m10);
        tempBuffer.put(1, textureMatrix.m11);
        tempBuffer.put(2, textureMatrix.m12);
        tempBuffer.put(3, textureMatrix.m13);

        glTexGen(GL_T, GL_EYE_PLANE, tempBuffer);

        tempBuffer.put(0, textureMatrix.m20);
        tempBuffer.put(1, textureMatrix.m21);
        tempBuffer.put(2, textureMatrix.m22);
        tempBuffer.put(3, textureMatrix.m23);

        glTexGen(GL_R, GL_EYE_PLANE, tempBuffer);

        tempBuffer.put(0, textureMatrix.m30);
        tempBuffer.put(1, textureMatrix.m31);
        tempBuffer.put(2, textureMatrix.m32);
        tempBuffer.put(3, textureMatrix.m33);

        glTexGen(GL_Q, GL_EYE_PLANE, tempBuffer);
    }

    public static void endRender () {
        glDisable(GL_ALPHA_TEST);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_TEXTURE_GEN_S);
        glDisable(GL_TEXTURE_GEN_T);
        glDisable(GL_TEXTURE_GEN_R);
        glDisable(GL_TEXTURE_GEN_Q);
        glDisable(GL_TEXTURE_2D);

        if (glGetError() != GL_NO_ERROR) System.out.println("An OpenGL error occurred");
    }

    /**
     * Cleanup after the program.
     */
    public static void cleanUp() {
        glDeleteFramebuffersEXT(frameBuffer);
        glDeleteRenderbuffersEXT(renderBuffer);
        Display.destroy();
    }
}
