package jToolkit4FixedPipeline.lighting;

import org.lwjgl.opengl.GL11;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 14.02.13
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public enum SHADE_MODEL {
    SMOOTH (GL11.GL_SMOOTH),
    FLAT (GL11.GL_FLAT);

    private final int shadeModel;

    SHADE_MODEL (final int shadeModel) {
        this.shadeModel = shadeModel;
    }

    int getShadeModel () {
        return shadeModel;
    }
}
