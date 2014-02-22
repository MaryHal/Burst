package fp.infiniteset.particle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import fp.infiniteset.burst.BasicRenderer;
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

    float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    private final int mProgram;
    public FloatBuffer vertexBuffer;
    public FloatBuffer velocityBuffer;
    public IntBuffer lifeBuffer;
    public boolean alive;

    public final int vertexCount;
    private final int vertexStride;

    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    public PointCloud(int pointCount)
    {
        vertexCount = pointCount;
        vertexStride = COORDS_PER_VERTEX * 4;

        // Prepare Buffers
        ByteBuffer bb1 = ByteBuffer.allocateDirect(vertexCount * vertexStride);
        bb1.order(ByteOrder.nativeOrder());
        vertexBuffer = bb1.asFloatBuffer();

        ByteBuffer bb2 = ByteBuffer.allocateDirect(vertexCount * vertexStride);
        bb2.order(ByteOrder.nativeOrder());
        velocityBuffer = bb2.asFloatBuffer();

        ByteBuffer bb3 = ByteBuffer.allocateDirect(vertexCount * vertexStride);
        bb3.order(ByteOrder.nativeOrder());
        lifeBuffer = bb3.asIntBuffer();

        alive = false;

        // Prepare shaders and OpenGL program
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }

    public void resetBufferPosition()
    {
        vertexBuffer.position(0);
        velocityBuffer.position(0);
        lifeBuffer.position(0);
    }

    public int loadShader(int type, String shaderCode)
    {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void update()
    {
        if (!alive)
            return;

        vertexBuffer.position(0);
        lifeBuffer.position(0);
        for (int i = 0; i < vertexCount; i++)
        {
            vertexBuffer.put(vertexBuffer.get(2*i) + velocityBuffer.get(2*i));
            vertexBuffer.put(vertexBuffer.get(2*i + 1) + velocityBuffer.get(2*i + 1));
            lifeBuffer.put(lifeBuffer.get(i) - 1);
        }

        vertexBuffer.position(0);
        lifeBuffer.position(0);
    }

    public void draw(float[] mvpMatrix)
    {
        if (!alive)
            return;

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

