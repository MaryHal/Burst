package fp.infiniteset.burst;
import fp.infiniteset.particle.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.util.Random;

public class BasicRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "BasicRenderer";

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    /* private final float[] mRotationMatrix = new float[16]; */

    private Emitter emitter;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) 
    {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Set 2D drawing mode
        /* GLES20.glViewport(0, 0, windowWidth, windowHeight); */
        /* GLES20.glDisable(GLES20.GL_DEPTH_TEST); */

        final class Firework implements ParticleInitializer
        {
            public void setParticle(PointCloud p)
            {
                p.resetBufferPosition();
                Random rng = new Random();

                for (int i = 0; i < p.vertexCount; i++)
                {
                    float magnitude = rng.nextFloat() * 4 - 2;
                    float w = rng.nextFloat() * 2 - 1;
                    float u = rng.nextFloat() * 6.28f;

                    // X, Y
                    p.vertexBuffer.put(100.0f);
                    p.vertexBuffer.put(100.0f);

                    // X-velocity, Y-velocity
                    p.velocityBuffer.put(magnitude * (float)Math.sin(u) * (float)Math.sqrt(1 - w * w));
                    p.velocityBuffer.put(magnitude * (float)Math.cos(u) * (float)Math.sqrt(1 - w * w));

                    // Life
                    p.lifeBuffer.put(250);
                }
            }
        }
        ParticleInitializer p = new Firework();

        emitter.add(p);
    }

    @Override
    public void onDrawFrame(GL10 unused) 
    {
        /* float[] scratch = new float[16]; */

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
        /* mSquare.draw(mMVPMatrix); */
        /* emitter.draw(mMVPMatrix); */

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);

        /* Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f); */

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        /* Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0); */

        // Draw triangle
        /* mTriangle.draw(scratch); */
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        /* float ratio = (float)width / height; */

        // Set ortho projection
        /* int projectionHandle = GLES20.glGetUniformLocation(shaderProg, "Projection"); */
        /* Matrix.orthoM(mProjectionMatrix, 0, 0, width, 0, height, -10, 10); */
        /* GLES20.glUniformMatrix4fv(projectionHandle, 1, false, mProjectionMatrix, 0); */
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}

