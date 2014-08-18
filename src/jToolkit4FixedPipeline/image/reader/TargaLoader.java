package jToolkit4FixedPipeline.image.reader;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 27.01.13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class TargaLoader {
    private static int offset;
    private byte[] imageData;

    public TargaLoader (String pathToFile) {
        try {
            File f = new File(pathToFile);
            imageData = new byte[(int)f.length()];
            BufferedInputStream bufStram = new BufferedInputStream(new FileInputStream(f));
            bufStram.read(imageData);
            bufStram.close();
        } catch (IOException ioexception) {
            Logger.getLogger(TargaLoader.class.getName()).log(Level.WARNING, "io exception", ioexception);
        }
    }

    public BufferedImage getBufferedImage () {
        return decode(imageData);
    }

    public byte[] getByteArrayImage () {
        return ((DataBufferByte)(decode(imageData).getRaster().getDataBuffer())).getData();
    }

    public ByteBuffer getByteBufferImage () {
        byte[] data = ((DataBufferByte)(decode(imageData).getRaster().getDataBuffer())).getData();

        final ByteBuffer bb = ByteBuffer.allocateDirect(data.length);
        bb.order(ByteOrder.nativeOrder());
        bb.put(data, 0, data.length);
        bb.flip();

        return bb;
    }

    public int getImageWidth () {
        return getBufferedImage().getWidth();
    }

    public int getImageHeight () {
        return getBufferedImage().getHeight();
    }

    private BufferedImage decode(byte[] buf) {
        offset = 0;

        // Reading header bytes
        // buf[2]=image type code 0x02=uncompressed BGR or BGRA
        // buf[12]+[13]=width
        // buf[14]+[15]=height
        // buf[16]=image pixel size 0x20=32bit, 0x18=24bit
        // buf{17]=Image Descriptor Byte=0x28 (00101000)=32bit/origin upperleft/non-interleaved
        for (int i=0;i<12;i++)
            read(buf);
        int width = read(buf)+(read(buf)<<8);   // 00,04=1024
        int height = read(buf)+(read(buf)<<8);  // 40,02=576
        read(buf);
        read(buf);

        int n = width*height;
        int[] pixels = new int[n];
        int idx=0;

        if (buf[2]==0x02 && buf[16]==0x20) { // uncompressed BGRA
            while(n>0) {
                int b = read(buf);
                int g = read(buf);
                int r = read(buf);
                int a = read(buf);
                int v = (a<<24) | (r<<16) | (g<<8) | b;
                pixels[idx++] = v;
                n-=1;
            }
        } else if (buf[2]==0x02 && buf[16]==0x18) {  // uncompressed BGR
            while(n>0) {
                int b = read(buf);
                int g = read(buf);
                int r = read(buf);
                int a = 255; // opaque pixel
                int v = (a<<24) | (r<<16) | (g<<8) | b;
                pixels[idx++] = v;
                n-=1;
            }
        } else {
            // RLE compressed
            while (n>0) {
                int nb = read(buf); // num of pixels
                if ((nb&0x80)==0) { // 0x80=dec 128, bits 10000000
                    for (int i=0;i<=nb;i++) {
                        int b = read(buf);
                        int g = read(buf);
                        int r = read(buf);
                        pixels[idx++] = 0xff000000 | (r<<16) | (g<<8) | b;
                    }
                } else {
                    nb &= 0x7f;
                    int b = read(buf);
                    int g = read(buf);
                    int r = read(buf);
                    int v = 0xff000000 | (r<<16) | (g<<8) | b;
                    for (int i=0;i<=nb;i++)
                        pixels[idx++] = v;
                }
                n-=nb+1;
            }
        }

        BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bimg.setRGB(0, 0, width,height, pixels, 0,width);
        return bimg;
    }

    private static int btoi(byte b) {
        int a = b;
        return (a<0?256+a:a);
    }

    private static int read(byte[] buf) {
        return btoi(buf[offset++]);
    }
}
