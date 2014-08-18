package jToolkit4FixedPipeline.image;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

/**
 * Screenshoter for taking pictures from current screen
 * @author Astemir Eleev
 */
public class Screenshoter {
    public enum ColorType {ARGB, RGB, GRAYSCALE};
    public enum ImageType {JPG, PNG};

    private ByteBuffer buffer;
    private int widht;
    private int height;
    private final int numofColorComponents = 4;
    private final String ARGB = "ARGB";
    private final String RGB = "RGB";
    private final String GRAYSCALE = "GRAYSCALE";

    public Screenshoter() {
        glReadBuffer(GL_FRONT);
        widht = Display.getWidth();
        height = Display.getHeight();
        buffer = BufferUtils.createByteBuffer(widht * height * numofColorComponents);
        glReadPixels(0, 0, widht, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
    }
    
    /**
     * Save taken screenshot into some path
     * @param path - where would you like to save the screenshoot
     * @param iType - the format of taken picture
     */
    public void saveScreenShot (final String path, final ImageType iType, final ColorType cType) {
        File f = new File(path + nameGenerator() + "." + iType.name().toUpperCase());
        int colorType = 0;

        if (cType.name().equals(ARGB)) {
            colorType = BufferedImage.TYPE_INT_ARGB;
        } else if (cType.name().equals(RGB)) {
            colorType = BufferedImage.TYPE_INT_RGB;
        } else if (cType.name().equals(GRAYSCALE)) {
            colorType = BufferedImage.TYPE_BYTE_GRAY;
        }
        BufferedImage bi = new BufferedImage(widht, height, colorType);
        
        for (int x = 0; x < widht; x++) {
            for (int y = 0; y < height; y++) {
                int i = (x + (widht * y)) * numofColorComponents;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                bi.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        try {
            ImageIO.write(bi, iType.name(), f);
        } catch (IOException e) {
            Logger.getLogger(Screenshoter.class.getName()).log(Level.WARNING, "screenshot writing to file exception", e);
        }
    }
    
    private String nameGenerator () {
        return "Screenshot from GLtech" + new Date().getTime();
    }
    
}
