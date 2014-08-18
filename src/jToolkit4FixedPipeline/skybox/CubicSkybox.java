/*
 * Cubic skybox for some decaration of scene
 * 
 */
package jToolkit4FixedPipeline.skybox;

import static org.lwjgl.opengl.GL11.*;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import jToolkit4FixedPipeline.vector.Vector3f;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Astemir Yeleev
 */
public class CubicSkybox {
    private Vector<Texture> textures;
    private Vector3f positionOfplayer;
    private float size;

    public CubicSkybox(String path, String[] namesOfTextures, String fileFormat) {
        if (namesOfTextures.length == 0 || path == null || fileFormat == null) {
            try {
                throw new IOException("some variables won't be initialized");
            } catch (IOException ex) {
                Logger.getLogger(CubicSkybox.class.getName()).log(Level.WARNING, "EXCEPTION", ex);
            }
        }
        this.textures = new Vector();
        
        try {
            for (int i = 0; i < namesOfTextures.length; i++) {
                this.textures.add(TextureLoader.getTexture(fileFormat, ResourceLoader.getResourceAsStream(path + namesOfTextures[i] + '.' + fileFormat.toLowerCase()), GL_LINEAR));
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CubicSkybox.class.getName()).log(Level.WARNING, "loading skyboxes images exception",ex);
        }
    }

    private void clampToEdge() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
    }
    
    public void setSize (float size) {
        this.size = size;
    }
    
    public float getSize () {
        return this.size;
    }
    
    public void setPositionOfPlayer (Vector3f posotion) {
        this.positionOfplayer = posotion;
    }
    
    public void draw () {
        glPushMatrix();
        {
            glDisable(GL_CULL_FACE);
            glTranslatef(this.positionOfplayer.getX(), this.positionOfplayer.getY(), this.positionOfplayer.getZ());

            GL13.glActiveTexture(GL_TEXTURE_2D);
            glEnable(GL_TEXTURE_2D);
            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            // Render the front quad
            clampToEdge();
            textures.get(0).bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(1, 1);
                glVertex3f(size, -size, -size);
                glTexCoord2f(0, 1);
                glVertex3f(-size, -size, -size);
                glTexCoord2f(0, 0);
                glVertex3f(-size, size, -size);
                glTexCoord2f(1, 0);
                glVertex3f(size, size, -size);
            }
            glEnd();

            // Render the left quad
            clampToEdge();
            textures.get(1).bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(1, 1);
                glVertex3f(size, -size, size);
                glTexCoord2f(0, 1);
                glVertex3f(size, -size, -size);
                glTexCoord2f(0, 0);
                glVertex3f(size, size, -size);
                glTexCoord2f(1, 0);
                glVertex3f(size, size, size);
            }
            glEnd();

            // Render the back quad
            clampToEdge();
            textures.get(2).bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(1, 1);
                glVertex3f(-size, -size, size);
                glTexCoord2f(0, 1);
                glVertex3f(size, -size, size);
                glTexCoord2f(0, 0);
                glVertex3f(size, size, size);
                glTexCoord2f(1, 0);
                glVertex3f(-size, size, size);
            }
            glEnd();

            // Render the right quad
            clampToEdge();
            textures.get(3).bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(1, 1);
                glVertex3f(-size, -size, -size);
                glTexCoord2f(0, 1);
                glVertex3f(-size, -size, size);
                glTexCoord2f(0, 0);
                glVertex3f(-size, size, size);
                glTexCoord2f(1, 0);
                glVertex3f(-size, size, -size);
            }
            glEnd();

            // Render the top quad
            clampToEdge();
            textures.get(4).bind();
            glPushMatrix();
            {
                glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                glBegin(GL_QUADS);
                {
                    glTexCoord2f(1, 1);
                    glVertex3f(-size, size, -size);
                    glTexCoord2f(0, 1);
                    glVertex3f(-size, size, size);
                    glTexCoord2f(0, 0);
                    glVertex3f(size, size, size);
                    glTexCoord2f(1, 0);
                    glVertex3f(size, size, -size);
                }
                glEnd();
            }
            glPopMatrix();

            // Render the bottom quad
            clampToEdge();
            textures.get(4).bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(1, 1);
                glVertex3f(-size, -size, -size);
                glTexCoord2f(1, 0);
                glVertex3f(-size, -size, size);
                glTexCoord2f(0, 0);
                glVertex3f(size, -size, size);
                glTexCoord2f(0, 1);
                glVertex3f(size, -size, -size);
            }
            glEnd();
            glEnable(GL_CULL_FACE);
            glDisable(GL_TEXTURE_2D);
        }
        glPopMatrix();
    }

    public void cleanUp () {
        glDeleteTextures(textures.get(0).getTextureID());
        glDeleteTextures(textures.get(1).getTextureID());
        glDeleteTextures(textures.get(2).getTextureID());
        glDeleteTextures(textures.get(3).getTextureID());
        glDeleteTextures(textures.get(4).getTextureID());
    }
    
}
