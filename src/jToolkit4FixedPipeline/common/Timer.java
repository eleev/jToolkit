package jToolkit4FixedPipeline.common;

import org.lwjgl.Sys;

/**
 *
 * @author Astemir Yeleev
 */
public class Timer {
    private long lastFrame;

    public long getTime () {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    public int getDelta () {
        long currentTime = getTime();
        int delta = (int)(currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    public void setLastFrame(long lastFrame) {
        this.lastFrame = lastFrame;
    }

    public long getLastFrame() {
        return lastFrame;
    }
    
}
