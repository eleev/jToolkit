package jToolkit4FixedPipeline.image.reader;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Astemir Yeleev
 */
public class RAWLoader {
    public byte[] LoadRawFile (String strName, int nSize, byte [] heightmap) {
        FileInputStream input = null;
        
        try {
            input = new FileInputStream(new File(strName));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't Find The Height Map!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        try {
            input.read(heightmap, 0, nSize);
            input.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't read The Height Map!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        for (int i = 0; i < heightmap.length; i++) {
            heightmap[i] &= 0xFF;
        }
        return heightmap;
    }
}
