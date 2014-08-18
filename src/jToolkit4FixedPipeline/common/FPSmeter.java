package jToolkit4FixedPipeline.common;

import org.lwjgl.Sys;

/**
 *
 * @author Astemir Yeleev
 */
public class FPSmeter {
    private long lastFrame;     // time at last frame
    private int fps;            // frames per second
    private long lastFPS;       //last fps time    
    private long lastFrameTime; // used to calculate delta
    
    private int maxFPS;
    private int minFPS;
    private int avgFPS;
    private long countOfFrames;

    
    private long t0, t1; 
    private int frames;
    
    public FPSmeter() {
        this.maxFPS = -100;
        this.minFPS = 100;
    }
    
    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            System.out.println("FPS: "  + fps + '\t' + "MinFPS: " + this.minFPS + '\t' + "MaxFPS: " + maxFPS + 
                                '\t' + "Total frames: " + this.countOfFrames);
            fps = 0;
            lastFPS += 1000;
            
            if (fps > this.maxFPS) {
                this.maxFPS = fps;
            } else if (fps < this.minFPS) {
                this.minFPS = fps;
            }
            
        }
        fps++;
        this.countOfFrames++;
        
    }
    
    /**
     * Get the accurate system time
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    public void fpsMeter () {
        this.frames++;
        this.countOfFrames++;
        this.t1 = System.currentTimeMillis();
        
        if (this.t1 - this.t0 >= 1000) {
            this.fps = this.frames;
            this.t0 = this.t1;
            this.frames = 0;
//            this.minFPS = fps;
            
            if (this.fps > this.maxFPS) {
                this.maxFPS = this.fps;
            }
            else if (this.fps < this.minFPS) {
                this.minFPS = this.fps;
            }
            this.avgFPS = (this.maxFPS + this.minFPS + this.fps) / 3;

            System.out.println("FPS: "  + this.fps + '\t' + "MinFPS: " + this.minFPS + 
                                '\t' + "MaxFPS: " + maxFPS + '\t' + "Avg FPS: " + 
                                this.avgFPS + '\t' + "Total frames: " + this.countOfFrames);
        }

    }
    
    /**
     * Calculate how many milliseconds have passed since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        int delta = (int) (time - lastFrameTime);
        lastFrameTime = time;
        return delta;
    }
    
    public long getLastFPS() {
        return lastFPS;
    }

    public void setLastFPS(long lastFPS) {
        this.lastFPS = lastFPS;
    }

    public int getFps() {
        return fps;
    }
    
}