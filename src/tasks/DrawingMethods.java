package tasks;

import jToolkit4FixedPipeline.vector.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 8/25/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class DrawingMethods {
    // data fields only for vbo
    private FloatBuffer vertexData;
    private FloatBuffer colorData;
    private int amountOfVertices;
    private int vertexSize;
    private int colorSize;
    private int vboVertexHandle;
    private int vboColorHandle;

    /**
     *
     * This method simply draws a 3d pyramid using immediate mode.
     * @param size      - of pyramid
     * @param position  - physical position
     */
    public void immediateMode (final float size, final Vector3f position) {

        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glScalef(size, size, size);
            glShadeModel(GL_SMOOTH);

            glBegin(GL_TRIANGLES); {
                // 1st side
                glColor3f(1.0f, .0f, .0f); glVertex3f(-1, -1, 1);
                glColor3f(.0f, 1.0f, .0f); glVertex3f(1, -1, 1);
                glColor3f(.0f, .0f, 1.0f); glVertex3f(.0f, 1.0f, 0.0f);

                // 2d side
                glColor3f(1.0f, .0f, .0f); glVertex3f(-1, -1, 1);
                glColor3f(.0f, 1.0f, .0f); glVertex3f(.0f, -1.0f, -1.0f);
                glColor3f(.0f, .0f, 1.0f); glVertex3f(.0f, 1.0f, .0f);

                // 3d side
                glColor3f(.0f, 1.0f, .0f); glVertex3f(1, -1, 1);
                glColor3f(.0f, 1.0f, .0f); glVertex3f(.0f, -1.0f, -1.0f);
                glColor3f(.0f, .0f, 1.0f); glVertex3f(.0f, 1.0f, .0f);

                // bottom side
                glColor3f(1.0f, .0f, .0f); glVertex3f(-1, -1, 1);
                glColor3f(.0f, 1.0f, .0f); glVertex3f(.0f, -1.0f, -1.0f);
                glColor3f(.0f, 1.0f, .0f); glVertex3f(1, -1, 1);

            }
            glEnd();
        }
        glPopMatrix();
    }


    /**
     * Simple pyramid drawing by display lists technique. It means that before rendering object we compile this object, and
     * after that simply call OGLs functions which will draw compiled object.
     * @param size              - of pyramid
     * @param position          - physical position
     * @param displayListIndex  - integer index of this primitive. Simply after compilation of this code call function
     *                            glCallList(YOUR_INDEX); in OGLs pipeline for drawing this object
     */
    public void displayLists (final float size, final Vector3f position, final int displayListIndex) {

        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glScalef(size, size, size);
            glShadeModel(GL_SMOOTH);

            glGenLists(displayListIndex);{

                glBegin(GL_TRIANGLES); {
                    // 1st side
                    glColor3f(1.0f, .0f, .0f); glVertex3f(-1.0f, -1.0f, 1.0f);
                    glColor3f(.0f, 1.0f, .0f); glVertex3f(1.0f, -1.0f, 1.0f);
                    glColor3f(.0f, .0f, 1.0f); glVertex3f(.0f, 1.0f, 0.0f);

                    // 2d side
                    glColor3f(1.0f, .0f, .0f); glVertex3f(-1.0f, -1.0f, 1.0f);
                    glColor3f(.0f, 1.0f, .0f); glVertex3f(.0f, -1.0f, -1.0f);
                    glColor3f(.0f, .0f, 1.0f); glVertex3f(.0f, 1.0f, .0f);

                    // 3d side
                    glColor3f(.0f, 1.0f, .0f); glVertex3f(1.0f, -1.0f, 1.0f);
                    glColor3f(.0f, 1.0f, .0f); glVertex3f(.0f, -1.0f, -1.0f);
                    glColor3f(.0f, .0f, 1.0f); glVertex3f(.0f, 1.0f, .0f);

                    // bottom side
                    glColor3f(1.0f, .0f, .0f); glVertex3f(-1.0f, -1.0f, 1.0f);
                    glColor3f(.0f, 1.0f, .0f); glVertex3f(.0f, -1.0f, -1.0f);
                    glColor3f(.0f, 1.0f, .0f); glVertex3f(1.0f, -1.0f, 1.0f);
                }
                glEnd();
            }
            glEndList();
        }
        glPopMatrix();

    }

    /**
     * Initialization method for vao rendering mode
     */
    public void initVertexArrayObject () {
        amountOfVertices = 12;
        vertexSize = 3;
        colorSize = 3;

        vertexData = BufferUtils.createFloatBuffer(amountOfVertices * vertexSize);
        vertexData.put(new float[] {
                // 1st
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                .0f, 1.0f, 0.0f,
                // 2d
                -1.0f, -1.0f, 1.0f,
                .0f, -1.0f, -1.0f,
                .0f, 1.0f, .0f,
                // 3d
                1.0f, -1.0f, 1.0f,
                .0f, -1.0f, -1.0f,
                .0f, 1.0f, .0f,
                // bottom
                -1.0f, -1.0f, 1.0f,
                .0f, -1.0f, -1.0f,
                1.0f, -1.0f, 1.0f
        });
        vertexData.flip();

        colorData = BufferUtils.createFloatBuffer(amountOfVertices * colorSize);
        colorData.put(new float[] {
                // 1st triangle
                1.0f, .0f, .0f,
                .0f, 1.0f, .0f,
                .0f, .0f, 1.0f,
                // 2nd triangle
                1.0f, .0f, .0f,
                .0f, 1.0f, .0f,
                .0f, .0f, 1.0f,
                // 3d triangle
                .0f, 1.0f, .0f,
                .0f, 1.0f, .0f,
                .0f, .0f, 1.0f,
                // bottom triangle
                1.0f, .0f, .0f,
                .0f, 1.0f, .0f,
                .0f, 1.0f, .0f
        });
        colorData.flip();

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glVertexPointer(vertexSize, 0, vertexData);
        glColorPointer(colorSize, 0, colorData);
    }

    public void drawVertexArrayObject (final float size, final Vector3f position) {
        glPushMatrix(); {
            glTranslated(position.getX(), position.getY(), position.getZ());
            glScalef(size, size, size);

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);

            glDrawArrays(GL_TRIANGLES, 0, amountOfVertices);

            glDisableClientState(GL_VERTEX_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);
        }
        glPopMatrix();

    }


    /**
     * Initialization method for vbo rendering mode
     */
    public void initVertexBufferObject () {
        amountOfVertices = 12;
        vertexSize = 3;
        colorSize = 3;

        vertexData = BufferUtils.createFloatBuffer(amountOfVertices * vertexSize);
        vertexData.put(new float[] {
                // 1st
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                .0f, 1.0f, 0.0f,
                // 2d
                -1.0f, -1.0f, 1.0f,
                .0f, -1.0f, -1.0f,
                .0f, 1.0f, .0f,
                // 3d
                1.0f, -1.0f, 1.0f,
                .0f, -1.0f, -1.0f,
                .0f, 1.0f, .0f,
                // bottom
                -1.0f, -1.0f, 1.0f,
                .0f, -1.0f, -1.0f,
                1.0f, -1.0f, 1.0f
        });
        vertexData.flip();

        colorData = BufferUtils.createFloatBuffer(amountOfVertices * colorSize);
        colorData.put(new float[] {
                // 1st triangle
                1.0f, .0f, .0f,
                .0f, 1.0f, .0f,
                .0f, .0f, 1.0f,
                // 2nd triangle
                1.0f, .0f, .0f,
                .0f, 1.0f, .0f,
                .0f, .0f, 1.0f,
                // 3d triangle
                .0f, 1.0f, .0f,
                .0f, 1.0f, .0f,
                .0f, .0f, 1.0f,
                // bottom triangle
                1.0f, .0f, .0f,
                .0f, 1.0f, .0f,
                .0f, 1.0f, .0f
        });
        colorData.flip();

        vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vboColorHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
        glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(vertexSize, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
        glColorPointer(colorSize, GL_FLOAT, 0, 0L);
    }

    /**
     * In addition to vertex and color arrays, there could be passed normal array by the way :)
     * This rendering mode not so hard as many people think about it.
     * Just simply init VBOs data, enable some states and by glDrawArrays() function draw geometry. Pretty simple.
     * @param size      - of object
     * @param position  - of object
     */
    public void drawVertexBufferObject (final float size, final Vector3f position) {
        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glScaled(size, size, size);

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);

            glDrawArrays(GL_TRIANGLES, 0, amountOfVertices);

            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);
        }
        glPopMatrix();
    }

    /**
     * This method deallocates vertex buffer objects memory
     */
    public void deleteVertexBufferObject () {
        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboColorHandle);
    }

}
