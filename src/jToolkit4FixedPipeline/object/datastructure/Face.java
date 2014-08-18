package jToolkit4FixedPipeline.object.datastructure;

import jToolkit4FixedPipeline.vector.Vector3f;


/**
 *
 * @author Astemir Yeleev
 */
public class Face {
    public Vector3f vertex = new Vector3f();
    public Vector3f normals = new Vector3f();
    public Vector3f textures = new Vector3f();

    public Face(Vector3f vertex, Vector3f normals, Vector3f textures) {
        this.vertex = vertex;
        this.normals = normals;
        this.textures = textures;
    }
}
