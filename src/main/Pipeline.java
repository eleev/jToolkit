package main;

import org.lwjgl.util.vector.Vector4f;

/**
 * Created by Astemir Eleev on 14/06/14.
 */
public interface Pipeline {

    /**
     * Here all the classes and resources that are going to be used in run() method must be initialized
     */
    public void init();

    /**
     * This method initializes the base GL state
     * @param viewWidth - view area width
     * @param viewHeight - view area height
     * @param color - color that will be used as a base background when the screen will be updated
     */
    public void initGL(int viewWidth, int viewHeight, Vector4f color);

    /**
     * The main OpenGL rendering loop. Here all the "magic" happens
     */
    public void run();

    /**
     * The beginning of rendering stage must be put into this method, then this method will be invoked by run() to render.
     */
    public void renderBegin();

    /**
     * Things that must be invoked after the renderBegin() method must be put here
     */
    public void renderEnd();

    /**
     * All the keyboard, mouse and etc. events must be handled here
     */
    public void poolInput();

    /**
     * Initializes the base state of the OpenGL context and pixel format of the display. It defines what
     * OpenGL's profile will be used e.g. if you put as versionGeneration value 1 and as versionUpdate value 1 as well
     * the OpenGL's state machine will be 1.1 which means no programmable features, just old fixed pipeline
     * @param versionGeneration - version that defines what generation of OpenGL will be used (e.g. 1, 2, 3, 4 (current version))
     * @param versionUpdate - it defines which one of a generation updates will be used (e.g. 1.1, 1.2, 1.3 ect.)
     */
    public void display(int versionGeneration, int versionUpdate, boolean forwardCompatible, boolean coreProfile, boolean debug);

    /**
     * This method destroyed the Display object that holds the hole graphics information. Also here you'd better put some
     * stuff that must be deallocated and cleaned up
     */
    public void cleanUp();

    /**
     * This method prints the most important information about the current graphics hardware & software
     */
    public void printInformationAbountGraphics();

    /**
     * Prints information about VM, OS and ect.
     */
    public void printInformationAboutSystem();
}
