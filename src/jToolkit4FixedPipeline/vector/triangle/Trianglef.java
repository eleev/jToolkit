package jToolkit4FixedPipeline.vector.triangle;

import jToolkit4FixedPipeline.vector.Vector3f;

/**
 *
 * @author Astemir Eleev
 */
public class Trianglef {
    private Vector3f vector[];

    public Trianglef() {
        vector = new Vector3f[3];
        
        for (int i = 0; i < 3; i++) {
            vector[i] = new Vector3f();
        }
    }
    
    public Trianglef(Vector3f v1, Vector3f v2, Vector3f v3) {
        vector = new Vector3f[3];
        vector[0] = v1;
        vector[1] = v2;
        vector[2] = v3;
    }
    
    public Trianglef geTriangle () {
        return this;
    }
    
    public void setTriangle (Vector3f v1, Vector3f v2, Vector3f v3) {
        vector = new Vector3f[3];
        vector[0] = v1;
        vector[1] = v2;
        vector[2] = v3;
    }
    
    @Override
    public String toString () {
        return vector[0].getX() + " " + vector[0].getY() + " " + vector[0].getZ() + " " + "\n" +
                    vector[1].getX() + " " + vector[1].getY() + " " + vector[1].getZ() + " " + "\n" +
                        vector[2].getX() + " " + vector[2].getY() + " " + vector[2].getZ() + " ";
    }
    
}
