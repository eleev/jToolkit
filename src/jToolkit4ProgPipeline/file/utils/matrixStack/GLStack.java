package jToolkit4ProgPipeline.file.utils.matrixStack;

import org.lwjgl.opengl.GL20;

/**
 * Created by Astemir Eleev on 31/12/13.
 */
public abstract class GLStack {
    private String modelUniformName;
    private int modelMatrixUniformLocationIndex;
    private int shaderProgramIndex;

    public GLStack(int shaderProgramIndex, String modelUniformName) {
        this.shaderProgramIndex = shaderProgramIndex;
        this.modelUniformName = modelUniformName;
        modelMatrixUniformLocationIndex = initUniformIndices();
    }

    public int getModelMatrixUniformLocationIndex() {
        return modelMatrixUniformLocationIndex;
    }

    private int initUniformIndices() {
        return GL20.glGetUniformLocation(shaderProgramIndex, modelUniformName);
    }
}
