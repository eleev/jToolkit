package jToolkit4FixedPipeline.camera;

/**
 * A 3D camera for LWJGL.
 *
 * @author Astemir Yeleev
 */
public interface CameraInterface {
    /**
     * Processes mouse input and converts it in to camera movement.
     */
    public void processMouse();

    /**
     * Processes mouse input and converts it in to camera movement.
     *
     * @param mouseSpeed the speed (sensitivity) of the mouse, 1.0 should suffice
     */
    public void processMouse(float mouseSpeed);

    /**
     * Processes mouse input and converts it into camera movement.
     *
     * @param mouseSpeed  the speed (sensitivity) of the mouse, 1.0 should suffice
     * @param maxLookUp   the maximum angle in degrees at which you can look up
     * @param maxLookDown the maximum angle in degrees at which you can look down
     */
    public void processMouse(float mouseSpeed, float maxLookUp, float maxLookDown);

    /**
     * Processes keyboard input and converts into camera movement.
     *
     * @param delta the elapsed time since the last frame update in milliseconds
     * @throws IllegalArgumentException if delta is 0 or delta is smaller than 0
     */
    public void processKeyboard(float delta);

    /**
     * Processes keyboard input and converts into camera movement.
     *
     * @param delta the elapsed time since the last frame update in milliseconds
     * @param speed the speed of the movement (normal = 1.0)
     * @throws IllegalArgumentException if delta is 0 or delta is smaller than 0
     */
    public void processKeyboard(float delta, float speed);

    /**
     * Processes keyboard input and converts into camera movement.
     *
     * @param delta  the elapsed time since the last frame update in milliseconds
     * @param speedX the speed of the movement on the x-axis (normal = 1.0)
     * @param speedY the speed of the movement on the y-axis (normal = 1.0)
     * @param speedZ the speed of the movement on the z-axis (normal = 1.0)
     * @throws IllegalArgumentException if delta is 0 or delta is smaller than 0
     */
    public void processKeyboard(float delta, float speedX, float speedY, float speedZ);

    /**
     * Move in the direction you're looking. That is, this method assumes a new coordinate system where the axis you're
     * looking down is the z-axis, the axis to your left is the x-axis, and the upward axis is the y-axis.
     *
     * @param dx the movement along the x-axis
     * @param dy the movement along the y-axis
     * @param dz the movement along the z-axis
     */
    public void moveFromLook(float dx, float dy, float dz);

    /**
     * Sets the position of the camera.
     *
     * @param x the x-coordinate of the camera
     * @param y the y-coordinate of the camera
     * @param z the z-coordinate of the camera
     */
    public void setPosition(float x, float y, float z);

    /**
     * Sets GL_PROJECTION to an orthographic projection matrix. The matrix mode will be returned it its previous value after execution.
     */
    public void applyOrthographicMatrix();

    /**
     * Enables or disables OpenGL states that will enhance the camera appearance.
     */
    public void applyOptimalStates();

    /**
     * Sets GL_PROJECTION to an perspective projection matrix. The matrix mode will be returned it its previous value after execution.
     */
    public void applyPerspectiveMatrix();

    /**
     * Applies the camera translations and rotations to GL_MODELVIEW.
     */
    public void applyTranslations();

    /**
     * Sets the rotation of the camera.
     *
     * @param pitch the rotation around the x-axis in degrees
     * @param yaw   the rotation around the y-axis in degrees
     * @param roll  the rotation around the z-axis in degrees
     */
    public void setRotation(float pitch, float yaw, float roll);

    /**
     * @return the x-coordinate of the camera
     */
    public float getX();

    /**
     * @return y the y-coordinate of the camera
     */
    public float getY();

    /**
     * @return the z-coordinate of the camera
     */
    public float getZ();

    /**
     * @return the pitch of the camera in degrees
     */
    public float getPitch();

    /**
     * @return the yaw of the camera in degrees
     */
    public float getYaw();

    /**
     * @return the roll of the camera in degrees
     */
    public float getRoll();

    /**
     * @return the fov of the camera in degrees in the y direction
     */
    public float getFieldOfView();

    /**
     * Sets the field of view angle in degrees in the y direction. Note that
     * this.applyPerspectiveMatrix() must be applied in order to see any difference.
     *
     * @param fov the field of view angle in degrees in the y direction
     */
    public void setFieldOfView(float fov);

    /**
     * Sets the aspect ratio of the camera. Note that, to see any effect, you must call applyPerspectiveMatrix
     * or applyOrthographicMatrix.
     *
     * @param aspectRatio the aspect ratio of the camera
     * @throws IllegalArgumentException if aspectRatio is 0 or less
     */
    public void setAspectRatio(float aspectRatio);

    /**
     * @return the aspect ratio of the camera
     */
    public float aspectRatio();

    /**
     * @return the distance from the camera to the near clipping pane
     */
    public float nearClippingPane();

    /**
     * @return the distance from the camera to the far clipping pane
     */
    public float farClippingPane();
}
