package jToolkit4FixedPipeline.object.datastructure;

import jToolkit4FixedPipeline.vector.Vector2f;
import jToolkit4FixedPipeline.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Astemir Eleev
 */
public class Model {
    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    public List<Vector3f> normals = new ArrayList<Vector3f>();
    public List<Vector2f> textures = new ArrayList<Vector2f>();
    public List<Face> faces = new ArrayList<Face>();
}
