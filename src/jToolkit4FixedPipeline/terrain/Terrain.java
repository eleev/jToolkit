package jToolkit4FixedPipeline.terrain;

import jToolkit4FixedPipeline.image.reader.HeightMapLoader;
import jToolkit4FixedPipeline.image.reader.PNGLoader;
import jToolkit4FixedPipeline.vector.Vector3f;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Astemir Eleev
 */
public class Terrain {
    private float[][] vertices;
    private Vector3f translate;
    private Vector3f scale;
    private int indexOfDisplayList;
    private int heightOfLookUp;
    private int widthOfLookUp;
    private ByteBuffer bbuffer;
    private int indexOfLookUpTexture;

    private int lod;                                // detail level of terrain
    private float aspectStep;
    private float max;                              // max height point of terrain

    public Terrain(String heightMapPath, Vector3f translate, Vector3f scale) {
        vertices = new HeightMapLoader(heightMapPath).getData();
        this.translate = translate;
        this.scale = scale;
    }

    public Terrain(String heightMapPath, String lookupPath, Vector3f translate, Vector3f scale, int indexOfLookUptexture) {
        this.translate = translate;
        this.scale = scale;
        
        vertices = new HeightMapLoader(heightMapPath).getData();
        PNGLoader decoder = new PNGLoader(lookupPath);
        
        bbuffer = decoder.getBuffer();
        heightOfLookUp = decoder.getHeight();
        widthOfLookUp = decoder.getWidth();    
        this.indexOfLookUpTexture = indexOfLookUptexture;
    }
    
    /**
     * This method renders and compiles to display list all geometry
     * @param indexOfDisplayList - some positive index
     * @param levelOfDetalization - must be between 1 and n. The higher the number the lower the detail and otherwise
     * @param density - level of sectors
     * @return index of compiled geometry
     */
    public int renderToDisplayListWithTrangleStrip(int indexOfDisplayList, int levelOfDetalization, final int density) {
        lod = levelOfDetalization;
        int dnsty = density;

        if (levelOfDetalization < 1) {
            lod = 1;
            Logger.getLogger(Terrain.class.getName()).log(Level.INFO, "Square of triangle cannot be equal value less than 1! Your mistake was corrected");
        }
        if (dnsty < 2) {
            dnsty = 2;
            Logger.getLogger(Terrain.class.getName()).log(Level.INFO, "Density has to be grater than 2");
        }

        glEnable(GL_NORMALIZE);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, indexOfLookUpTexture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, widthOfLookUp, heightOfLookUp, 0, GL_RGBA, GL_UNSIGNED_BYTE, bbuffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        this.indexOfDisplayList = glGenLists(indexOfDisplayList);

        max = vertices[0][0];
        aspectStep = vertices.length / (dnsty / 2);

        glNewList(this.indexOfDisplayList, GL_COMPILE);
        {
            glPushMatrix();
            {
                glTranslatef(translate.getX(), translate.getY(), translate.getZ());
                glScalef(scale.getX(), scale.getY(), scale.getZ());
                glDisable(GL_CULL_FACE);

                for (int z = 0; z < vertices.length - lod; z += lod) {

                    glBegin(GL_TRIANGLE_STRIP);
                    {
                        for (int x = 0; x < vertices[z].length; x += lod) {
                            float y = vertices[z][x];
                            float yt = vertices[z + lod][x];
                            if (y > max) max = y;

                            glVertex3f(x, y, z);
                            glVertex3f(x, yt, z + lod);
                        }
                    }
                    glEnd();
                }
                glEnable(GL_CULL_FACE);

//                List<VertexStorage> storage = calculateDataStrctrOfSectors(aspectStep, density);
//                storage = calculateDataStrctrOfSectors(aspectStep, density);
//                renderSector(storage, 2, lod, aspectStep);

//                renderSectorGrid(translate, new Vector3f(1.0f, .0f, .0f), lod, aspectStep, max);
            }
            glPopMatrix();
        }
        glEndList();
        glDisable(GL_TEXTURE_2D);

        return indexOfDisplayList;
    }


    public void callDisplayList () {
        glCallList(this.indexOfDisplayList);
    }

    public Vector3f getScale () {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getTranslate() {
        return translate;
    }

    public void setTranslate (final Vector3f translate) {
        this.translate = translate;
    }

    public float[][] getVertices() {
        return vertices;
    }

    public float getMax() {
        return max;
    }

    public float getAspectStep() {
        return aspectStep;
    }

    public int getLod() {
        return lod;
    }
}
