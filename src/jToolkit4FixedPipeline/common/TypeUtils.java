package jToolkit4FixedPipeline.common;

/**
 *
 * @author Astemir Eleev
 */
public class TypeUtils {
    // Unsigned comparisons. I don't include every inequality here, just the one you need.
    // Translation: "(i is less than j) XOR (i and j have different signs)"
    public static boolean unsignedLessThan(long i, long j) {
        return (i < j) ^ (i < 0) ^ (j < 0);
    }

    public static boolean unsignedLessThan(int i, int j) {
        return (i < j) ^ (i < 0) ^ (j < 0);
    }

    public static boolean unsignedLessThan(short i, short j) {
        return (i < j) ^ (i < 0) ^ (j < 0);
    }

    public static boolean unsignedLessThan(byte i, byte j) {
        return (i < j) ^ (i < 0) ^ (j < 0);
    }
}
