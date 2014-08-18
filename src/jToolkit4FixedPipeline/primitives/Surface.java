package jToolkit4FixedPipeline.primitives;

import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Virus
 * Date: 14.01.13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public class Surface {
    public void drawLinerGround (int dlIndex, float size, int lod, float angleOfRotation, Vector3f rotate, Vector3f translate, Vector3f scale, Vector3f color) {
        if (dlIndex < 1) {
            System.err.println("Display list index must be greather than 1. Programm automaticly sets new value to dlInde which is equal 1");
            dlIndex = 1;
        }
        if (lod < 1) {
            System.err.println("Level of detail must be greather than 1.");
            lod = 1;
        }
        if (size < 1) {
            System.err.println("Size of chesboard must be greather than 1");
            size = 1;
        }

        glPushMatrix();
        {
            glGenLists(dlIndex);
            glNewList(dlIndex, GL_COMPILE);
            {
                glTranslated(translate.getX(), translate.getY(), translate.getZ());
                glRotatef(angleOfRotation, rotate.getX(), rotate.getY(), rotate.getZ());
                glScalef(scale.getX(), scale.getY(), scale.getZ());
                glColor3f(color.getX(), color.getY(), color.getZ());

                glBegin(GL_LINES);
                {
                    float line;
                    for (line = -size; line < size; line += lod) {
                        glVertex3f(line, .0f, size);
                        glVertex3f(line, .0f, -size);
                        glVertex3f(size, .0f, line);
                        glVertex3f(-size, .0f, line);
                    }
                    glVertex3f(line, .0f, size);
                    glVertex3f(line, .0f, -size);
                    glVertex3f(size, .0f, line);
                    glVertex3f(-size, .0f, line);
                }
                glEnd();
            }
            glEndList();


        }
        glPopMatrix();
    }

    public void drawChessBoard (int dlIndex, float size, float density, float angleOfRotation, Vector3f rotate, Vector3f translate, Vector3f scale, Vector3f color) {
        if (density < 2) {
            System.err.println("Desity must be greather than 2");
            density = 2;
        }
        if (size < 1) {
            System.err.println("Size of chesboard must be greather than 1");
            size = 1;
        }
        if (dlIndex < 1) {
            System.err.println("Display list index must be greather than 1. Programm automaticly sets new value to dlInde which is equal 1");
            dlIndex = 1;
        }

        float aspectStep = size / (density / 2);
        int x = 0, y = 0;

        glPushMatrix();
        {
            glGenLists(dlIndex);
            glNewList(dlIndex, GL_COMPILE);
            {
                glEnable(GL_NORMALIZE);
                glTranslated(translate.getX(), translate.getY(), translate.getZ());
                glRotatef(angleOfRotation, rotate.getX(), rotate.getY(), rotate.getZ());
                glScalef(scale.getX(), scale.getY(), scale.getZ());

                glBegin(GL_QUADS);
                {
                    for (float i = -size; i < size; i += aspectStep) {
                        for (float j = -size; j < size; j += aspectStep) {
                            glColor4f(color.getX(), color.getY(), color.getZ(), 0.75f);

                            if (Math.abs(x + y) % 2 == 1) {
                                // ToDo: Check this out
//                                glColor4f((color.getX() + 0.5f) % 1, (color.getY() + 0.5f) % 1, (color.getZ() + 0.5f) % 1, 0.50f);
                                glColor4f(0, 0, 0, 0.25f);
                            }

                            glNormal3f(.0f, 1.0f, .0f);
                            glVertex3f(i, 0.0f, j);
                            glVertex3f(i, 0.0f, j + aspectStep);
                            glVertex3f(i + aspectStep, 0.0f, j + aspectStep);
                            glVertex3f(i + aspectStep, 0.0f, j);
                            y++;
                        }
                        x++;
                    }
                }
                glEnd();
            }
            glEndList();
        }
        glPopMatrix();
    }


    public void drawSinCosSurface (int dlIndex, int size, float height, float smoothX, float smoothZ,  int lod, float angleOfRotation, Vector3f rotate, Vector3f translate, Vector3f scale, Vector3f color ) {
        if (size < 1) {
            System.err.println("Size of chesboard must be greather than 1");
            size = 1;
        }
        if (dlIndex < 1) {
            System.err.println("Display list index must be greather than 1. Programm automaticly sets new value to dlInde which is equal 1");
            dlIndex = 1;
        }
        if (height < 0) {
            System.err.println("Height must be grather than 0");
            height = 0;
        }
        if (lod < 1) {
            System.err.println("Level of detail must ne greather than 0");
            lod = 1;
        }
        if (smoothX < 0) {
            System.err.println("Smooth value must be greather than 0");
            smoothX = 0;
        }
        if (smoothZ < 0) {
            System.err.println("Smooth calue must be greather than 0");
            smoothZ = 0;
        }
        float[][] heightMap = sinCosSurfaceGenerator(size, height, smoothX, smoothZ);

        glPushMatrix();
        {
            glGenLists(dlIndex);
            glNewList(dlIndex, GL_COMPILE);
            {
                glTranslated(translate.getX(), translate.getY(), translate.getZ());
                glRotatef(angleOfRotation, rotate.getX(), rotate.getY(), rotate.getZ());
                glScalef(scale.getX(), scale.getY(), scale.getZ());
                glColor3f(color.getX(), color.getY(), color.getZ());

                for (int z = 0; z < heightMap.length - lod; z += lod) {
                    glBegin(GL_TRIANGLE_STRIP);
                    {
                        for (int x = 0; x < heightMap[z].length; x += lod) {
                            glVertex3f(x, heightMap[z][x], z);
                            glVertex3f(x, heightMap[z + lod][x], z + lod);
                        }
                    }
                    glEnd();
                }
            }
            glEndList();
        }
        glPopMatrix();

    }

    private float[][] sinCosSurfaceGenerator (int size, float height, float smoothX, float smoothZ) {
        float[][] heightMap = new float[size][size];
        float h;

        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                h = (float)(Math.sin(x / smoothX) * Math.cos(z / smoothZ)) * height;
                heightMap[x][z] = h;
            }
        }
        return heightMap;
    }

}

