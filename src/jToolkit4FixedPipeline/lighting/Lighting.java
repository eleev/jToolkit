package jToolkit4FixedPipeline.lighting;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.Sphere;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class presets lighting. You can configurate light and put on the scene some numbers of
 * spotlights and set their position and orientation.
 * @author Astemir Yeleev
 */
public class Lighting {
    private float spotExp;

    /**
     * Think about ingration this addition functionality into this code
     * There are 3 different types of attenuation of light
     * @param numOfLightSource
     */
    public void setAttenuation (final int numOfLightSource) {

//        glLightf(GL_LIGHT0, GL_CONSTANT_ATTENUATION, 3.0f);
//        glLightf(GL_LIGHT0, GL_LINEAR_ATTENUATION, .01f);
//        glLightf(GL_LIGHT0, GL_QUADRATIC_ATTENUATION, 0.00005f);

    }

    /**
     * initialization method for lighting
     * @param ambient - component of ambient light
     * @param diffuse - component of diffuse light
     * @param specular - component of specular light
     */
    public void initializeLighting (int numOflightPoint, SHADE_MODEL model, float[] ambient, float[] diffuse, float[] specular, float spotCutOff, int spotExponent) {
        if (numOflightPoint < 0 || numOflightPoint > 7) {
            throw new IllegalArgumentException("Incorrect argument. You should set number between 0 and 7");
        }
        spotExp = spotExponent;
        
        glPushMatrix();
        glEnable(GL_LIGHTING);                                                  // enabling or disabling lighting
        glShadeModel(model.getShadeModel());                                            // choose shading model (FLAT, SMOOTH)

        FloatBuffer fbAmb = BufferUtils.createFloatBuffer(ambient.length);
        fbAmb.put(ambient);
        fbAmb.position(0);
        
        FloatBuffer fbDiff = BufferUtils.createFloatBuffer(diffuse.length);
        fbDiff.put(diffuse);
        fbDiff.position(0);
        
        FloatBuffer fbSpecular = BufferUtils.createFloatBuffer(specular.length);
        fbSpecular.put(specular);
        fbSpecular.position(0);

        switch (numOflightPoint) {
            case 0:
                glLight(GL_LIGHT0, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT0, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT0, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT0, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT0);
                break;
            case 1:
                glLight(GL_LIGHT1, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT1, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT1, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT1, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT1);
                break;
            case 2:
                glLight(GL_LIGHT2, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT2, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT2, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT2, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT2);
                break;
            case 3:
                glLight(GL_LIGHT3, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT3, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT3, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT3, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT3, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT3);
                break;
            case 4:
                glLight(GL_LIGHT4, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT4, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT4, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT4, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT4, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT4);
                break;
            case 5:
                glLight(GL_LIGHT5, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT5, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT5, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT5, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT5, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT5);
                break;
            case 6:
                glLight(GL_LIGHT6, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT6, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT6, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT6, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT6, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT6);
                break;
            case 7:
                glLight(GL_LIGHT7, GL_AMBIENT, fbAmb);
                glLight(GL_LIGHT7, GL_DIFFUSE, fbDiff);
                glLight(GL_LIGHT7, GL_SPECULAR, fbSpecular);
                glLightf(GL_LIGHT7, GL_SPOT_CUTOFF, spotCutOff);
                glLighti(GL_LIGHT7, GL_SPOT_EXPONENT, spotExponent);

                glEnable(GL_LIGHT7);
                break;
        }
                
        glPopMatrix();
    }
    
    /**
     * Spotlight which can be translate, rotate to any coordinates
     * @param angleOfRotation - x, y, z coordinates of rotation
     * @param position - x, y, z, w coordinates of position of light point
     * @param direction - x, y, z coordinates of points light direction
     * @param hidespotLight  - activate or deactivate rendering of graphics model for point of light 
     */
    public void drawSpoltight (int numOflightPoint, float[] angleOfRotation, float[] position, float[] direction, boolean hidespotLight) {
        if (numOflightPoint < 0 || numOflightPoint > 7) {
            throw new IllegalArgumentException("Incorrect argument. You should set number between 0 and 7");
        }
        float j = 0.0f;
        float[] lightPosition = {0.0f, 0.0f, 0.0f, 1.0f};
        
        glPushMatrix();
        glTranslatef(position[0], position[1], position[2]);
        
        glPushMatrix();
            glRotatef(angleOfRotation[0], 1.0f, 0.0f, 0.0f);
            glRotatef(angleOfRotation[1], 0.0f, 1.0f, 0.0f);
            glRotatef(angleOfRotation[2], 0.0f, 0.0f, 1.0f);
            
            FloatBuffer fbDirection = BufferUtils.createFloatBuffer(direction.length);
            fbDirection.put(direction);
            fbDirection.position(0);
            
            FloatBuffer fbPosition = BufferUtils.createFloatBuffer(lightPosition.length);
            fbPosition.put(lightPosition);
            fbPosition.position(0);
            
            switch (numOflightPoint) {
                case 0:
                    glLight(GL_LIGHT0, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT0, GL_SPOT_DIRECTION, fbDirection);
                    break;
                case 1:
                    glLight(GL_LIGHT1, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT1, GL_SPOT_DIRECTION, fbDirection);
                    break;
                case 2:
                    glLight(GL_LIGHT2, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT2, GL_SPOT_DIRECTION, fbDirection);
                    break;
                case 3:
                    glLight(GL_LIGHT3, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT3, GL_SPOT_DIRECTION, fbDirection);
                    break;
                case 4:
                    glLight(GL_LIGHT4, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT4, GL_SPOT_DIRECTION, fbDirection);
                    break;
                case 5:
                    glLight(GL_LIGHT5, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT5, GL_SPOT_DIRECTION, fbDirection);
                    break;
                case 6:
                    glLight(GL_LIGHT6, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT6, GL_SPOT_DIRECTION, fbDirection);
                    break;
                case 7:
                    glLight(GL_LIGHT7, GL_POSITION, fbPosition);
                    glLight(GL_LIGHT7, GL_SPOT_DIRECTION, fbDirection);
                    break;
            }
            glTranslatef(0.0f, 0.0f, 0.0f);
            
            if (!hidespotLight) {
                glPushAttrib(GL_LIGHTING_BIT);
                    glDisable(GL_LIGHTING);
                    glColor3f(1.0f, 0.0f, 0.0f);
                    
                    glBegin(GL_LINES);
                        for (float i = 0.0f; i < (2.2f * Math.PI); i += Math.PI / 4) {
                            j = i;
                            j += (float) (Math.PI / 4);
                            
                            glVertex3f(0.0f, 0.0f, 0.0f);
                            glVertex3f((float)Math.sin(i) / (spotExp / 100.0f * 2.0f), (float)Math.cos(i) / (spotExp / 100.0f * 2.0f), 10.0f);
                            glVertex3f((float)Math.sin(i) / (spotExp / 100.0f * 2.0f), (float)Math.cos(i) / (spotExp / 100.0f * 2.0f), 10.0f);
                            glVertex3f((float)Math.sin(j) / (spotExp / 100.0f * 2.0f), (float)Math.cos(j) / (spotExp / 100.0f * 2.0f), 10.0f);
                        }
                    glEnd();
                    
                    new Sphere().draw(2.0f, 10, 10);
                glPopAttrib();
            }
            
        glPopMatrix();
        glPopMatrix();
    }
    
    /**
     * Set material configuration. 
     * This method works a little bit slower than the same method which is overloaded, 
     * but in this method you can set many different configuration values
     * @param ambient 
     * @param diffuse
     * @param specular
     * @param shininees 
     */
    public void initializeMatetial (float[] ambient, float[] diffuse, float[] specular, float[] emission, int shininees) {
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
        
        FloatBuffer fbAmb = BufferUtils.createFloatBuffer(ambient.length);
        fbAmb.put(ambient);
        fbAmb.position(0);
        
        FloatBuffer fbDiff = BufferUtils.createFloatBuffer(diffuse.length);
        fbDiff.put(diffuse);
        fbDiff.position(0);

        FloatBuffer fbSpecular = BufferUtils.createFloatBuffer(specular.length);
        fbSpecular.put(specular);
        fbSpecular.position(0);

        FloatBuffer fbEmission = BufferUtils.createFloatBuffer(emission.length);
        fbEmission.put(emission);
        fbEmission.position(0);
        
        glMaterial(GL_FRONT, GL_AMBIENT, fbAmb);
        glMaterial(GL_FRONT, GL_DIFFUSE, fbDiff);
        glMaterial(GL_FRONT, GL_SPECULAR, fbSpecular);
        glMaterial(GL_FRONT, GL_EMISSION, fbEmission);
        glMateriali(GL_FRONT, GL_SHININESS, shininees);
    }
}
