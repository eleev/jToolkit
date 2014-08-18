package jToolkit4ProgPipeline.file.utils.matrixStack;

import jToolkit4ProgPipeline.file.utils.buffer.BufferTools;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix3f;

import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * Created by Astemir Eleev on 31/12/13.
 */
public final class GLStack3f extends GLStack {
    private FloatBuffer fbuffer;
    private LinkedList<Matrix3f> stack;
    private int stackHeadIndex;

    public GLStack3f(int shaderProgramIndex, String modelUniformName) {
        super(shaderProgramIndex, modelUniformName);
        stack = new LinkedList<Matrix3f>();
    }

    public final void push(Matrix3f matrix3f) {
        stack.add(matrix3f);
        stackHeadIndex = stack.size();


        if (stackHeadIndex > 1) {
            Matrix3f last = stack.get(stackHeadIndex - 1);
            Matrix3f preLast = stack.get(stackHeadIndex - 2);
            Matrix3f.mul(last, preLast, preLast);
            fbuffer = BufferTools.toFlippedFloatBuffer(preLast);
        } else {
            fbuffer = BufferTools.toFlippedFloatBuffer(stack.getLast());
        }
        GL20.glUniformMatrix4(super.getModelMatrixUniformLocationIndex(), false, fbuffer);
    }

    public final void pop() {
        stack.removeLast();
    }
}


