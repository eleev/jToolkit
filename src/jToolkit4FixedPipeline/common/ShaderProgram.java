package jToolkit4FixedPipeline.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Class for loading and manipulating shader through openGL fixed pipeline
 * @author Astemir Eleev
 */
public final class ShaderProgram {
    private int program;
    private int vertexShader;
    private int fragmentShader;
    private StringBuilder vBuilder;
    private StringBuilder fBuilder;

    /**
     * This constructor creates the minimum required GL pipeline for rendering
     * @param pathToVertexShader    - vertex shader source file
     * @param pathToFragmentShader  - fragment shader source file
     */
    public ShaderProgram(String pathToVertexShader, String pathToFragmentShader) {
        this.program = glCreateProgram();
        this.vertexShader = glCreateShader(GL_VERTEX_SHADER);
        this.fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        
        initialize (pathToVertexShader, pathToFragmentShader);
        compileVertexShader();
        compileFragmentShader();
        attachShaderAndValidate();
    }

    // after implementing these constructors the code below must be modified as well

    @Deprecated
    public ShaderProgram(String pathToVertexShader, String pathToGeometryShader, String pathToFragmentShader) {
        // implement this constructor
        throw new RuntimeException(ShaderProgram.class.getName() + " : this constructor is in development");
    }

    @Deprecated
    public ShaderProgram(String pathToVertexShder, String pathToTesselationControlShader, String pathToTesselationEvaluatioShader, String pathFragmentShder) {
        // implement this constructor
        throw new RuntimeException(ShaderProgram.class.getName() + " : this constructor is in development");
    }

    @Deprecated
    public ShaderProgram(String pathToVertexShder, String pathToTesselationControlShader, String pathToTesselationEvaluatioShader, String pathToGeometryShader, String pathFragmentShder) {
        // implement this constructor
        throw new RuntimeException(ShaderProgram.class.getName() + " : this constructor is in development");
    }


    /**
     * Use this method and useShaderEnd method as a body for some other program code
     * It's like body for if construction. put some code between useShadefBegin and
     * useShaderEnd methods
     */
    public void useShaderBegin () {
        glUseProgram(this.program);
    }
    
    /*
     * Use this method as end of body of shader.
     * Requires to use it with useShaderBedin method
     * NOTE: Actually we have to sent 0 to function glUseProgram for disabling using shader. Originally there was this.program
     */
    public void useShaderEnd () {
//        glUseProgram(this.program);
        glUseProgram(0);
    }
    
    /*
     * Delete shader program from RAM
     */
    public void deleteShader () {
        glDeleteShader(this.vertexShader);
        glDeleteShader(this.fragmentShader);
        glDeleteProgram(this.program);
    }
    
    /**
     * Set some float uniform variable to shader program
     * The length of array should be between 1 to 4
     * 
     * If your shader doesn't use any uniform variables you must not use
     * this method
     * @param variableFromShader    - some value from shader program
     * @param array                 - some float data
     */
    public void setUniformf (String variableFromShader, float[] array) {
        int id = glGetUniformLocation(program, variableFromShader);
        
        if (array.length == 1) {
            glUniform1f(id, array[0]);
        } else if (array.length == 2) {
            glUniform2f(id, array[0], array[1]);
        } else if (array.length == 3) {
            glUniform3f(id, array[0], array[1], array[2]);
        } else if (array.length == 4) {
            glUniform4f(id, array[0], array[1], array[2], array[3]);
        } else {
            Logger.getLogger(ShaderProgram.class.getName()).log(Level.WARNING, "setUniformf method \n array lenght is wrong");
        }
    }
    
    /**
     * Set some integer uniform variable to shader program
     * The length of array should be between 1 and 4
     * 
     * If your shader doesn't use any uniform variables you must not use
     * this method
     * @param variableFromShader    - some value from shader program
     * @param array                 - some integer data
     */
    public void setUniformi (String variableFromShader, int[] array) {
        int id = glGetUniformLocation(program, variableFromShader);
        
        if (array.length == 1) {
            glUniform1i(id, array[0]);
        } else if (array.length == 2) {
            glUniform2i(id, array[0], array[1]);
        } else if (array.length == 3) {
            glUniform3i(id, array[0], array[1], array[2]);
        } else if (array.length == 4) {
            glUniform4i(id, array[0], array[1], array[2], array[3]);
        } else {
            Logger.getLogger(ShaderProgram.class.getName()).log(Level.WARNING, "setUniformi \n array lenght is wrong");
        }
    }
    
    /**
     * Set some matrix uniform variable to shader program
     * The capacity of float buffer should be 4, 9 or 16 (matrix 2x2, 3x3, 4x4)
     * 
     * If your shader doesn't use any uniform variables you must not use
     * this method
     * @param variableFromShader    - some value from shader program
     * @param matrix                - some float buffer data
     */
    public void setUniformMatrixf (String variableFromShader, boolean transpose, FloatBuffer matrix) {
        int id = glGetUniformLocation(program, variableFromShader);
        
        if (matrix.capacity() == 4) {
            glUniformMatrix2(id, transpose, matrix);
        } else if (matrix.capacity() == 9) {
           glUniformMatrix3(id, transpose, matrix);
        } else if (matrix.capacity() == 16) {
            glUniformMatrix4(id, transpose, matrix);
        } else {
             Logger.getLogger(ShaderProgram.class.getName()).log(Level.WARNING, "FloatBuffer capacity is wrong");
        }
    }
    
    /**
     * Get id of shader program
     * @return id of program
     */
    public int getProgram() {
        return program;
    }
    
    /**
     * Get id of fragment shader
     * @return id of fragment shader 
     */
    public int getFragmentShader() {
        return fragmentShader;
    }

    /**
     * Get id of vertex shader
     * @return id of vertex shader 
     */
    public int getVertexShader() {
        return vertexShader;
    }
    
    /***
     * Initialize shader program
     * @param pathToVertexShader    - local path to vertex shader
     * @param pathToFragmentShader  - local path to fragment shader
     */
    private void initialize (String pathToVertexShader, String pathToFragmentShader) {
        this.fBuilder = loadFromFile(pathToFragmentShader);
        this.vBuilder = loadFromFile(pathToVertexShader);
    }
    
    private StringBuilder loadFromFile (String path) {
        BufferedReader breader = null;
        StringBuilder sb = new StringBuilder();
        String tmp = "";
        
        try {
            breader = new BufferedReader(new FileReader(new File(path)));
            
            while ((tmp = breader.readLine())!= null) {
                sb.append(tmp).append("\n");
            }
            breader.close();
        } catch (IOException e) {
            Logger.getLogger(ShaderProgram.class.getName()).log(Level.WARNING, "loading shader exception", e);
            e.printStackTrace();
        }
        
        return sb;
    }
    
    private void compileVertexShader () {
        glShaderSource(vertexShader, vBuilder);
        glCompileShader(vertexShader);
    }

    private void compileFragmentShader () {
        glShaderSource(fragmentShader, fBuilder);
        glCompileShader(fragmentShader);
    }

    public void printVertexShaderCompilationStatus() {
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            int infoLength = glGetShaderi(vertexShader, GL_INFO_LOG_LENGTH);
            String log = glGetShaderInfoLog(vertexShader, infoLength);

            System.err.println("Vertex shader: GL_COMPILE_STATUS: ERROR\n"  + log);
        } else System.err.println("Vertex shader: GL_COMPILE_STATUS: Success");

        if (glGetShaderi(vertexShader, GL_LINK_STATUS) == GL_FALSE) {
            int infoLength = glGetProgrami(vertexShader, GL_INFO_LOG_LENGTH);
            String linkingLog = glGetProgramInfoLog(vertexShader, infoLength);

            System.err.println("Vertex shader: GL_LINK_STATUS: ERROR\n" + linkingLog);
        } else System.err.println("Vertex shader: GL_LINK_STATUS: Success");

        if (glGetShaderi(vertexShader, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("Vertex shader: GL_VALIDATE_STATUS: ERROR");
        } else System.err.println("Vertex shader: GL_VALIDATE_STATUS: Success");
    }

    public void printFragmentShaderCompilationStatus() {
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            int infoLength = glGetShaderi(fragmentShader, GL_INFO_LOG_LENGTH);
            String log = glGetShaderInfoLog(fragmentShader, infoLength);

            System.err.println("Fragment shader: GL_COMPILE_STATUS: ERROR\n" + log);
        } else System.err.println("Fragment shader: GL_COMPILE_STATUS: Success");

        if (glGetShaderi(fragmentShader, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Fragment shader: GL_LINK_STATUS: ERROR");
        } else System.err.println("Fragment shader: GL_LINK_STATUS: Success");

        if (glGetShaderi(fragmentShader, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("Fragment shader: GL_VALIDATE_STATUS: ERROR");
        } else System.err.println("Fragment shader: GL_VALIDATE_STATUS: Success");
    }

    private void getGLError() {
        int err = glGetError();

        while(err != GL_NO_ERROR) {
            Logger.getLogger(ShaderProgram.class.getCanonicalName()).log(Level.INFO, glGetString(err));
            err = glGetError();
        }
    }


    /**
     * connect shaders between each other, as a result you will have a shader program instead of 
     * two types of different shaders
     */
    private void attachShaderAndValidate () {
        glAttachShader(this.program, this.vertexShader);
        glAttachShader(this.program, this.fragmentShader);
        glLinkProgram(this.program);
        glValidateProgram(this.program);
    }
    
}
