package jToolkit4FixedPipeline.object.instruments;

import jToolkit4FixedPipeline.object.datastructure.Face;
import jToolkit4FixedPipeline.object.datastructure.Model;
import jToolkit4FixedPipeline.vector.Vector2f;
import jToolkit4FixedPipeline.vector.Vector3f;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Astemir Eleev
 */
public class OBJloader {
    public static Model loadModel (String path) throws IOException {
        BufferedReader reader = null;
        Model model = null;
        String line;
        
        try {
            File f = new File(path);
            reader = new BufferedReader(new FileReader(f));
            model = new Model();
            
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    model.vertices.add(new Vector3f(x, y, z));
                } else if (line.startsWith("vn ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    model.normals.add(new Vector3f(x, y, z));
                } else if (line.startsWith("vt ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    model.textures.add(new Vector2f(x, y));
                }else if (line.startsWith("f ")) {
                    Vector3f vertexIndices = new Vector3f(Float.valueOf(line
                            .split(" ")[1].split("/")[0]), Float.valueOf(line
                            .split(" ")[2].split("/")[0]), Float.valueOf(line
                            .split(" ")[3].split("/")[0]));
                    Vector3f textureIndices = new Vector3f(Float.valueOf(line
                            .split(" ")[1].split("/")[1]), Float.valueOf(line
                            .split(" ")[2].split("/")[1]), Float.valueOf(line
                            .split(" ")[3].split("/")[1]));
                    Vector3f normalIndices = new Vector3f(Float.valueOf(line
                            .split(" ")[1].split("/")[2]), Float.valueOf(line
                            .split(" ")[2].split("/")[2]), Float.valueOf(line
                            .split(" ")[3].split("/")[2]));

                   model.faces.add(new Face(vertexIndices, normalIndices, textureIndices));
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OBJloader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(OBJloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        reader.close();
        return model;
    }
}
