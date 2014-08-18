package jToolkit4FixedPipeline.image.reader;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Astemir Eleev
 */
public class TWLLoader {
    /**
     * Loads a PNG texture and puts it in Gl_TEXTURE_RECTANGLE_ARB
     *
     * @param location the location of the png image file
     * @return the generated texture handle or -1 if there was a loading error
     */
    public int glLoadLinearPNG(String location) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, texture);
        InputStream in = null;
        
        try {
            in = new FileInputStream(location);
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        } catch (FileNotFoundException e) {
            System.err.println("Texture file could not be found.");
            return -1;
        } catch (IOException e) {
            System.err.print("Failed to load the texture file.");
            return -1;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
        return texture;
    }

    /**
     * Loads a PNG texture and puts it in Gl_TEXTURE_2D
     *
     * @param location the location of the png image file
     * @return the generated texture handle or -1 if there was a loading error
     */
    public int glLoadPNG(String location) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        InputStream in = null;
        
        try {
            in = new FileInputStream(location);
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        glBindTexture(GL_TEXTURE_2D, 0);
        return texture;
    }
}
