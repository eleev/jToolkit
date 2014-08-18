package jToolkit4FixedPipeline.image.reader;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * It loads PNG file and converts if to byteBuffer structure
 * @author Astemir Yeleev
 */
public class PNGLoader {
    private PNGDecoder decoder;
    private ByteBuffer buffer;
    
    public PNGLoader(String path) {
        try {
            decoder = new PNGDecoder(new FileInputStream(new File(path)));
            buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
        } catch (IOException ex) {
            Logger.getLogger(PNGLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }
    
    public int getHeight () {
        return decoder.getHeight();
    }
    
    public int getWidth () {
        return decoder.getWidth();
    }
    
}
