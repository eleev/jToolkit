package jToolkit4ProgPipeline.file.loader.image;

import jToolkit4ProgPipeline.file.utils.buffer.BufferTools;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for loading BMP, PNG or JPEG images. This class represents low - level 
 * of image, which means that you can achieve access to bytes of image
 * @author Astemit Yeleev
 */
public final class ImageLoader {
    private BufferedImage image;

    public ImageLoader() {
        // In some cases empty constructor required
    }

    public ImageLoader(String path) {
        File f = new File(path);

        try {
            image = ImageIO.read(f);
            if (image == null) {
                throw new IOException("Loading image error");
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getBufferedImage () {
        return image;
    }

    public byte[] getArray () {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    public byte[][] getMatrix () {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getBankData();
    }

    public ByteBuffer getImageData() {
        byte data[] = getArray();
        return BufferTools.toFlippedByteBuffer(data);
    }

    public int getImageWidth () {
        return image.getWidth();
    }

    public int getImageHeight () {
        return image.getHeight();
    }

    public boolean hasAlpha () {
        return image.getColorModel().hasAlpha();
    }

    public int getBlue () {
        return image.getGraphics().getColor().getBlue();
    }

    public int getGreen () {
        return image.getGraphics().getColor().getGreen();
    }

    public int getRed () {
        return image.getGraphics().getColor().getRed();
    }

}
