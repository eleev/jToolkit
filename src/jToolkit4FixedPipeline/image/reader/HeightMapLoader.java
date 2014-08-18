package jToolkit4FixedPipeline.image.reader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Heightmap loader
 * Can be loaded JPG, BMP, PNG file formats
 * @author Astemir Eleev
 */
public class HeightMapLoader {
    private float data[][];
    private int height;
    private int width;
    
    public HeightMapLoader(String path) {
        try {
            BufferedImage bufImage = ImageIO.read(new File(path));
            height = bufImage.getHeight();
            width = bufImage.getWidth();
            data = new float[width][height];

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    data[i][j] = (bufImage.getRGB(i, j)) & 255;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HeightMapLoader.class.getName()).log(Level.SEVERE, "io exception", ex);
        }
    }

    public float[][] getData() {
        return data;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
