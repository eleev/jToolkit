package main;

import jToolkit4FixedPipeline.camera.TCamera;
import jToolkit4FixedPipeline.common.*;
import jToolkit4FixedPipeline.image.Screenshoter;
import jToolkit4FixedPipeline.lighting.Lighting;
import jToolkit4FixedPipeline.primitives.Box;
import jToolkit4FixedPipeline.primitives.Surface;
import jToolkit4FixedPipeline.vector.Vector3f;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Astemoir Eleev
 * Date: 20/16/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class FixedPipeline {
    private int WINDOW_HEIGHT;
    private int WINDOW_WIDTH;
    private String WINDOW_TITLE;

    private boolean closeRequested = false;
    private final float ZNEAR = 0.15f;
    private final float ZFAR = 1000;

    private int ispolygonMode = 0;

    private ShaderProgram shader;
    private TCamera ct;
    private FPSmeter fps;

    private Box box;
    private Surface surface;
    float angle = .0f;
    private boolean isAnialisingEnabled = false;

    private float rotationAngle = 0f;

    public static boolean dead = false;
    public static int height = 0;
    public static boolean movingforward = false;
    public static boolean movingback = false;
    public static boolean movingsideright = false;
    public static boolean movingsideleft = false;
    public static boolean isJump = false;

    public FixedPipeline(final int WINDOW_HEIGHT, final int WINDOW_WIDTH, final String WINDOW_TITLE) {
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_TITLE = WINDOW_TITLE;

        ct = new TCamera(100.0f, 0.5f, 25.0f, 0.5f);
        fps = new FPSmeter();
        surface = new Surface();
    }

    public void run() {
//        createWindow();
        createWindowWithCustomPixelFormat();
        getInformationAboutSystem();
        System.out.println(getInformatrionAbountGraphics());


//        box = new Box(5, .0f, new Vector3f(),new Vector3f(.0f, 10.0f, 0.0f), new Vector3f().makeIdentity(), new Vector3f(1.0f, .0f, .0f));
//        box.setRotate(new Vector3f(1, 1, 1));
//        surface.drawChessBoard(2, 200, 128, .0f, new Vector3f(), new Vector3f(), new Vector3f().makeIdentity(), new Vector3f());

//        StencilBuffer stencilBuffer = new StencilBuffer();
//        AccumulationBuffer accumulationBuffer = new AccumulationBuffer();

        ShaderProgram shader = new ShaderProgram("jToolkit/shader programs/simple/sample01/simple.vsh",
                                    "jToolkit/shader programs/simple/sample01/simple.fsh");
//        int arrayIndex = GL30.glGenVertexArrays();
//        GL30.glBindVertexArray(arrayIndex);


        // data for describing materials
        float[] materialAmbient     =   new float[]{0.3f, 0.3f, 0.3f, 1.0f};
        float[] materialDiffuse     =   new float[]{0.3f, 0.3f, 0.3f, 1.0f};
        float[] materialSpecular    =   new float[]{0.3f, 0.3f, 0.3f, 1.0f};
        float[] materialEmission    =   new float[]{.1f, .1f, .1f, 1.0f};
        int materialShininees       =   64;

        // data for describing light source
        int lightNumLightPoint  =   0;
        int secondLightPoint    =   1;
        int thirdLightPoint     =   2;
        int fourthLightPoint    =   3;
        int fifthLightPoint     =   4;
        int sixthlightPoint     =   5;
        int seventhLightPoint   =   6;
        int eighthLightPoint    =   7;

        float[] lightAmbient    =   new float[]{0.05f, 0.05f, 0.05f, 1.0f};
        float[] lightDiffuse    =   new float[]{0.08f, 0.08f, 0.08f, 1.0f};
        float[] lightSpecular   =   new float[]{0.08f, 0.08f, 0.08f, 1.0f};
        float lightSporCutOff   =   30.0f;
        int lightSpotExp        =   2;

        // data for describing spot light
        float[] spotlightAngleOfRotation    =   new float[]{110.0f, 0.0f, 0.0f};
//        float[] spotlightPosition           =   new float[]{-10.0f, 170.0f, 80.1f, 1.0f};
        float[] spotlightPosition           =   new float[]{2.0f, 172.0f, 80.1f, 1.0f};
        float[] spolightDirection           =   new float[]{0.0f, 0.0f, 1.0f, 1.0f};
        boolean spotlightIsHidden           =   false;


        Lighting lighting = new Lighting();
        Lighting light2 = new Lighting();
        Lighting light3 = new Lighting();
        Lighting light4 = new Lighting();
        Lighting light5 = new Lighting();
        Lighting light6 = new Lighting();
        Lighting light7 = new Lighting();
        Lighting light8 = new Lighting();


//        ShadowMap.initShadow(1024, 1024, .5f, 16, 180, spotlightPosition);
//        ShadowMap.initGLForShadowMapping();

//        ShadowMap.startGenerate();
//        drawGeometry();
//        ShadowMap.endGenerate();


//        jToolkit4FixedPipeline.keyframeanim.Character character = new Character();
//        character.createCharacter();
//        character.loadTexture();

//        WalkingGoblinDemo wilkingGoblin = new WalkingGoblinDemo();

        while (!closeRequested) {
            initGL(Display.getWidth(), Display.getHeight(), ZNEAR, ZFAR);
            ct.eventHandler();
            pollInput();

//            lighting.initializeLighting(lightNumLightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff, lightSpotExp);
//            lighting.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            lighting.drawSpoltight(lightNumLightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);
//
//            light2.initializeLighting(secondLightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff - 1, lightSpotExp);
//            light2.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            light2.drawSpoltight(secondLightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);
//
//            light3.initializeLighting(thirdLightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff - 2, lightSpotExp);
//            light3.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            light3.drawSpoltight(thirdLightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);
//
//            light4.initializeLighting(fourthLightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff - 3, lightSpotExp);
//            light4.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            light4.drawSpoltight(fourthLightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);
//
//            light5.initializeLighting(fifthLightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff + 1, lightSpotExp);
//            light5.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            light5.drawSpoltight(fifthLightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);
//
//            light6.initializeLighting(sixthlightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff + 2, lightSpotExp);
//            light6.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            light6.drawSpoltight(sixthlightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);
//
//            light7.initializeLighting(seventhLightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff + 3, lightSpotExp);
//            light7.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            light7.drawSpoltight(seventhLightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);
//
//            light8.initializeLighting(eighthLightPoint, SHADE_MODEL.SMOOTH, lightAmbient, lightDiffuse, lightSpecular, lightSporCutOff + 4, lightSpotExp);
//            light8.initializeMatetial(materialAmbient, materialDiffuse, materialSpecular, materialEmission, materialShininees);
//            light8.drawSpoltight(eighthLightPoint, spotlightAngleOfRotation, spotlightPosition, spolightDirection, spotlightIsHidden);

//            wilkingGoblin.draw();

            /**
             * ToDo: Complete this example! Use that code from objective c project
             */

            shader.useShaderBegin();

            fps.fpsMeter();
            if (Display.wasResized()) {
                // if window has changed size than update geometries shadow
//                ShadowMap.initShadow(512, 512, 0.25f, 4, 180, spotlightPosition);
//                ShadowMap.initGLForShadowMapping();
//
//                ShadowMap.startGenerate();
//                drawGeometry();
//                ShadowMap.endGenerate();
                // end of updating

                initGL(Display.getWidth(), Display.getHeight(), ZNEAR, ZFAR);

                Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG,
                        "-----" + '\n' + "display was resized: \t" + "width: " + Display.getWidth() + '\t' + "height: " + Display.getHeight() + '\n' + "-----");
            }
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        cleanUp();
        System.exit(0);
    }

    /**
     * Architectural separations of geometric logic
     */
    private void drawDecorations() {
        glPushMatrix(); {
            glCallList(2);
        }
        glPopMatrix();
    }

    /**
     * There are all geometry which have to have shadows. Simply put there all geometry objects which are ENTITIES of scene
     */
    private void drawGeometry() {
        drawDecorations();

        glDisable(GL_BLEND);
        glPushMatrix(); {
            glTranslated(-30.0f, .0f, .0f);

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        glPushMatrix(); {
                            glTranslated(x * 10, y * 10, z * 10);

                            box.setAngleofRotation(45);
                            box.setRotate(new Vector3f(1, 1, 1));
                            box.setColor(new Vector3f(1, x / 2, y / 2));
                            box.draw();
                            glDisable(GL_CULL_FACE);
                        }
                        glPopMatrix();
                    }
                }

            }
        }
        glPopMatrix();

        glPushMatrix(); {
            glTranslated(25.0f, 10.0f, .0f);
            glColor3f(.0f, 1.0f, 1.0f);
            GLUT.glutSolidTeapot(10);

            glTranslated(-20.0f, 80.0f, 40.0f);
            glRotatef(45, 1, 1, 1);
            glScalef(4.0f, 2.0f, 6.0f);
            glColor3f(.5f, 1.0f, .0f);

            glLineWidth(5);
            GLUT.glutWireTorus(2, 5, 20, 20);
            glLineWidth(1);

        }
        glPopMatrix();

        glPushMatrix(); {
            glTranslated(.0f, 20.0f, 50.0f);
            glRotatef(-90, 0, 1, 0);
            glColor3f(1.0f, .0f, 0f);
            GLUT.glutSolidTeapot(10);
        }
        glPopMatrix();


        glFlush();
        glPopMatrix();

        glEnable(GL_BLEND);
    }

    private void initGL(final int width, final int height, final float nearZPlane, final float farZPlane) {
        glViewport(0, 0, width, height);
//        glMatrixMode(GL_PROJECTION);
//        glLoadIdentity();
//        GLU.gluPerspective(90.0f, ((float) width / (float) height), nearZPlane, farZPlane);

//        GLU.gluOrtho2D(10, 10, 0, -1);
//        glOrtho(0, 640, 0, 480, 1f, -1f);

//        GL11.glEnable(GL11.GL_TEXTURE_2D);

//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_ALPHA);

//        glEnable(GL_POINT_SMOOTH);
//        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
//        glEnable(GL_LINE_SMOOTH);
//        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
//        glEnable(GL_POLYGON_SMOOTH);
//        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
//        glEnable(GL13.GL_MULTISAMPLE);

        glClearColor(.1f, .1f, .1f, 0.0f);
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
//        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
//        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
//        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
//        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        glShadeModel(GL_SMOOTH);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT | GL_ACCUM_BUFFER_BIT);
//        glMatrixMode(GL_MODELVIEW);
//        glLoadIdentity();
//        glEnable(GL_CULL_FACE);
//        glFrontFace(GL_CCW);
    }

    private void pollInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    closeRequested = true;
                    Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG, "-----\nexit by user\n-----");
                }

                if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
                    System.out.println("-----\nf1 was pressed");

                    if (ispolygonMode != 1 && ispolygonMode != 2) {
                        ispolygonMode = 1;
                        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                        Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG, "line polygon mode is active\n-----");
                    } else if (ispolygonMode != 0 && ispolygonMode != 2) {
                        ispolygonMode = 2;
                        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                        Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG, "fill polygon mode is deactive\n-----");
                    } else if (ispolygonMode != 0 && ispolygonMode != 1) {
                        ispolygonMode = 0;
                        glPolygonMode(GL_FRONT_AND_BACK, GL_POINTS);
                        Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG, "point polygon mode is deactivate\n-----");
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_F2) {
                    Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG, "-----\nf2 was pressed");
                    new Screenshoter().saveScreenShot("DiplomaProject/res/screenshots/", Screenshoter.ImageType.PNG, Screenshoter.ColorType.ARGB);
                    Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG, "screenshot was saved in res/screenshots/ in png format\n-----");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
                    Logger.getLogger(FixedPipeline.class.getName()).log(Level.CONFIG, "-----\nf3 was pressed\n" + getInformatrionAbountGraphics() + "\n-----");
                }

                if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
                    isAnialisingEnabled =  !isAnialisingEnabled;
                }

                // keyboard handling for Goblin Demo
                if (Keyboard.isKeyDown(Keyboard.KEY_UP) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_LEFT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                    movingforward = true;
                    movingback = false;
                    movingsideleft = false;
                    movingsideright = false;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_LEFT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                    movingback = true;
                    movingsideleft = false;
                    movingsideright = false;
                    movingforward = false;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_DOWN) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                    movingsideleft = true;
                    movingforward = false;
                    movingback = false;
                    movingsideright = false;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_DOWN) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_LEFT) &&
                        !Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                    movingsideright = true;
                    movingback = false;
                    movingforward = false;
                    movingsideleft = false;
                }

            }
        }
    }

    private void createWindow() {
        try {
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setResizable(true);
            Display.setVSyncEnabled(true);
            Display.setTitle(WINDOW_TITLE);

            // usual way of creating display
//            Display.create();

            // non usual way of creating display with ACCUMULATION BUFFER and STENCIL BUFFER (finally!)
            Display.create(new PixelFormat().withDepthBits(24).withSamples(4).withSRGB(true).withStencilBits(8).withAccumulationBitsPerPixel(8));

        } catch (LWJGLException e) {
            Sys.alert("Error", "Initialization failed!\n" + e.getMessage());
            System.exit(0);
        }
    }

    private void createWindowWithCustomPixelFormat() {
        PixelFormat pixelFormat = new PixelFormat();
        // OpenGL 3.2 core profile
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true)
                .withDebug(true);

        try {
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setResizable(true);
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat, contextAtrributes);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void cleanUp() {
        Display.destroy();
    }

    private String getInformatrionAbountGraphics() {
        return "LWJGL: " + Sys.getVersion() + " / " + LWJGLUtil.getPlatformName()
                + "\nGL_VENDOR: " + glGetString(GL_VENDOR)
                + "\nGL_RENDERER: " + glGetString(GL_RENDERER)
                + "\nGL_VERSION: " + glGetString(GL_VERSION) + '\n';
    }

    public void getInformationAboutSystem() {
        StringBuilder info = new StringBuilder();
        info.append("java.home: ").append(System.getProperty("java.home")).append("\n");
        info.append("java.vendor: ").append(System.getProperty("java.vendor")).append("\n");
        info.append("java.vendor.url: ").append(System.getProperty("java.vendor.url")).append("\n");
        info.append("java.version: ").append(System.getProperty("java.version")).append("\n");
        info.append("java.vm.name: ").append(System.getProperty("java.vm.name")).append("\n");
        info.append("java.vm.specification.name: ").append(System.getProperty("java.vm.specification.name")).append("\n");
        info.append("java.vm.specification.vendor: ").append(System.getProperty("java.vm.specification.vendor")).append("\n");
        info.append("java.vm.specification.version: ").append(System.getProperty("java.vm.specification.version")).append("\n");
        info.append("java.vm.vendor: ").append(System.getProperty("java.vm.vendor")).append("\n");
        info.append("java.vm.version: ").append(System.getProperty("java.vm.version")).append("\n");
        info.append("os.arch: ").append(System.getProperty("os.arch")).append("\n");
        info.append("os.name: ").append(System.getProperty("os.name")).append("\n");
        info.append("os.version: ").append(System.getProperty("os.version")).append("\n");
//        info.append("java.class.path: ").append(System.getProperty("java.class.path")).append("\n");

        Logger.getLogger(FixedPipeline.class.getName()).log(Level.INFO, info.toString());
    }

}