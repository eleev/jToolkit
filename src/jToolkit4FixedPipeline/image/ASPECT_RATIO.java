package jToolkit4FixedPipeline.image;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 09.02.13
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public enum ASPECT_RATIO {
    QUAD (JpgDrawer.w, JpgDrawer.w),
    WIDE (JpgDrawer.w, JpgDrawer.h);

    private final int w;
    private final int h;

    ASPECT_RATIO(final int w, final int h) {
        this.w = w;
        this.h = h;
    }
}
