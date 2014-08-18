package jToolkit4ProgPipeline.file.utils.matrixStack;

import jToolkit4ProgPipeline.file.utils.buffer.BufferTools;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * Created by Astemir Eleev on 29/12/13.
 */
public final class GLStack4f extends GLStack{
    private FloatBuffer fbuffer;
    private LinkedList<Matrix4f> stack;
    private int stackHeadIndex;
    private Matrix4f last;
    private Matrix4f preLast;


    public GLStack4f(int shaderProgramIndex, String modelUniformName) {
        super(shaderProgramIndex, modelUniformName);
        stack = new LinkedList<Matrix4f>();
    }

    public final void push(Matrix4f matrix4f) {
        stack.add(matrix4f);
        stackHeadIndex = stack.size();

        if (stackHeadIndex > 1) {
            last = stack.get(stackHeadIndex - 1);
            preLast = stack.get(stackHeadIndex - 2);
            System.out.println("last matrix : \n" + last.toString() + "\npreLast matrix : \n" + preLast);

            Matrix4f.mul(last, preLast, preLast);
            System.out.println("after mult last matrix is : " + last);

            fbuffer = BufferTools.toFlippedFloatBuffer(preLast);
        } else {
            fbuffer = BufferTools.toFlippedFloatBuffer(stack.getLast());
        }
        GL20.glUniformMatrix4(super.getModelMatrixUniformLocationIndex(), false, fbuffer);

    }

    public final void pop() {
        stack.removeLast();
    }

    public void updatedMatrix(Matrix4f updatedMatrix) {
        if (stackHeadIndex > 1) {
            Matrix4f.mul(updatedMatrix, preLast, preLast);
            fbuffer = BufferTools.toFlippedFloatBuffer(preLast);
        }
        else fbuffer = BufferTools.toFlippedFloatBuffer(stack.getLast());
        GL20.glUniformMatrix4(super.getModelMatrixUniformLocationIndex(), false, fbuffer);
    }

}
