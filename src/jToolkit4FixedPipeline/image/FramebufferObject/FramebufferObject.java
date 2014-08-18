package jToolkit4FixedPipeline.image.FramebufferObject;

import jToolkit4FixedPipeline.vector.Vector3f;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;

/**
 * Frame buffer class for drawing scene or some part of the scene to the texture
 *
 * Those objects which are going to be rendered to the frame buffer should be invoked between begin() and end()
 * methods of this class.
 * Those objects which are going to be rendered NOT to the frame buffer, should be invoked AFTER end() method
 *
 * =============================================================================================================
 * How to use this class (simple algorithm):
 *
 * 1) nameOfObject.begin(Display.getWidth(), Display.getHeight());
 *
 * // (If you want that content of image changes as changes camera position, use algorithm steps number 2 and 3. Otherwise you
 * //  will get fixed position of camera)
 *
 * 2) cameraTranformation();
 * 3) keyboardHandler();
 * 4) drawingSomeObjects...
 * 5)...
 * ...
 * n) nameOfObject.end(Display.getWidth(), Display.getHeight(), zNear clipping plane, zFar clipping plane);
 *
 * // (If you want to apply camera transformations you can use steps n + 1 and n + 2. Otherwise you will get fixed position of scene)
 *
 * n + 1) cameraTranformation();
 * n + 2) keyboardHandler();
 *
 * // (As a result you have texture with buffered to the framebuffer objects)
 * ============================================================================================================
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 13.02.13
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
public class FramebufferObject {
    private int colorTextureID;
    private int framebufferID;
    private int depthRenderBufferID;

    public void initialize(final int width, final int height) {
        glViewport (0, 0, width, height);							// Reset The Current Viewport
        glMatrixMode (GL_PROJECTION);								// Select The Projection Matrix
        glLoadIdentity ();											// Reset The Projection Matrix
        GLU.gluPerspective(90.0f, width / height, 0.1f, 1000.0f);	// Calculate The Aspect Ratio Of The Window
        glMatrixMode (GL_MODELVIEW);								// Select The Modelview Matrix
        glLoadIdentity ();											// Reset The Modelview Matrix

        // Start Of User Initialization
        glClearColor (0.0f, 0.0f, 0.0f, 0.5f);						// Black Background
        glClearDepth (1.0f);										// Depth DataBuffer Setup
        glDepthFunc (GL_LEQUAL);									// The Type Of Depth Testing (Less Or Equal)
        glEnable (GL_DEPTH_TEST);									// Enable Depth Testing
        glShadeModel (GL_SMOOTH);									// Select Smooth Shading
        glHint (GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);			// Set Perspective Calculations To Most Accurate

        if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
            System.err.println("FBO is not supported");
            System.exit(0);
        }
        else {
            // init our fbo
            framebufferID = glGenFramebuffersEXT();											                            // create a new framebuffer
            colorTextureID = glGenTextures();												                            // and a new texture used as a color buffer
            depthRenderBufferID = glGenRenderbuffersEXT();									                            // And finally a new depthbuffer

            glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID); 						                            // switch to the new framebuffer

            // initialize color texture
            glBindTexture(GL_TEXTURE_2D, colorTextureID);									                            // Bind the colorbuffer texture
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);	// Create the texture data
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0);    // attach it to the framebuffer

            // initialize depth renderbuffer
            glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);				                            // bind the depth renderbuffer
            glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT32, width, width);                          // get the data space for it
            glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthRenderBufferID);// bind it to the renderbuffer
            glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);									                            // Switch back to normal framebuffer rendering
        }
    }

    /**
     * This method renders some objects to the frame buffer
     *
     * @param width of current glViewport (the resolution will depend on quality of texture)
     * @param height of current glViewport (the resolution will depend on quality of texture)
     */
    public void begin (final int width, final int height) {
        glViewport (0, 0, width, height);								// set The Current Viewport to the fbo size
        glBindTexture(GL_TEXTURE_2D, 0);								// unlink textures because if we dont it all is gonna fail
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);		// switch to rendering on our FBO
        glClearColor (1.0f, 1.0f, 1.0f, 1.0f);
        glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);			// Clear Screen And Depth DataBuffer on the fbo to red
        glLoadIdentity ();												// Reset The Modelview Matrix
    }

    /**
     * This method returns default state of pipeline. After this method you can bind texture to the primitive and as a
     * result you will have textured object with rendered on its surface some objects from current pipeline.
     *
     * @param width the resolution of current glViewport (not the texture)
     * @param height the resolution of current glViewport (not the texture)
     * @param zNear clipping value
     * @param zFar lipping value
     */
    public void end (final int width, final int height, final float zNear, final float zFar) {
       glColor3f(1.0f, 1.0f, 1.0f);
        glEnable(GL_TEXTURE_2D);										// enable texturing
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);					// switch to rendering on the framebuffer
        glClearColor (1.0f, 1.0f, 1.0f, 1.f);
        glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);			// Clear Screen And Depth DataBuffer on the framebuffer to black
        glBindTexture(GL_TEXTURE_2D, colorTextureID);					// bind our FBO texture
        glViewport (0, 0, width, height);								// set The Current Viewport

        // returns default state of pipeline
        glLoadIdentity ();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(90.0f, ((float) width / (float) height), zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void drawBox() {
        glPushMatrix();
        glTranslatef(.0f, 20.0f, -5.0f);
        // this func just draws a perfectly normal box with some texture coordinates
        glBegin(GL_QUADS);
        // Front Face
        glTexCoord2f(0.0f, 0.0f); glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f); glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f); glVertex3f( 1.0f,  1.0f,  1.0f);	// Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f); glVertex3f(-1.0f,  1.0f,  1.0f);	// Top Left Of The Texture and Quad
        // Back Face
        glTexCoord2f(1.0f, 0.0f); glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f); glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f); glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f); glVertex3f( 1.0f, -1.0f, -1.0f);	// Bottom Left Of The Texture and Quad
        // Top Face
        glTexCoord2f(0.0f, 1.0f); glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f); glVertex3f(-1.0f,  1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f); glVertex3f( 1.0f,  1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f); glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Right Of The Texture and Quad
        // Bottom Face
        glTexCoord2f(1.0f, 1.0f); glVertex3f(-1.0f, -1.0f, -1.0f);	// Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f); glVertex3f( 1.0f, -1.0f, -1.0f);	// Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f); glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f); glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        // Right face
        glTexCoord2f(1.0f, 0.0f); glVertex3f( 1.0f, -1.0f, -1.0f);	// Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f); glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f); glVertex3f( 1.0f,  1.0f,  1.0f);	// Top Left Of The Texture and Quad
        glTexCoord2f(0.0f, 0.0f); glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        // Left Face
        glTexCoord2f(0.0f, 0.0f); glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Left Of The Texture and Quad
        glTexCoord2f(1.0f, 0.0f); glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        glTexCoord2f(1.0f, 1.0f); glVertex3f(-1.0f,  1.0f,  1.0f);	// Top Right Of The Texture and Quad
        glTexCoord2f(0.0f, 1.0f); glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Left Of The Texture and Quad
        glEnd();
        glPopMatrix();
    }

    public void drawViewerConnectedToTheScreen (final float angle, final Vector3f rotation, final Vector3f position, final Vector3f scaling) {
        glPushMatrix(); {
            glLoadIdentity();
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glRotatef(angle, rotation.getX(), rotation.getY(), rotation.getZ());
            glScalef(scaling.getX(), scaling.getY(), scaling.getZ());

            glBegin(GL_QUADS);
            {
                glTexCoord2f(0.0f, 0.0f); glVertex3f(-1.5f, -1.0f,  0.0f);	// Bottom Left Of The Texture and Quad
                glTexCoord2f(1.0f, 0.0f); glVertex3f( 1.5f, -1.0f,  0.0f);	// Bottom Right Of The Texture and Quad
                glTexCoord2f(1.0f, 1.0f); glVertex3f( 1.5f,  1.0f,  0.0f);	// Top Right Of The Texture and Quad
                glTexCoord2f(0.0f, 1.0f); glVertex3f(-1.5f,  1.0f,  0.0f);	// Top Left Of The Texture and Quad
            }
            glEnd();
        }
        glPopMatrix();
    }

    public void drawViewerConnectedToTheScreen () {
        glPushMatrix(); {
            glLoadIdentity();
//            glTranslatef(position.getX(), position.getY(), position.getZ());
//            glScalef(scaling.getX(), scaling.getY(), scaling.getZ());

            glBegin(GL_QUADS);
            {
                glTexCoord2f(0.0f, 0.0f); glVertex3f(-1.5f, -1.0f,  0.0f);	// Bottom Left Of The Texture and Quad
                glTexCoord2f(1.0f, 0.0f); glVertex3f( 1.5f, -1.0f,  0.0f);	// Bottom Right Of The Texture and Quad
                glTexCoord2f(1.0f, 1.0f); glVertex3f( 1.5f,  1.0f,  0.0f);	// Top Right Of The Texture and Quad
                glTexCoord2f(0.0f, 1.0f); glVertex3f(-1.5f,  1.0f,  0.0f);	// Top Left Of The Texture and Quad
            }
            glEnd();
        }
        glPopMatrix();
    }

}


