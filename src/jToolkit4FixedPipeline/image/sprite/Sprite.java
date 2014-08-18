package jToolkit4FixedPipeline.image.sprite;

import jToolkit4FixedPipeline.image.texture.Texture;
import jToolkit4FixedPipeline.image.texture.TextureLoader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

/**
 * Implementation of sprite that uses an OpenGL quad and a texture
 * to render a given image to the screen.
 * @author Astemir Yeleev
 */
public class Sprite {

    /**
     * The texture that stores the image for this sprite
     */
    private Texture texture;
    /**
     * The width in pixels of this sprite
     */
    private int width;
    /**
     * The height in pixels of this sprite
     */
    private int height;

    /**
     * Create a new sprite from a specified image.
     *
     * @param loader the texture loader to use
     * @param classpath A reference to the image on which this sprite should be based
     */
    public Sprite(TextureLoader loader, String classpath) {
        try {
            texture = loader.getTexture(classpath);
            width = texture.getImageWidth();
            height = texture.getImageHeight();
        } catch (IOException ioe) {
            Logger.getLogger(Sprite.class.getName()).log(Level.WARNING, "exception when image is loading", ioe);
            System.exit(-1);
        }
    }

    /**
     * Get the width of this sprite in pixels
     *
     * @return The width of this sprite in pixels
     */
    public int getWidth() {
        return texture.getImageWidth();
    }

    /**
     * Get the height of this sprite in pixels
     *
     * @return The height of this sprite in pixels
     */
    public int getHeight() {
        return texture.getImageHeight();
    }

    /**
     * Draw the sprite at the specified location
     *
     * @param x The x location at which to draw this sprite
     * @param y The y location at which to draw this sprite
     */
    public void draw(int x, int y) {
        // store the current model matrix
        glPushMatrix();

        // bind to the appropriate texture for this sprite
        texture.bind();

        // translate to the right location and prepare to draw
        glTranslatef(x, y, 0);

        // draw a quad textured to match the sprite
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);

            glTexCoord2f(0, texture.getHeight());
            glVertex2f(0, height); 

            glTexCoord2f(texture.getWidth(), texture.getHeight());
            glVertex2f(width, height);

            glTexCoord2f(texture.getWidth(), 0);
            glVertex2f(width, 0);
        }
        glEnd();

        // restore the model view matrix to prevent contamination
        glPopMatrix();
    }
    
}