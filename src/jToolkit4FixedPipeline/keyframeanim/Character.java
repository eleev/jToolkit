package jToolkit4FixedPipeline.keyframeanim;


import static org.lwjgl.opengl.ARBMultitexture.GL_TEXTURE0_ARB;
import static org.lwjgl.opengl.ARBMultitexture.glActiveTextureARB;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import jToolkit4FixedPipeline.image.texture.TextureUploader;
import jToolkit4FixedPipeline.object.datastructure.*;
import jToolkit4FixedPipeline.object.datastructure.Face;
import jToolkit4FixedPipeline.object.instruments.OBJloader;
import jToolkit4FixedPipeline.vector.Vector2f;
import jToolkit4FixedPipeline.vector.Vector3f;
import jToolkit4FixedPipeline.vector.Vector4f;
import main.FixedPipeline;
import org.lwjgl.opengl.Display;

/**
 * ToDo: Change hardcoded paths! Further improvement is to convert this class as a generic, weakly connected code
 * @author Astemir Eleev
 */
public class Character  {
	private int texture2; // char texture id
	private int texture3; // eyes texture id
	private String tex2 = "jToolkit/res/gobilnlwjglchar.png";
    private String tex3 = "jToolkit/res/gobilnlwjgleyes.png";
    private int displaylistch;
    private int displaylistchar;
    private int displaylistcharj1;
    private int displaylistcharj2;
    private int displaylistchar6;
    private int displaydead;
    private int displaydeadeye;
    private int[] displaylist;
    private int[] displaylistside;

    private OBJloader obj;
    private static String locj1 = "jToolkit/res/lwjglcharactergoblin18.obj";
    private static String locj2 = "jToolkit/res/lwjglcharactergoblin13.obj";
    private static String locg = "jToolkit/res/lwjglcharactergoblin10_000001.obj";
    private static String locge = "jToolkit/res/lwjglcharactergoblin12.obj";
	private Model m = null;

    private int motioncounter = 0;
    private int motioncounterback = 20;
    private int motionsideright = 0;
    private int motionsideleft = 9;

    private Vector3f position = new Vector3f();
    private float rotationAngle;
    private Vector3f rotation = new Vector3f();
    private Vector3f scale = new Vector3f();
    private Vector4f color = new Vector4f();


	public Character() {
		loadTexture();
	}

    /**
     * This methods builds the character.
     * It catches call back from main pipeline
     */
	public void build() {
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);

		//glCallList(displaylistchar);

		glBindTexture(GL_TEXTURE_2D, texture3);

        if (FixedPipeline.dead == false ) {
		    glCallList(displaylistchar6);
		} else {
			if (FixedPipeline.height == 0)
			glCallList(displaydeadeye);
		}
        glBindTexture(GL_TEXTURE_2D, texture2);
		if (FixedPipeline.movingforward | FixedPipeline.movingback | FixedPipeline.movingsideright | FixedPipeline.movingsideleft  && !FixedPipeline.isJump && FixedPipeline.height == 0 & FixedPipeline.dead == false) {

			if (FixedPipeline.movingforward)
			    glCallList(displaylist[motioncounter]);

			if (FixedPipeline.movingback)
				glCallList(displaylist[motioncounterback]);

			if (FixedPipeline.movingsideright)
				glCallList(displaylistside[motionsideright]);

			if (FixedPipeline.movingsideleft)
				glCallList(displaylistside[motionsideleft]);
		}else {
			if (FixedPipeline.height == 0 & FixedPipeline.dead == false) {
			    glCallList(displaylistchar);

			}else {
				if (FixedPipeline.height > 0 & FixedPipeline.height < 3 & FixedPipeline.dead == false) {
					glCallList(displaylistcharj1);
				} else {
				    if (FixedPipeline.dead == false) {
				        //glCallList(displaylistcharj2);
				        glCallList(displaylistcharj2);
				    }
				}
			}

			if (FixedPipeline.dead & FixedPipeline.height ==0) {
				glCallList(displaydead);
			} else {
				if (FixedPipeline.dead & FixedPipeline.height > 0) glCallList(displaylistcharj2);
			}

		}

	}

    /**
     * This method responds on call - back from the main FixedPipeline
     */
    public void motionListener () {
        if (motioncounter == 20)
            motioncounter = 0;

        ++motioncounter;

        if (motioncounterback == 0)
            motioncounterback = 20;

        --motioncounterback;

        if (motionsideright == 9)
            motionsideright = 0;

        ++motionsideright;

        if (motionsideleft == 0)
            motionsideleft = 9;

        --motionsideleft;

    }

    /**
     * Resource dealloc
     */
	public void destroy() {
		
		glDeleteTextures(texture2);
		glDeleteTextures(texture3);
		glDeleteLists(displaylistchar6, 1);
		glDeleteLists(displaylistchar, 1);
		glDeleteLists(displaylistcharj1, 1);
		glDeleteLists(displaylistcharj2, 1);

		for (int i = 0;i < displaylist.length;i++) {
			glDeleteLists(displaylist[i], 1);
		}
		for (int i = 0;i < displaylistside.length;i++) {
			glDeleteLists(displaylistside[i], 1);
		}

	}


    /**
     * This method loads obj file and put coords together as triangles
     * @param location - physical location of file
     */
	public void loadFrame(String location) {
        try {
			m = OBJloader.loadModel(location);
		} catch (FileNotFoundException e) {
            Logger.getLogger(Character.class.getCanonicalName()).log(Level.WARNING, "Loading model exception", e);
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
            Logger.getLogger(Character.class.getCanonicalName()).log(Level.WARNING, "IO exception", e);
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		glBegin(GL_TRIANGLES);
		for (Face face : m.faces) {
			Vector2f t1 = m.textures.get((int) face.textures.x - 1);
			glTexCoord2f(t1.x, 1 - t1.y);
			Vector3f n1 = m.normals.get((int) face.normals.x - 1);
			glNormal3f(n1.x, n1.y, n1.z);
			Vector3f v1 = m.vertices.get((int) face.vertex.x - 1);
			glVertex3f(v1.x, v1.y, v1.z);
			Vector2f t2 = m.textures.get((int) face.textures.y - 1);
			glTexCoord2f(t2.x, 1 - t2.y);
			Vector3f n2 = m.normals.get((int) face.normals.y - 1);
			glNormal3f(n2.x, n2.y, n2.z);
			Vector3f v2 = m.vertices.get((int) face.vertex.y - 1);
			glVertex3f(v2.x, v2.y, v2.z);
			Vector2f t3 = m.textures.get((int) face.textures.z - 1);
			glTexCoord2f(t3.x, 1 - t3.y);
			Vector3f n3 = m.normals.get((int) face.normals.z - 1);
			glNormal3f(n3.x, n3.y, n3.z);
			Vector3f v3 = m.vertices.get((int) face.vertex.z - 1);
			glVertex3f(v3.x, v3.y, v3.z);

        }
		glEnd();
	}

    /**
     * Wrapper under build() method. It gives more control
     */
    public void draw () {
        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glRotatef(rotationAngle, rotation.getX(), rotation.getY(), rotation.getZ());
            glScalef(scale.getX(), scale.getY(), scale.getZ());
            glColor4f(color.getX(), color.getY(), color.getZ(), color.getW());

            // [self build]. It happens when you love 2 languages :)
            this.build();
        }
        glPopMatrix();
    }

    /**
     * ToDo: There are some problems with architecture. Change all the lines which have HARDCODED PATHS
     * Actually it's better to add for cycle for adding frame animation fragments
     */
    public void createCharacter() {

		// load stand frame
		displaylistchar = glGenLists(1);
		glNewList(displaylistchar, GL_COMPILE);
		loadFrame(locg);
		glEndList();
		
		// load eye frame
		displaylistchar6 = glGenLists(1);
		glNewList(displaylistchar6, GL_COMPILE);
		loadFrame(locge);
		glEndList();
		
		// load two frames for jump
		displaylistcharj2 = glGenLists(1);
		glNewList(displaylistcharj2, GL_COMPILE);
		loadFrame(locj2);
		glEndList();
		Display.update();
		displaylistcharj1 = glGenLists(1);
		glNewList(displaylistcharj1, GL_COMPILE);
		loadFrame(locj1);
		glEndList();
		
		displaydead = glGenLists(1);
		glNewList(displaydead, GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblindead.obj");
		glEndList();
		
		displaydeadeye = glGenLists(1);
		glNewList(displaydeadeye, GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblindeadeye.obj");
		glEndList();
		
		
		// load frames for walking sideways
		displaylistside = new int[10];
		displaylistside[0] = glGenLists(1);
		glNewList(displaylistside[0], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin17_000001.obj");
		glEndList();
		displaylistside[1] = displaylistside[0];
		displaylistside[2] = glGenLists(1);
		glNewList(displaylistside[2], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin17_000002.obj");
		glEndList();
		displaylistside[3] = displaylistside[2];
		displaylistside[4] = glGenLists(1);
		glNewList(displaylistside[4], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin17_000003.obj");
		glEndList();
		displaylistside[5] = displaylistside[4];
		displaylistside[6] = glGenLists(1);
		glNewList(displaylistside[6], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin17_000004.obj");
		glEndList();
		displaylistside[7] = displaylistside[6];
		displaylistside[8] = displaylistside[0];
		displaylistside[9] = displaylistside[8];
		
		//load frames for walk
		displaylist = new int[21];
		displaylist[0] = glGenLists(1);
		glNewList(displaylist[0], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000005.obj");
		glEndList();
		displaylist[1] = glGenLists(1);
		glNewList(displaylist[1], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000006.obj");
		glEndList();
		displaylist[2] = glGenLists(1);
		glNewList(displaylist[2], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000007.obj");
		glEndList();
		displaylist[3] = glGenLists(1);
		glNewList(displaylist[3], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000008.obj");
		glEndList();
		displaylist[4] = glGenLists(1);
		glNewList(displaylist[4], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000009.obj");
		glEndList();
		displaylist[5] = glGenLists(1);
		glNewList(displaylist[5], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000010.obj");
		glEndList();
		displaylist[6] = glGenLists(1);
		glNewList(displaylist[6], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000011.obj");
		glEndList();
		displaylist[7] = glGenLists(1);
		glNewList(displaylist[7], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000012.obj");
		glEndList();
		displaylist[8] = glGenLists(1);
		glNewList(displaylist[8], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000013.obj");
		glEndList();
		displaylist[9] = glGenLists(1);
		glNewList(displaylist[9], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000014.obj");
		glEndList();
		displaylist[10] = glGenLists(1);
		glNewList(displaylist[10], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000015.obj");
		glEndList();
		displaylist[11] = glGenLists(1);
		glNewList(displaylist[11], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000016.obj");
		glEndList();
		displaylist[12] = glGenLists(1);
		glNewList(displaylist[12], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000017.obj");
		glEndList();
		displaylist[13] = glGenLists(1);
		glNewList(displaylist[13], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000018.obj");
		glEndList();
		displaylist[14] = glGenLists(1);
		glNewList(displaylist[14], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000019.obj");
		glEndList();
		displaylist[15] = glGenLists(1);
		glNewList(displaylist[15], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000020.obj");
		glEndList();
		displaylist[16] = glGenLists(1);
		glNewList(displaylist[16], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000021.obj");
		glEndList();
		displaylist[17] = glGenLists(1);
		glNewList(displaylist[17], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000022.obj");
		glEndList();
		displaylist[18] = glGenLists(1);
		glNewList(displaylist[18], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000023.obj");
		glEndList();
		displaylist[19] = glGenLists(1);
		glNewList(displaylist[19], GL_COMPILE);
		loadFrame("jToolkit/res/lwjglcharactergoblin11_000024.obj");
		glEndList();
		displaylist[20] = displaylist[0];

	}

	public void loadTexture() {
		// loader for textures
		BufferedImage image2 = TextureUploader.loadImage(tex2);
		texture2 = TextureUploader.loadTexture(image2);
		
		BufferedImage image3 = TextureUploader.loadImage(tex3);
		texture3 = TextureUploader.loadTexture(image3);
	}


    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "Character{" +
                "texture2=" + texture2 +
                ", texture3=" + texture3 +
                ", tex2='" + tex2 + '\'' +
                ", tex3='" + tex3 + '\'' +
                ", displaylistch=" + displaylistch +
                ", displaylistchar=" + displaylistchar +
                ", displaylistcharj1=" + displaylistcharj1 +
                ", displaylistcharj2=" + displaylistcharj2 +
                ", displaylistchar6=" + displaylistchar6 +
                ", displaydead=" + displaydead +
                ", displaydeadeye=" + displaydeadeye +
                ", displaylist=" + Arrays.toString(displaylist) +
                ", displaylistside=" + Arrays.toString(displaylistside) +
                ", obj=" + obj +
                ", m=" + m +
                ", motioncounter=" + motioncounter +
                ", motioncounterback=" + motioncounterback +
                ", motionsideright=" + motionsideright +
                ", motionsideleft=" + motionsideleft +
                ", position=" + position +
                ", rotationAngle=" + rotationAngle +
                ", rotation=" + rotation +
                ", scale=" + scale +
                ", color=" + color +
                '}';
    }
}
