package jToolkit4ProgPipeline.file.loader.model;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Astemir Eleev on 17/12/13.
 */
public class Face {
    public Vector3f vertex;
    public Vector3f normal;
    public Vector3f texture;

    public Face(Vector3f vertex, Vector3f normal, Vector3f texture) {
        this.vertex = vertex;
        this.normal = normal;
        this.texture = texture;
    }
}
