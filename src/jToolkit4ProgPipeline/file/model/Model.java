package jToolkit4ProgPipeline.file.model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by virus on 17/12/13.
 */
public class Model {
    // vertices, normals, texture coordinates or colors all of these are stored here
    private DataBuffer bufferData;
    private int bufferDataLength;

    private int vertexAttribIndex = -1;
    private int normalAttribIndex = -1;
    private int textureAttribIndex = -1;
    private int colorAttribIndex = -1;

    private int vertexArrayIndex;

    private int componentrNum;
    private final int STANDART_SIZE = 3;

    /**
     * This constructor defines foundation for model data structure
     * @param bufferData
     * @param vertexAttribIndex
     * @param normalAttribIndex
     * @param usage - it can be GL_STATIC_DRAW, GL_STATIC_COPY, GL_STATIC_READ,
     *                          GL_DYNAMIC_DRAW, GL_DYNAMIC_COPY, GL_DYNAMIC_READ,
     *                          GL_STREAM_DRAW, GL_STREAM_COPY, GL_STREAM_READ
     */
    public Model(DataBuffer bufferData, int vertexAttribIndex, int colorAttribIndex, int normalAttribIndex, int usage) {
        this.bufferData = bufferData;
        this.vertexAttribIndex = vertexAttribIndex;
        this.colorAttribIndex = colorAttribIndex;
        this.normalAttribIndex = normalAttribIndex;
        bufferDataLength = bufferData.getLength();

        initVertexArray();

        int verID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verID);
        glBufferData(GL_ARRAY_BUFFER, bufferData.vertices, usage);

        GL20.glEnableVertexAttribArray(this.vertexAttribIndex);
        GL20.glVertexAttribPointer(this.vertexAttribIndex, STANDART_SIZE, GL11.GL_FLOAT, false, 0, 0);
        componentrNum += STANDART_SIZE;

        int colID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colID);
        glBufferData(GL_ARRAY_BUFFER, bufferData.normals, usage);
        componentrNum += STANDART_SIZE;

        GL20.glEnableVertexAttribArray(this.colorAttribIndex);
        GL20.glVertexAttribPointer(this.colorAttribIndex, STANDART_SIZE, GL11.GL_FLOAT, false, 0, 0);

        int normID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normID);
        glBufferData(GL_ARRAY_BUFFER, bufferData.normals, usage);
        componentrNum += STANDART_SIZE;

        GL20.glEnableVertexAttribArray(this.normalAttribIndex);
        GL20.glVertexAttribPointer(this.normalAttribIndex, STANDART_SIZE, GL11.GL_FLOAT, false, 0, 0);

    }

    /**
     * This constructor defines foundation for model data structure
     * @param bufferData
     * @param vertexAttribIndex
     * @param normalAttribIndex
     * @param usage - it can be GL_STATIC_DRAW, GL_STATIC_COPY, GL_STATIC_READ,
     *                          GL_DYNAMIC_DRAW, GL_DYNAMIC_COPY, GL_DYNAMIC_READ,
     *                          GL_STREAM_DRAW, GL_STREAM_COPY, GL_STREAM_READ
     */
    public Model(DataBuffer bufferData, int vertexAttribIndex, int colorAttribIndex, int normalAttribIndex, int textureAttribIndex, int usage) {
        this.bufferData = bufferData;
        this.vertexAttribIndex = vertexAttribIndex;
        this.colorAttribIndex = colorAttribIndex;
        this.normalAttribIndex = normalAttribIndex;
        this.textureAttribIndex = textureAttribIndex;
        bufferDataLength = bufferData.getLength();

        initVertexArray();

        int verID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verID);
        glBufferData(GL_ARRAY_BUFFER, bufferData.vertices, usage);

        GL20.glEnableVertexAttribArray(this.vertexAttribIndex);
        GL20.glVertexAttribPointer(this.vertexAttribIndex, STANDART_SIZE, GL11.GL_FLOAT, false, 0, 0);
        componentrNum += STANDART_SIZE;

        int colID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colID);
        glBufferData(GL_ARRAY_BUFFER, bufferData.normals, usage);
        componentrNum += STANDART_SIZE;

        GL20.glEnableVertexAttribArray(this.colorAttribIndex);
        GL20.glVertexAttribPointer(this.colorAttribIndex, STANDART_SIZE, GL11.GL_FLOAT, false, 0, 0);

        int normID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normID);
        glBufferData(GL_ARRAY_BUFFER, bufferData.normals, usage);
        componentrNum += STANDART_SIZE;

        GL20.glEnableVertexAttribArray(this.normalAttribIndex);
        GL20.glVertexAttribPointer(this.normalAttribIndex, STANDART_SIZE, GL11.GL_FLOAT, false, 0, 0);

        int textID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textID);
        glBufferData(GL_ARRAY_BUFFER, bufferData.textureCoords, usage);
        componentrNum += STANDART_SIZE;

        GL20.glEnableVertexAttribArray(this.textureAttribIndex);
        GL20.glVertexAttribPointer(this.textureAttribIndex, STANDART_SIZE, GL11.GL_FLOAT, false, 0, 0);
    }

    /**
     * This method draws the defined primitive
     * @param mode can be : GL_TRIANGLES, GL_TRIANGLES_STRIP, GL_TRIANGLES_FAN,
     *                      GL_LINES, GL_POINTS, GL_QUADS, GL_QUADS_STRIP, GL_POLYGONS
     */
    public void draw(int mode) {
        GL11.glDrawArrays(mode, 0, bufferData.getLength() * componentrNum);
    }

    public void bindVertexArray() {
        glBindVertexArray(vertexArrayIndex);
    }

    public void unbindVertexArray() {
        glBindVertexArray(0);
    }
    private void initVertexArray() {
        vertexArrayIndex = glGenVertexArrays();
        bindVertexArray();
    }

}
