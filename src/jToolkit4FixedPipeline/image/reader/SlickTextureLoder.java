package jToolkit4FixedPipeline.image.reader;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for loading PNG or JPEG images as a texture or sprite
 * @author Astemir Eleev
 */
public class SlickTextureLoder {
    private static Texture texture;

    public SlickTextureLoder(String classpath, String type) {
        try {
            texture = TextureLoader.getTexture(type, ResourceLoader.getResourceAsStream(classpath), true);
        } catch (IOException e) {
            Logger.getLogger(SlickTextureLoder.class.getName()).log(Level.WARNING, "exception when image is loading", e);
        }
    }
    
    public byte[] getByteTexture () {
        return texture.getTextureData();
    } 
    
    public ByteBuffer getByteBuffertexture () {
        byte[] b = texture.getTextureData();
        ByteBuffer bb = ByteBuffer.allocateDirect(b.length);
        bb.put(b);
        bb.position(0);
        return bb;
    }

    public Texture getTexture () {
        return texture;
    }

    /**
     *
     * @return the texture ID for binding
     */
    public int getTextureID () {
        return texture.getTextureID();
    }

    @Override
    public String toString () {
        return "Texture loaded: " + texture + "\n"
                + ">> Image width: " + texture.getImageWidth() + "\n"
                + ">> Image height: " + texture.getImageHeight() + "\n"
                + ">> Texture width: " + texture.getTextureWidth() + "\n"
                + ">> Texture height: " + texture.getTextureHeight() + "\n"
                + ">> Texture ID: " + texture.getTextureID() + "\n"
                + ">> Texture has alpha: " + texture.hasAlpha();
    }
}
