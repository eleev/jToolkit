package jToolkit4ProgPipeline.file.texture;

import org.lwjgl.opengl.GL11;
import java.nio.ByteBuffer;

/**
 * Created by Astemir Eleev on 02/01/14.
 */
public class Texture {
    private int id;
    private int target;
    private int height;
    private int width;
    private boolean hasAlpha;
    private ByteBuffer image;

    /**
     *
     * @param image
     * @param width
     * @param height
     * @param hasAlpha
     * @param target it can be: GL_TEXTURE_1D, GL_TEXTURE_2D,GL_TEXTURE_3D,
     *                          GL_TEXTURE_RECTANGLE, GL_TEXTURE_1D_ARRAY,
     *                          GL_TEXTURE_2D_ARRAY, GL_TEXTURE_CUBE_MAP,
     *                          GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_BUFFER,
     *                          GL_TEXTURE_2D_MULTISAMPLE, GL_TEXTURE_2D_MULTISAMPLE_ARRAY
     */
    public Texture(ByteBuffer image, int width, int height, boolean hasAlpha, int target) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.hasAlpha = hasAlpha;
        this.target = target;

        id = GL11.glGenTextures();
        GL11.glBindTexture(this.target, id);

        int format;
        if (this.hasAlpha) format = GL11.GL_RGBA;
        else format = GL11.GL_RGB;

        //GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, 8, GL30.GL_RGBA32F, this.width, this.height);
        GL11.glTexImage2D(this.target, 0, GL11.GL_RGBA, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);


        //GL13.glActiveTexture(0);
    }

    public int getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Texture{" +
                "id=" + id +
                ", target=" + target +
                ", height=" + height +
                ", width=" + width +
                ", hasAlpha=" + hasAlpha +
                ", image=" + image +
                '}';
    }
}
