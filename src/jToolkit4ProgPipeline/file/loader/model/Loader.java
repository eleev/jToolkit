package jToolkit4ProgPipeline.file.loader.model;

import jToolkit4FixedPipeline.vector.Vector2f;
import jToolkit4ProgPipeline.file.model.DataBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Astemir Eleev on 17/12/13.
 */
public class Loader {
    private List<Vector3f> vertices;
    private List<Vector3f> normals;
    private List<Face> faces;
    private List<Vector2f> textures;

    private DataBuffer buffer;

    public Loader() {
        vertices = new ArrayList<Vector3f>();
        normals = new ArrayList<Vector3f>();
        faces = new ArrayList<Face>();
        textures = new ArrayList<Vector2f>();

        buffer = new DataBuffer();
    }

    public static Loader loadOBJModel(String name) {
        Loader m = null;
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(new File(name)));
            String line;
            m = new Loader();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    m.vertices.add(new Vector3f(x, y, z));
                } else if (line.startsWith("vn ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    m.normals.add(new Vector3f(x, y ,z));
                } else if (line.startsWith("vt ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    m.textures.add(new Vector2f(x, y));
                }else if (line.startsWith("f ")) {
                    Vector3f vertex = new Vector3f(Float.valueOf(line
                            .split(" ")[1].split("/")[0]), Float.valueOf(line
                            .split(" ")[2].split("/")[0]), Float.valueOf(line
                            .split(" ")[3].split("/")[0]));
//                    Vector3f texture = new Vector3f(Float.valueOf(line
//                            .split(" ")[1].split("/")[1]), Float.valueOf(line
//                            .split(" ")[2].split("/")[1]), Float.valueOf(line
//                            .split(" ")[3].split("/")[1]));
                    Vector3f normal = new Vector3f(Float.valueOf(line
                            .split(" ")[1].split("/")[2]), Float.valueOf(line
                            .split(" ")[2].split("/")[2]), Float.valueOf(line
                            .split(" ")[3].split("/")[2]));


                    m.faces.add(new Face(vertex, normal, null));
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return m;
    }

    public DataBuffer initVBO() {
        buffer.vertices = BufferUtils.createFloatBuffer(9 * faces.size());
        buffer.normals = BufferUtils.createFloatBuffer(9 * faces.size());
//        buffer.textureCoords = BufferUtils.createFloatBuffer(6 * faces.size());
//        buffer.colors = BufferUtils.createFloatBuffer(9 * faces.size());

        for (Face face : faces) {
            Vector3f v1 = vertices.get((int) face.vertex.x - 1);
            buffer.vertices.put(asFloats(v1));

            Vector3f v2 = vertices.get((int) face.vertex.y - 1);
            buffer.vertices.put(asFloats(v2));

            Vector3f v3 = vertices.get((int) face.vertex.z - 1);
            buffer.vertices.put(asFloats(v3));

            Vector3f n1 = normals.get((int) face.normal.x - 1);
            buffer.normals.put(asFloats(n1));

            Vector3f n2 = normals.get((int) face.normal.y - 1);
            buffer.normals.put(asFloats(n2));

            Vector3f n3 = normals.get((int) face.normal.z - 1);
            buffer.normals.put(asFloats(n3));

        }
        buffer.vertices.flip();
        buffer.normals.flip();

        buffer.setLength(faces.size() + normals.size());

        return buffer;
    }

    private static float[] asFloats(Vector3f v) {
        return new float[] {v.getX(), v.getY(), v.getZ()};
    }

    public DataBuffer getBuffer() {
        return buffer;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public List<Vector2f> getTextures() {
        return textures;
    }
}
