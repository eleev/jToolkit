package jToolkit4FixedPipeline.particle;

import jToolkit4FixedPipeline.image.reader.PNGLoader;
import jToolkit4FixedPipeline.primitives.Box;
import jToolkit4FixedPipeline.vector.Vector3f;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Astemir Yeleev
 */
public class ParticleSystem {
    private Vector3f positionOfEmitter;
    private Vector3f rotation;
    private Vector3f scale;
    private LinkedList<Particle> particles;
    private float angleOfRotaion;
    private int numOfParticles;
    private static int spriteIndex;
    private Box emitter = new Box();
    
    public ParticleSystem(int countOfPatricles, Vector3f positionOfEmitter, float angleofRotation, Vector3f rotation, Vector3f scale, String pathToImageOfParticle, int indexofTexture) {
        numOfParticles = countOfPatricles;
        spriteIndex = indexofTexture;
        this.angleOfRotaion = angleofRotation;
        this.positionOfEmitter = positionOfEmitter;
        particles = new LinkedList<Particle>();
        this.rotation = rotation;
        this.scale = scale;
        
        if (numOfParticles < 1) {
            numOfParticles = 0;
        } else {
            for (int i = 0; i < countOfPatricles; i++) {
                addParticle();
            }
        }
        
        PNGLoader dec = new PNGLoader(pathToImageOfParticle);
        ByteBuffer spriteBuffer = dec.getBuffer();
       
        glBindTexture(GL_TEXTURE_2D, spriteIndex);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, dec.getWidth(), dec.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, spriteBuffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }
    
    public void draw () {
        glPushMatrix();
        {
            glTranslatef(positionOfEmitter.getX(), positionOfEmitter.getY(), positionOfEmitter.getZ());
            glRotatef(angleOfRotaion, rotation.getX(), rotation.getY(), rotation.getZ());
            glScalef(scale.getX(), scale.getY(), scale.getZ());
            glDisable(GL_CULL_FACE);
            drawEmmitterPoint();

            glEnable(GL_TEXTURE_2D);
            addParticle();
            applyForce(new Vector3f(0.0f, 0.0f, 0.0f));

            glEnd();
            for (int i = 0; i < particles.size(); i++) {
                Particle p = particles.get(i);
                p.drawQuads();

                if (p.isDead()) {
                    particles.remove(i);
                }
            }

            glDisable(GL_TEXTURE_2D);
            glEnable(GL_CULL_FACE);
        }
        glPopMatrix();
    }
    
    public void applyForce(Vector3f direction) {
        for (Particle p : particles) {
            p.applyForce(direction);
        }
    }
    
    public int getCountOfParticles () {
        return particles.size();
    }
    
    private void drawEmmitterPoint () {
        emitter.draw();
    }
    
    private void addParticle() {
        particles.add(new Particle());
    }
}   
