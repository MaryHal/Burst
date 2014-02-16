package fp.infiniteset.burst;

import android.content.Context;
import android.opengl.GLSurfaceView;

class BasicView extends GLSurfaceView 
{
    private final BasicRenderer mRenderer;

    public BasicView(Context context) 
    {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new BasicRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        /* setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); */
    }
}

