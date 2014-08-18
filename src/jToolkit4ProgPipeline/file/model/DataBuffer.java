package jToolkit4ProgPipeline.file.model;

import java.nio.FloatBuffer;

/**
 * Created by Astemir Eleev on 17/12/13.
 */
public class DataBuffer {
    public FloatBuffer vertices;
    public FloatBuffer normals;
    public FloatBuffer textureCoords;
    public FloatBuffer colors;

    private int length;

    public DataBuffer() {
        // default constructor
    }

    public DataBuffer(FloatBuffer vertices, FloatBuffer normals, FloatBuffer textureCoords) {
        this.vertices = vertices;
        this.normals = normals;
        this.textureCoords = textureCoords;
    }

    public DataBuffer(FloatBuffer vertices, FloatBuffer normals, FloatBuffer textureCoords, FloatBuffer colors) {
        this.vertices = vertices;
        this.normals = normals;
        this.textureCoords = textureCoords;
        this.colors = colors;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

}
