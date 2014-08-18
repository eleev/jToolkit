package jToolkit4FixedPipeline.camera;

import jToolkit4FixedPipeline.vector.Vector3f;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class represents first person camera
 * 07.07.2012 17.00
 * @author Astemir Eleev
 */
public class TCamera {
    private float x, y, z;
    private float yaw, pitch;
    private float pitchRadius;
    private float movevel, moveacceleration, mousevel;
    private boolean isEnabled;

    public TCamera(float pitchRadius, float movevel, float moveacceleration, float mousevel) {
        this.pitchRadius = pitchRadius;
        this.movevel = movevel;
        this.moveacceleration = moveacceleration;
        this.mousevel = mousevel;
    }
    
    public void eventHandler () {
        if (Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
            isEnabled = true;
        } else if (Mouse.isButtonDown(1) || Mouse.isButtonDown(2)) {
            Mouse.setGrabbed(false);
            isEnabled = false;

            try {
                Mouse.create();
            } catch (LWJGLException e) {
                e.printStackTrace();
                Logger.getLogger(TCamera.class.getCanonicalName()).log(Level.WARNING, "mouse creation exception");
            }
        }

        if (isEnabled) {
            int midY = Display.getHeight() / 2;
            int midX = Display.getWidth() / 2;
            int tmpx = Mouse.getX();
            int tmpy = Mouse.getY();
            
            this.yaw += mousevel * (midX - tmpx);
            this.pitch += mousevel * (midY - tmpy);
            lockCamera();
            Mouse.setCursorPosition(midX, midY);

            if (Mouse.isButtonDown(0))
                Mouse.setGrabbed(true);
            else if (Mouse.isButtonDown(1))
                Mouse.setGrabbed(false);

            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                if (this.pitch != this.pitchRadius && this.pitch != -this.pitchRadius) {
                    moveCamera(movevel, 0.0f);
                    moveCameraUp(movevel, 0.0f);
                } 
                
            } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                if (this.pitch != this.pitchRadius && this.pitch != -this.pitchRadius) {
                    moveCamera(movevel, 180.0f);
                    moveCameraUp(movevel, 180.0f);
                } 
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                moveCamera(movevel, 90.0f);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                moveCamera(movevel, 270.0f);
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_W)) {
                 moveCamera(movevel + moveacceleration, 0.0f);
                 moveCameraUp(movevel, 0.0f);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_S)) {
                moveCamera(movevel + moveacceleration, 180.0f);
                moveCameraUp(movevel, 180.0f);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
                moveCamera(movevel + moveacceleration, 90.0f);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
                moveCamera(movevel + moveacceleration, 270.0f);
            }
        }
        GL11.glRotatef(-pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(-yaw, 0.0f, 1.0f, 0.0f);
        updateCamera();
    }
    
    private void moveCameraUp (float dist, float dir) {
        float rad = degreeToRadian(this.pitch + dir);
        this.y += Math.sin(rad) * dist; 
    }
    
    private void moveCamera (float dist, float dir) {
        float rad = degreeToRadian(this.yaw + dir);
        this.x -= Math.sin(rad) * dist; 
        this.z -= Math.cos(rad) * dist; 
    }
    
    private float degreeToRadian (float degree) {
        return (float)(degree * Math.PI / 180);
    }
    
    private void lockCamera () {
        if (this.pitch > this.pitchRadius) {
            this.pitch = this.pitchRadius;
        }
        if (this.pitch < -this.pitchRadius) {
            this.pitch = -this.pitchRadius;
        }
        if (this.yaw < 0.0f) {
            this.yaw += 360.0f;
        }
        if (this.yaw > 360.0f) {
            this.yaw -= 360.0f;
        }
    }
    
    private void updateCamera () {
        GL11.glTranslatef(-x, -y, -z);
    }
    
    public void setPitchRadius (float radius) {
        this.pitchRadius = radius;
    }
    
    public float getPitchRadius () {
        return this.pitchRadius;
    }
    
    public float getYawRadius () {
        return yaw;
    }
    
    public Vector3f getCurrentPosition () {
        return new Vector3f(x, y, z);
    }
}
