package jToolkit4FixedPipeline.common;

/**
 * Some of math tools
 * @author Astemir Eleev
 */
public class MathTools {
    public static final float RAD_TO_DEG = (float) (180.0f / Math.PI);
    public static final float DEG_TO_RAD = (float) (Math.PI / 180.0f);

    public static int fastAbs(int i) {
        return (i >= 0) ? i : -i;
    }

    public static float fastAbs(float d) {
        return (d >= 0) ? d : -d;
    }

    public static double fastAbs(double d) {
        return (d >= 0) ? d : -d;
    }

    /**
     * Clamps a given value to be an element of [0..1].
     */
    public static double clamp(double value) {
        if (value > 1.0)
            return 1.0;
        if (value < 0.0)
            return 0.0;
        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (value > max)
            return max;
        if (value < min)
            return min;
        return value;
    }

    public static float clamp(float value, float min, float max) {
        if (value > max)
            return max;
        if (value < min)
            return min;
        return value;
    }

    public static int clamp(int value, int min, int max) {
        if (value > max)
            return max;
        if (value < min)
            return min;
        return value;
    }

    /**
     * Linear interpolation.
     */
    public static double lerp(double x, double x1, double x2, double q00, double q01) {
        return ((x2 - x) / (x2 - x1)) * q00 + ((x - x1) / (x2 - x1)) * q01;
    }

    public static double lerp(double x1, double x2, double p) {
        return x1 * (1.0 - p) + x2 * p;
    }

    public static float lerpf(float x1, float x2, float p) {
        return x1 * (1.0f - p) + x2 * p;
    }

    /**
     * Trilinear interpolation.
     */
    public static double triLerp(double x, double y, double z, double q000, double q001, double q010, double q011, double q100, double q101, double q110, double q111, double x1, double x2, double y1, double y2, double z1, double z2) {
        double x00 = lerp(x, x1, x2, q000, q100);
        double x10 = lerp(x, x1, x2, q010, q110);
        double x01 = lerp(x, x1, x2, q001, q101);
        double x11 = lerp(x, x1, x2, q011, q111);
        double r0 = lerp(y, y1, y2, x00, x01);
        double r1 = lerp(y, y1, y2, x10, x11);
        return lerp(z, z1, z2, r0, r1);
    }

    public static float radianToDegree (float radian) {
        return (float)(radian * Math.PI / 180);
    }
    
    public static float degreeToRadian (float degree) {
        return (float)(degree * 180 / Math.PI);
    }
    
    public float lineLength (float x1, float y1, float z1, float x2, float y2, float z2) {
        return (float)(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
    }
    
}
