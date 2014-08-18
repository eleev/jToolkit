package main;

import jToolkit4ProgPipeline.file.camera.Camera;
import jToolkit4FixedPipeline.common.FPSmeter;
import jToolkit4FixedPipeline.common.ShaderProgram;
import jToolkit4ProgPipeline.file.loader.image.ImageLoader;
import jToolkit4ProgPipeline.file.loader.model.Loader;
import jToolkit4ProgPipeline.file.texture.Texture;
import jToolkit4ProgPipeline.file.utils.buffer.BufferTools;
import jToolkit4ProgPipeline.file.utils.matrixStack.GLStack4f;
import jToolkit4ProgPipeline.file.model.Model;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;


/**
 * Created by Astemir Eleev on 13/12/13.
 */
public class ProgrammablePipeline {
    private int WINDOW_HEIGHT;
    private int WINDOW_WIDTH;
    private String WINDOW_TITLE;

    private boolean closeRequested = false;
    private final float zNEAR = 0.1f;
    private final float zFAR = 100;
    private final int SYNC = 60;
    private final Vector4f color = new Vector4f(0, 0, 0, 1);

    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int modelMatrixLocation = 0;
    private int normalMatrixLocation = 0;
    private int lightPositionLocation = 0;

    private Matrix4f projectionMatrix = null;
    private Matrix4f modelMatrix = null;
    private Vector3f modelPos = null;
    private Vector3f modelAngle = null;
    private Vector3f modelScale = null;
    private FloatBuffer matrix44Buffer = null;

    private Camera camera;

    private boolean polygonModeState;
    private float delta = 0.01f;
    private float[] amplitude = new float[9];

    public ProgrammablePipeline(int WINDOW_WIDTH, int WINDOW_HEIGHT, String WINDOW_TITLE) {
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_TITLE = WINDOW_TITLE;
    }

    public void initGL(int viewWidth, int viewHeight, Vector4f color) {
        GL11.glViewport(0, 0, viewWidth, viewHeight);
        GL11.glClearColor(color.getX(), color.getY(), color.getZ(), color.getW());

        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        //GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glFrontFace(GL11.GL_CCW);

        GL11.glEnable(GL13.GL_MULTISAMPLE);
    }

    public void run() {
        display(3, 2); // OpenGL 3.2 core profile
        printInformationAboutSystem();
        printInformationAbountGraphics();

        modelPos = new Vector3f(0, -5, -5);
        modelAngle = new Vector3f(0, 0, 0);
        modelScale = new Vector3f(1.0f, 1f, 1.0f);

        FPSmeter fpSmeter = new FPSmeter();
        ShaderProgram shader = new ShaderProgram(
                "jToolkit/shader programs/New shaders/Waves/waves.vsh",
                "jToolkit/shader programs/New shaders/Waves/waves.fsh"
        );
        shader.useShaderBegin();

        projectionMatrixLocation = GL20.glGetUniformLocation(shader.getProgram(),"uvProjection");
        viewMatrixLocation = GL20.glGetUniformLocation(shader.getProgram(), "uvView");
        modelMatrixLocation = GL20.glGetUniformLocation(shader.getProgram(), "uvModel");

        normalMatrixLocation = GL20.glGetUniformLocation(shader.getProgram(), "uvNormal");
        lightPositionLocation = GL20.glGetUniformLocation(shader.getProgram(), "ufLightPosition");

        int timeLocation = GL20.glGetUniformLocation(shader.getProgram(), "ufTime");
        int uamplitude = GL20.glGetUniformLocation(shader.getProgram(), "amplitude");

        int uimageLocation = GL20.glGetUniformLocation(shader.getProgram(), "texture");


        projectionMatrix = Camera.makePerspective(60, (float)(Display.getWidth() / Display.getHeight()), 0.1f, 100);
        matrix44Buffer = BufferUtils.createFloatBuffer(16);

        camera = new Camera(90.0f, (float)Display.getWidth() / (float)Display.getHeight(), 0.1f, 10000, 0.1f);

        Loader loader = Loader.loadOBJModel("jToolkit/res/OBJ models/!Primitives/plane/planeUltraHd.obj");
        loader.initVBO();
        Model model = new Model(loader.getBuffer(), 0, 1, 2, GL15.GL_STATIC_DRAW);

        ImageLoader imageLoader = new ImageLoader("jToolkit/res/textures/png/bird.png");
        ByteBuffer imageData = imageLoader.getImageData();

        float rotationAngle = 0.25f;
        float time = 0.37f;
        //boolean accessor = true;
        amplitude = new float[]{.1f, .175f, .25f, .325f, .4f, .475f, .55f, .625f, 1.7f};

        GLStack4f glStack = new GLStack4f(shader.getProgram(), "uvModel");

        while(!closeRequested) {
            initGL(Display.getWidth(), Display.getHeight(), color);
            poolInput();
            camera.apply();

            modelMatrix = new Matrix4f();
            modelMatrix.setIdentity();

            modelAngle.setY(modelAngle.getY() + rotationAngle);
            if (modelAngle.getY() > 360) {
                modelAngle.setY(0);
            }

            // Scale, translate and rotate loader
            Matrix4f.scale(modelScale, modelMatrix, modelMatrix);
            Matrix4f.translate(modelPos, modelMatrix, modelMatrix);
//            Matrix4f.rotate((float)Math.toRadians(modelAngle.x), new Vector3f(0, 0, 1),
//                    modelMatrix, modelMatrix);
//            Matrix4f.rotate((float)Math.toRadians(modelAngle.y), new Vector3f(0, 1, 0),
//                    modelMatrix, modelMatrix);
//            Matrix4f.rotate((float)Math.toRadians(modelAngle.y), new Vector3f(1, 0, 0),
//                    modelMatrix, modelMatrix);

            GL20.glUniformMatrix4(projectionMatrixLocation, false, BufferTools.toFlippedFloatBuffer(camera.getProjectionMatrix()));
            GL20.glUniformMatrix4(viewMatrixLocation, false, BufferTools.toFlippedFloatBuffer(camera.getViewMatrix()));
            GL20.glUniformMatrix4(modelMatrixLocation, false, BufferTools.toFlippedFloatBuffer(modelMatrix));
            GL20.glUniformMatrix4(normalMatrixLocation, false, BufferTools.toFlippedFloatBuffer(camera.getNormalMatrix()));
            GL20.glUniform3(lightPositionLocation, BufferTools.toFlippedFloatBuffer(new Vector3f(camera.getForward().x, camera.getForward().y,camera.getForward().z)));

            GL20.glUniform1f(timeLocation, time);
            GL20.glUniform1(uamplitude, BufferTools.toFlippedFloatBuffer(amplitude));

            time += delta;

            glStack.push(modelMatrix); {

                model.bindVertexArray();
                model.draw(GL11.GL_TRIANGLES);
                model.unbindVertexArray();

            } glStack.pop();

            Texture texture = new Texture(imageData, imageLoader.getImageWidth(), imageLoader.getImageHeight(), imageLoader.hasAlpha(), GL11.GL_TEXTURE_2D);
            GL20.glUniform1i(uimageLocation, 0);

            if (Display.wasResized()) {
                WINDOW_WIDTH = Display.getWidth();
                WINDOW_HEIGHT = Display.getHeight();
                initGL(Display.getWidth(), Display.getHeight(), color);
            }
            Display.update();
            Display.sync(SYNC);
            fpSmeter.fpsMeter();
        }
        Display.destroy();
        cleanUp();
        System.exit(0);
    }


    private void poolInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    closeRequested = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_1) {
                    if (polygonModeState) {GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);}
                    else  {GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);}
                    polygonModeState = !polygonModeState;
                }

                if (Keyboard.getEventKey() == Keyboard.KEY_2) {
                   // modelScale.setY((modelScale.getY() + 0.05f) % 10);
                    amplitude[6] += 0.1f;
                    amplitude[7] += 0.1f;
                    amplitude[8] += 0.1f;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_3) {
                   // modelScale.setY(((modelScale.getY() - 0.05f) + 10) % 10);
                    amplitude[6] -= 0.1f;
                    amplitude[7] -= 0.1f;
                    amplitude[8] -= 0.1f;
                }

                if (Keyboard.getEventKey() == Keyboard.KEY_4) {
                    delta += .01f;
                } else if (Keyboard.getEventKey() == Keyboard.KEY_5) {
                    delta -= .01f;
                }
            }
        }
    }

    private void display(int versionGeneration, int versionUpdate) {
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAttribs = new ContextAttribs(versionGeneration, versionUpdate)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setResizable(true);
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat, contextAttribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void cleanUp() {
        Display.destroy();
    }

    private void printInformationAbountGraphics() {
        String info = "LWJGL: " + Sys.getVersion() + " / " + LWJGLUtil.getPlatformName()
                + "\nGL_VENDOR: " + GL11.glGetString(GL11.GL_VENDOR)
                + "\nGL_RENDERER: " + GL11.glGetString(GL11.GL_RENDERER)
                + "\nGL_VERSION: " + GL11.glGetString(GL11.GL_VERSION) + '\n';

        Logger.getLogger(ProgrammablePipeline.class.getCanonicalName()).log(Level.INFO, info);
    }

    private void printInformationAboutSystem() {
        StringBuilder info = new StringBuilder();
        info.append("java.vm.name: ").append(System.getProperty("java.vm.name")).append("\n");
        info.append("java.vm.specification.name: ").append(System.getProperty("java.vm.specification.name")).append("\n");
        info.append("java.vm.specification.vendor: ").append(System.getProperty("java.vm.specification.vendor")).append("\n");
        info.append("java.vm.specification.version: ").append(System.getProperty("java.vm.specification.version")).append("\n");
        info.append("java.vm.vendor: ").append(System.getProperty("java.vm.vendor")).append("\n");
        info.append("java.vm.version: ").append(System.getProperty("java.vm.version")).append("\n");
        info.append("os.arch: ").append(System.getProperty("os.arch")).append("\n");
        info.append("os.name: ").append(System.getProperty("os.name")).append("\n");
        info.append("os.version: ").append(System.getProperty("os.version")).append("\n");

        Logger.getLogger(FixedPipeline.class.getCanonicalName()).log(Level.INFO, info.toString());
    }

}

