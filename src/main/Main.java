package main;


/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 20/16/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main (String[] args) {
        // create pipeline instance
//        new FixedPipeline(720, 1000, "Shader Tron").run();
//        new ProgrammablePipeline(1150, 740, "OpenGL 4.1 / GLSL410 core / Shaders test").run();
        new PPipeline(1600, 900, "OpenGL 4.1 Test").run();

    }

}
