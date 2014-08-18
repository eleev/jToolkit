package main;


import jToolkit4FixedPipeline.common.FPSmeter;
import jToolkit4FixedPipeline.common.ShaderProgram;
import jToolkit4ProgPipeline.file.camera.Camera;
import jToolkit4ProgPipeline.file.loader.model.Loader;
import jToolkit4ProgPipeline.file.model.Model;
import jToolkit4ProgPipeline.file.utils.buffer.BufferTools;
import jToolkit4ProgPipeline.file.utils.matrixStack.GLStack4f;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Astemir Eleev on 14/06/14.
 */
public class PPipeline implements Pipeline {
    private int WINDOW_WIDTH;
    private int WINDOW_HEIGHT;
    private String WINDOW_TITLE;
    private boolean closeRequested = false;
    private boolean polygonModeState;
    private final float zNEAR = 0.1f;
    private final float zFAR = 100;
    private final int SYNC = 60;
    private final Vector4f color = new Vector4f(0, 0, 0, 1);

    private FPSmeter fpSmeter;
    private ShaderProgram shaderProgram;
    private Camera camera;
    private Model model;
    private Model plane;
    private GLStack4f stack4f;

    // uniforms
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int modelMatrixLocation;

    private Matrix4f modelMatrix = new Matrix4f();
    private Matrix4f modelTwoMatrix = new Matrix4f();
    private Vector3f modelPos = null;
    private Vector3f modelTwoPos = null;

    public PPipeline (final int windowWidth, final int windowHeight, final String windowTitle) {
        WINDOW_WIDTH = windowWidth;
        WINDOW_HEIGHT = windowHeight;
        WINDOW_TITLE = windowTitle;
    }

    @Override
    public void init() {
        fpSmeter = new FPSmeter();
        shaderProgram = new ShaderProgram(
                "jToolkit/shader programs/New shaders/Sample 01/vertex.vsh",
                "jToolkit/shader programs/New shaders/Sample 01/fragment.fsh"
        );
        shaderProgram.printVertexShaderCompilationStatus();
        shaderProgram.printFragmentShaderCompilationStatus();

        camera = new Camera(90.0f, (float)Display.getWidth() / (float)Display.getHeight(), zNEAR, zFAR, 0.1f);

        Loader loader = Loader.loadOBJModel("jToolkit/res/OBJ models/!Primitives/quad/quadSimple.obj");
        loader.initVBO();
        model = new Model(loader.getBuffer(), 0, 1, 2, GL15.GL_STATIC_DRAW);

        Loader planeLoader = Loader.loadOBJModel("jToolkit/res/OBJ models/!Primitives/susanne.obj");
        planeLoader.initVBO();

        plane = new Model(planeLoader.getBuffer(), 0, 1, 2, GL15.GL_STATIC_DRAW);


        int shaderProgramIndex = shaderProgram.getProgram();
        projectionMatrixLocation = GL20.glGetUniformLocation(shaderProgramIndex,"uvProjection");
        viewMatrixLocation = GL20.glGetUniformLocation(shaderProgramIndex, "uvView");
        modelMatrixLocation = GL20.glGetUniformLocation(shaderProgramIndex, "uvModel");

        stack4f = new GLStack4f(shaderProgramIndex, "uvModel");

        modelPos = new Vector3f(0, 0, 0);
        modelTwoPos = new Vector3f(0, 3, 0);
    }

    @Override
    public void initGL(int viewWidth, int viewHeight, Vector4f color) {
        GL11.glViewport(0, 0, viewWidth, viewHeight);
        GL11.glClearColor(color.getX(), color.getY(), color.getZ(), color.getW());

        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glFrontFace(GL11.GL_CCW);

        GL11.glEnable(GL13.GL_MULTISAMPLE);
    }

    @Override
    public void run() {
        display(3, 2, true, true, true); // OpenGL 3.2 & 4.1 (core profile) are supported
        printInformationAboutSystem();
        printInformationAbountGraphics();

        init();

        while (!closeRequested) {
            initGL(Display.getWidth(), Display.getHeight(), color);
            poolInput();
            camera.apply();

            renderBegin();
            renderEnd();

            if (Display.wasResized()) {
                WINDOW_WIDTH = Display.getWidth();
                WINDOW_HEIGHT = Display.getHeight();
                initGL(Display.getWidth(), Display.getHeight(), color);
            }
            Display.update();
            Display.sync(SYNC);

            fpSmeter.fpsMeter();
            WINDOW_TITLE = Long.toString(fpSmeter.getFps());
            Display.setTitle(WINDOW_TITLE);
        }
        Display.destroy();
        cleanUp();
        System.exit(0);
    }

    @Override
    public void renderBegin() {
        shaderProgram.useShaderBegin();

        GL20.glUniformMatrix4(projectionMatrixLocation, false, BufferTools.toFlippedFloatBuffer(camera.getProjectionMatrix()));
        GL20.glUniformMatrix4(viewMatrixLocation, false, BufferTools.toFlippedFloatBuffer(camera.getViewMatrix()));

//        modelMatrix.setIdentity();
//        Matrix4f.translate(modelPos, modelMatrix, modelMatrix);
//        GL20.glUniformMatrix4(modelMatrixLocation, false, BufferTools.toFlippedFloatBuffer(modelMatrix));
//        model.bindVertexArray();
//        model.draw(GL11.GL_TRIANGLES);
//        model.unbindVertexArray();

//        modelMatrix.setIdentity();
//        Matrix4f.translate(modelTwoPos, modelMatrix, modelMatrix);
//        GL20.glUniformMatrix4(modelMatrixLocation, false, BufferTools.toFlippedFloatBuffer(modelMatrix));
//        plane.bindVertexArray();
//        plane.draw(GL11.GL_TRIANGLES);
//        plane.unbindVertexArray();


        modelMatrix.setIdentity();
        modelTwoMatrix.setIdentity();
        Matrix4f.translate(modelTwoPos, modelMatrix, modelMatrix);
//        modelTwoPos.x -= 0.01f;

        Matrix4f.rotate((float)Math.toRadians(modelPos.x),  new Vector3f(1, 0, 0), modelMatrix, modelMatrix);
        Matrix4f.rotate((float)Math.toRadians(modelPos.y),  new Vector3f(0, 1, 0), modelMatrix, modelMatrix);
        Matrix4f.rotate((float)Math.toRadians(modelPos.z),  new Vector3f(0, 0, 1), modelMatrix, modelMatrix);

        modelPos.z %= 360;
//        modelPos.z++;

        stack4f.push(modelMatrix); {
//        GL20.glUniformMatrix4(modelMatrixLocation, false, BufferTools.toFlippedFloatBuffer(modelMatrix));
            model.bindVertexArray();
            model.draw(GL11.GL_TRIANGLES);
            model.unbindVertexArray();

            Matrix4f.translate(new Vector3f(0, 5, 0), modelTwoMatrix, modelTwoMatrix);
            //        modelTwoPos.z += 0.01f;
//            Matrix4f.rotate((float)Math.toRadians(modelTwoPos.x),  new Vector3f(1, 0, 0), modelTwoMatrix, modelTwoMatrix);
//            Matrix4f.rotate((float)Math.toRadians(modelTwoPos.y),  new Vector3f(0, 1, 0), modelMatrix, modelMatrix);
//            Matrix4f.rotate((float)Math.toRadians(modelTwoPos.z),  new Vector3f(0, 0, 1), modelTwoMatrix, modelTwoMatrix);
//
//            modelTwoPos.z %= 360;
//            modelTwoPos.z--;

//            modelTwoMatrix.setIdentity();
            Matrix4f.mul(modelMatrix, modelTwoMatrix, modelTwoMatrix);

            stack4f.push(modelTwoMatrix); {
//        GL20.glUniformMatrix4(modelMatrixLocation, false, BufferTools.toFlippedFloatBuffer(modelMatrix));
                plane.bindVertexArray();
                plane.draw(GL11.GL_TRIANGLES);
                plane.unbindVertexArray();

            }
            stack4f.pop();

        }
        stack4f.pop();



//        for (int x = 0; x < 20; x += 3) {
//            for (int y = 0 ; y < 20; y += 3) {
//                modelMatrix.setIdentity();
//                Matrix4f.translate(new Vector3f(x, (float)(Math.sin(x) * Math.cos(y)), y), modelMatrix, modelMatrix);
//                GL20.glUniformMatrix4(modelMatrixLocation, false, BufferTools.toFlippedFloatBuffer(modelMatrix));
//                plane.bindVertexArray();
//                plane.draw(GL11.GL_TRIANGLES);
//                plane.unbindVertexArray();
//            }
//        }
    }

    @Override
    public void renderEnd() {
        shaderProgram.useShaderEnd();
    }

    @Override
    public void poolInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    closeRequested = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_1) {
                    if (polygonModeState) {
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                    } else {
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                    }
                    polygonModeState = !polygonModeState;
                }
            }
        }
    }

    @Override
    public void display(int versionGeneration, int versionUpdate, boolean forwardCompatible, boolean coreProfile, boolean debug) {
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAttribs = new ContextAttribs(versionGeneration, versionUpdate)
                .withForwardCompatible(forwardCompatible)
                .withProfileCore(coreProfile)
                .withDebug(debug);

        try {
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setResizable(true);
            Display.setVSyncEnabled(true);
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat, contextAttribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void cleanUp() {
        Display.destroy();
    }

    @Override
    public void printInformationAbountGraphics() {
        String info = "LWJGL: " + Sys.getVersion() + " / " + LWJGLUtil.getPlatformName()
                + "\nGL_VENDOR: " + GL11.glGetString(GL11.GL_VENDOR)
                + "\nGL_RENDERER: " + GL11.glGetString(GL11.GL_RENDERER)
                + "\nGL_VERSION: " + GL11.glGetString(GL11.GL_VERSION) + '\n';

        Logger.getLogger(ProgrammablePipeline.class.getCanonicalName()).log(Level.INFO, info);
    }

    @Override
    public void printInformationAboutSystem() {
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
