package jToolkit4FixedPipeline.image.reader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for loading BMP, PNG or JPEG images. This class represents low - level 
 * of image, which means that you can achieve access to bytes of image
 * @author Astemit Yeleev
 */
public class ImageLoader {
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

    public ByteBuffer bufferedImageToByteBuffer () {
        final byte[] b = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final ByteBuffer bb = ByteBuffer.allocateDirect(b.length);
        bb.order(ByteOrder.nativeOrder());
        bb.put(b, 0, b.length);
        bb.flip();

        return bb;
    }

    public ByteBuffer bufferedImageToByteBuffer (final BufferedImage imageBuffered) {
        if (imageBuffered.equals(null)) {
            System.err.println("BufferedImage is null");
            return null;
        }
        final byte[] b = ((DataBufferByte) imageBuffered.getRaster().getDataBuffer()).getData();
        final ByteBuffer bb = ByteBuffer.allocateDirect(b.length);
        bb.order(ByteOrder.nativeOrder());
        bb.put(b, 0, b.length);
        bb.flip();

        return bb;
    }

    public ByteBuffer byteArrayToByteBuffer (final byte[] array) {
        if (array.length == 0) {
            System.out.println("byte array is null");
            return null;
        }
        final ByteBuffer bb = ByteBuffer.allocateDirect(array.length);
        bb.order(ByteOrder.nativeOrder());
        bb.put(array, 0, array.length);
        bb.flip();

        return bb;
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
