package jToolkit4FixedPipeline.matrix;

/**
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 12.02.13
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
public enum DIRECTION_OF_ROTATION {
    CLOCK_WISE(true),
    COUNTER_CLOCK_WISE(false);
    private final boolean state;

    DIRECTION_OF_ROTATION (final boolean state) {
        this.state = state;
    }

    boolean getState () {
        return state;
    }
}
