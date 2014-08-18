package jToolkit4ProgPipeline.file.camera;


import static org.lwjgl.opengl.GL11.*;

import jToolkit4FixedPipeline.camera.TCamera;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple 3D Camera class
 *
 * @author Astemir Eleev
 */
public class Camera
{
    // Field Of View
    private float fov;
    // Aspect Ratio
    private float aspect;
    // Near Plane
    private float zNear;
    // Far Plane
    private float zFar;

    // Projection matrix
    private Matrix4f projection;
    // View matrix
    private Matrix4f view;

    // Camera position
    private Vector3f position;
    // Camera rotation
    private Vector3f rotation;

    // Vectors for axes
    private Vector3f xAxis, yAxis, zAxis;

    // movement speed
    private float movementSpeed;

    private float pitch;
    private float yaw;

    private float pitchRadius = 90;
    private float mousevel = 0.5f;
    private float movevel = 0.5f;

    private boolean isEnabled;

    /**
     * Creates a simple 3D Perspective Camera.
     *
     * @param fov The field of view in degrees.
     * @param aspect The aspect ratio.
     * @param zNear The near clipping plane.
     * @param zFar The far clipping plane.
     */
    public Camera(float fov, float aspect, float zNear, float zFar, float movementSpeed)
    {
        // Set the local variables
        this.fov = fov;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;

        this.movementSpeed = movementSpeed;

        // Create matrices
        projection = Camera.makePerspective(fov, aspect, zNear, zFar);
        view = new Matrix4f();
        Matrix4f.setIdentity(view);

        // Initialize position and rotation vectors
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);

        // Create normalized axis vectors
        xAxis = new Vector3f(1, 0, 0);
        yAxis = new Vector3f(0, 1, 0);
        zAxis = new Vector3f(0, 0, 1);

        // Enable depth testing
        glEnable(GL_DEPTH_TEST);
    }

    public void apply() {
        view.setIdentity();
        update();

        // x vector or right vector
        Matrix4f.rotate((float) Math.toRadians(rotation.x), xAxis, view, view);
        // y vector or up vector
        Matrix4f.rotate((float) Math.toRadians(rotation.y), yAxis, view, view);
        // z vector or forward vector
        Matrix4f.rotate((float) Math.toRadians(rotation.z), zAxis, view, view);

        Matrix4f.translate(position, view, view);
    }

    private void update() {
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

            /**
             * This calculation was taken from Euler transformation matrix(Euler angles).
             */
            yaw += mousevel * (midX - tmpx);
            pitch += mousevel * (midY - tmpy);
            Mouse.setCursorPosition(midX, midY);

            /**
             * Determine where camera has to rotate depending on mouse movements.
             * There are some simple calculations.
             * Left, right, up and down rotations can be combined in runtime and as a result
             * camera can be rotated in any direction
             */
            if (Mouse.isGrabbed()) {
                float x = tmpx - Mouse.getX();
                // if mouse moved left
                if (x < 0) addRotation(0, (-1 + x) / 2, 0);
                // if mouse moved right
                if (x > 0) addRotation(0, (1 + x) / 2, 0);

                float y = tmpy - Mouse.getY();
                // if mouse moved down
                if (y < 0) addRotation((-1 + y) / 2, 0, 0);
                // if mouse moved up
                if (y > 0) addRotation((1 + y) / 2, 0, 0);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                move(movementSpeed, 1);
                moveup(movementSpeed, 1);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                move(-movementSpeed, 1);
                moveup(-movementSpeed, 1);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) move(movementSpeed, 0);
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) move(-movementSpeed, 0);
        }
    }

    public void addPosition(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    public void addPosition(Vector3f position) {
        addPosition(position.x, position.y, position.z);
    }

    public void addRotation(float rx, float ry, float rz) {
        rotation.x += rx;
        rotation.y += ry;
        rotation.z += rz;
    }

    public void addRotation(Vector3f rotation) {
        addRotation(rotation.x, rotation.y, rotation.z);
    }

    public void moveup(float amount, float direction) {
        position.y += amount * Math.sin(Math.toRadians(pitch + 180 * direction));
    }

    public void move(float amount, float direction) {
        position.z += amount * Math.sin(Math.toRadians(rotation.y + 90 * direction));
        position.x += amount * Math.cos(Math.toRadians(rotation.y + 90 * direction));
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setRotation(float rx, float ry, float rz) {
        rotation.x = rx;
        rotation.y = ry;
        rotation.z = rz;
    }

    public static Matrix4f makePerspective(float fov, float aspect, float nearPlane, float farPlane) {
        Matrix4f mat = new Matrix4f();

        float yScale = 1f / (float) Math.tan(Math.toRadians(fov / 2f));
        float xScale = yScale / aspect;
        float frustumLength = farPlane - nearPlane;

        mat.m00 = xScale;
        mat.m11 = yScale;
        mat.m22 = -((farPlane + nearPlane) / frustumLength);
        mat.m23 = -1;
        mat.m32 = -((2 * farPlane * nearPlane) / frustumLength);
        mat.m33 = 0;

        return mat;
    }

    public Matrix4f getNormalMatrix() {
        Matrix4f mat = getViewMatrix();
        mat.m30 = 0;    mat.m31 = 0;    mat.m32 = 0;    mat.m33 = 1;
        Matrix4f.invert(mat, mat);
        Matrix4f.transpose(mat, mat);
        return mat;
    }

    public Vector3f getRight() {
        return new Vector3f(view.m00, view.m01, view.m02);
    }

    public Vector3f getUp() {
        return new Vector3f(view.m10, view.m11, view.m12);
    }

    public Vector3f getForward() {
        return new Vector3f(view.m20, view.m21, -view.m22);
    }

    public float getFieldOfView()
    {
        return fov;
    }

    public float getAspectRatio()
    {
        return aspect;
    }

    public float getNearPlane()
    {
        return zNear;
    }

    public float getFarPlane()
    {
        return zFar;
    }

    public Matrix4f getProjectionMatrix()
    {
        return projection;
    }

    public Matrix4f getViewMatrix() {
        return view;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public Vector3f getRotation()
    {
        return rotation;
    }

    public void setRotation(Vector3f rotation)
    {
        this.rotation = rotation;
    }

}