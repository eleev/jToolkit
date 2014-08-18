package jToolkit4FixedPipeline.vector.triangle.sector;


import jToolkit4FixedPipeline.vector.triangle.Trianglef;

/**
 * Each 3D world is basically a collection of sectors. 
 * A sector can be a room, a cube, or any enclosed volume.
 * @author Astemir Eleev
 */
public class Sectorf {
    private  int numTriangles;
    private Trianglef triangle[]; //holds class Triangle objects

    public Sectorf(int num) {
        numTriangles = num;
        triangle = new Trianglef[numTriangles];
        
        for (int i = 0; i < numTriangles; i++) {
            triangle[i] = new Trianglef();
        }
    }
    
    public Sectorf getSector () {
        return this;
    }
    
    @Override
    public String toString () {
        return triangle[0].toString() + "\n" + 
                    triangle[1].toString() + "\n" +
                        triangle[2].toString();
    }
}
