package fp.infiniteset.burst;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import java.util.Random;

import android.opengl.GLES20;

public class PointCloud
{
    static final int COORDS_PER_VERTEX = 2;

    private final String vertexShaderCode =
        // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
        "attribute vec4 vPosition;" +
        "void main() {" +
        // the matrix must be included as a modifier of gl_Position
        // Note that the uMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        "  gl_Position = uMVPMatrix * vPosition;" +
        "  gl_PointSize = 8.0;" +
        "}";

    private final String fragmentShaderCode =
        "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}";

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

    private final int mProgram;
    private FloatBuffer vertexBuffer;

    private final int vertexCount;
    private final int vertexStride;

    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    /* private FloatBuffer velocityBuffer; */

    public PointCloud(int pointCount)
    {
        vertexCount = pointCount;
        vertexStride = COORDS_PER_VERTEX * 4;

        ByteBuffer bb = ByteBuffer.allocateDirect(vertexCount * vertexStride);
        bb.order(ByteOrder.nativeOrder());

        // Just add random points for now
        vertexBuffer = bb.asFloatBuffer();
        Random rng = new Random();
        for (int i = 0; i < vertexCount; i++)
        {
            vertexBuffer.put(rng.nextFloat() * 2.0f - 1.0f);
            vertexBuffer.put(rng.nextFloat() * 2.0f - 1.0f);
        }
        vertexBuffer.position(0);

        // Prepare shaders and OpenGL program
        int vertexShader = BasicRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = BasicRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }

    public void update()
    {
    }

    public void draw(float[] mvpMatrix) 
    {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        BasicRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        BasicRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

