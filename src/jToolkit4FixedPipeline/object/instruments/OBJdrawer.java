package jToolkit4FixedPipeline.object.instruments;

import jToolkit4FixedPipeline.object.datastructure.Face;
import jToolkit4FixedPipeline.object.datastructure.Model;
import jToolkit4FixedPipeline.vector.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 *
 * @author Astemir Yeleev
 */
public class OBJdrawer {
    private int indexInDisplayList;
    private Model model;

    public OBJdrawer(int indexInDisplayList, String modelLocation) {
        this.indexInDisplayList = indexInDisplayList;
        
        try {
            this.model = OBJloader.loadModel(modelLocation);
        } catch (Exception e) {
            Logger.getLogger(OBJdrawer.class.getName()).log(Level.WARNING, "loading object exception", e);
        }
    }
    
    public OBJdrawer(int indexInDisplayList, Model model) {
        this.indexInDisplayList = indexInDisplayList;
        this.model = model;
    }
    
    public void draw (Vector3f translate, float angleOfRotate, Vector3f rotare, Vector3f scale, Vector3f color) {
        glPushMatrix();
        {
            glGenLists(this.indexInDisplayList);
            glNewList(this.indexInDisplayList, GL_COMPILE);
            {
                glTranslatef(translate.getX(), translate.getY(), translate.getZ());
                glRotatef(angleOfRotate, rotare.getX(), rotare.getY(), rotare.getZ());
                glScaled(scale.getX(), scale.getY(), scale.getZ());
                glColor3f(color.getX(), color.getY(), color.getZ());

                glBegin(GL_TRIANGLES);
                {
                    for (Face face : this.model.faces) {
                        Vector3f n1 = model.normals.get((int) face.normals.getX() - 1);
                        glNormal3f(n1.getX(), n1.getY(), n1.getZ());
                        Vector3f v1 = model.vertices.get((int) face.vertex.getX() - 1);
                        glVertex3f(v1.getX(), v1.getY(), v1.getZ());
                        
                        Vector3f n2 = model.normals.get((int) face.normals.getY() - 1);
                        glNormal3f(n2.getX(), n2.getY(), n2.getZ());
                        Vector3f v2 = model.vertices.get((int) face.vertex.getY() - 1);
                        glVertex3f(v2.getX(), v2.getY(), v2.getZ());
                        
                        Vector3f n3 = model.normals.get((int) face.normals.getZ() - 1);
                        glNormal3f(n3.getX(), n3.getY(), n3.getZ());
                        Vector3f v3 = model.vertices.get((int) face.vertex.getZ() - 1);
                        glVertex3f(v3.getX(), v3.getY(), v3.getZ());
                    }
                    
                }
                glEnd();
            }
            glEndList();
        }
        glPopMatrix();
        glFlush();
    }
    
    private static FloatBuffer reserveData(int size) {
        FloatBuffer data = BufferUtils.createFloatBuffer(size);
        return data;
    }

    private static float[] asFloats(Vector3f v) {
        return new float[] {v.getX(), v.getY(), v.getZ()};
    }

    public static int[] createVBO(Model model) {
        int vboVertexHandle = glGenBuffers();
        int vboNormalHandle = glGenBuffers();
        FloatBuffer vertices = reserveData(model.faces.size() * 9);
        FloatBuffer normals = reserveData(model.faces.size() * 9);

        for (Face face : model.faces) {
            vertices.put(asFloats(model.vertices.get((int) face.vertex.getX() - 1)));
            vertices.put(asFloats(model.vertices.get((int) face.vertex.getY() - 1)));
            vertices.put(asFloats(model.vertices.get((int) face.vertex.getZ() - 1)));
            normals.put(asFloats(model.normals.get((int) face.normals.getX() - 1)));
            normals.put(asFloats(model.normals.get((int) face.normals.getY() - 1)));
            normals.put(asFloats(model.normals.get((int) face.normals.getZ() - 1)));
        }
        vertices.flip();
        normals.flip();
        
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
        glNormalPointer(GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        return new int[] {vboVertexHandle, vboNormalHandle};
    }
    
    public void setIndexInDisplayList(int indexInDisplayList) {
        this.indexInDisplayList = indexInDisplayList;
    }

    public int getIndexInDisplayList() {
        return indexInDisplayList;
    }

    public void setModel (final Model model) {
        this.model = model;
    }

    public Model getModel () {
        return model;
    }
    
}