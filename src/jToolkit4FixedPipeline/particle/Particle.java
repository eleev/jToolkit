package jToolkit4FixedPipeline.particle;

import jToolkit4FixedPipeline.primitives.Box;
import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * @author Astemir Yeleev
 */
public class Particle {
    private Vector3f position;
    private Vector3f velocity;
    private Vector3f acceleration;
    private Vector3f color;
    private int decay;
    private int lifespan;
    private int size;
    
    public Particle() {
        acceleration = new Vector3f(0.0f, 0.0f, 0.0f);
        color = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());
        position = new Vector3f(0.0f, 0.0f, 0.0f);
        velocity = new Vector3f((float)( -.2f + Math.random() * .4f), (float)(.01f + Math.random()), (float)(- .1f + Math.random() * .2f));
        this.size = 1;
        this.decay = 2;
        this.lifespan = 100;
    }
    
    public Particle(int lifespan, int size, Vector3f acceleration, Vector3f color, Vector3f position, Vector3f velocity) {
        this.acceleration = acceleration;
        this.color = color;
        this.position = position;
        this.velocity = velocity;
        this.size = size;
        this.decay = 1;
        this.lifespan = lifespan;
        
        if (this.lifespan < 0) {
            this.lifespan = 0;
        }
        if (this.size < 0) {
            this.size = 0;
        }
    }
    
    public void applyForce(Vector3f direction) {
        acceleration.add(direction);
    }
    
    public boolean isDead () {
        if (lifespan < 0.0f) {
            return true;
        } else {
            return false;
        }
    }
    
    public void drawQuads () {
        update();
        glPushMatrix();
        {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glColor4f(color.getX(), color.getY(), color.getZ(), 1.0f);
            
            glBegin(GL_TRIANGLE_STRIP);
            {
                glTexCoord3f(0, 0, 0);
                glVertex3f(position.getX(), position.getY(), position.getZ());
                glTexCoord3f(1, 0, 0);
                glVertex3f(position.getX() + size, position.getY(), position.getZ());
                glTexCoord3f(1, 1, 0);
                glVertex3f(position.getX() + size, position.getY() + size, position.getZ());
                glTexCoord3f(0, 1, 0);
                glVertex3f(position.getX(), position.getY() + size, position.getZ());
                glTexCoord3f(0, 0, 0);
                glVertex3f(position.getX(), position.getY(), position.getZ());
            }
            glEnd();
        }
        glPopMatrix();
    }
    
    public void drawPoints () {
        update();
        glPushMatrix();
        {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glColor4f(color.getX(), color.getY(), color.getZ(), 1.0f);

            glBegin(GL_POINTS);
            {
                glVertex3f(position.getX(), position.getY(), position.getZ());
            }
            glEnd();
        }
        glPopMatrix();
        
    }
    
    public void drawTriangles () {
        update();
        glPushMatrix();
        {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glColor4f(color.getX(), color.getY(), color.getZ(), 1.0f);
            
            glBegin(GL_TRIANGLES);
            {
                glTexCoord3f(0, 0, 0);
                glVertex3f(position.getX(), position.getY(), position.getZ());
                glTexCoord3f(1, 0, 0);
                glVertex3f(position.getX() - size, position.getY() + size, position.getZ());
                glTexCoord3f(0, 1, 0);
                glVertex3f(position.getX() + size, position.getY() + size, position.getZ());
            }
            glEnd();
        }
        glPopMatrix();
    }

    public void drawBoxes () {
        update();
        glPushMatrix();
        {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glColor4f(color.getX(), color.getY(), color.getZ(), 1.0f);
            new Box((float) Math.random(), .0f, new Vector3f().makeNull(), new Vector3f().makeIdentity(), new Vector3f().makeIdentity(), new Vector3f(1.0f, .0f, .0f)).draw();
        }
        glPopMatrix();

    }

    private void update () {
        velocity.add(acceleration);
        position.add(velocity);
        lifespan -= decay;
    }
    
    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public int getDecay() {
        return decay;
    }

    public void setDecay(int decay) {
        this.decay = decay;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }
    
}
