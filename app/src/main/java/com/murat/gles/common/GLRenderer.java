package com.murat.gles.common;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import android.opengl.Matrix;

import com.murat.gles.picture.SpriteRenderer;

import java.util.ArrayList;


public class GLRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];


    GLRenderer(Context context) {
        this.context = context;
        mRenderLine = new ArrayList<>();
    }

    private ArrayList<GLRenderable> mRenderLine;

    public void add(GLRenderable renderer) {
        mRenderLine.add(renderer);
    }

    public void remove(int index) {
        if (index <= 0 && index < mRenderLine.size())
            mRenderLine.remove(index);
    }

    public void clear() {
        mRenderLine.clear();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        for (GLRenderable renderer : mRenderLine)
            renderer.init(this.context);

        start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(projectionMatrix, 0);
        Matrix.translateM(viewMatrix, 0, 0f, 0f, -5f);
        Matrix.perspectiveM(projectionMatrix, 0, 45, (float) width / (float) height, 1f, 100f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        for (GLRenderable renderer : mRenderLine)
            renderer.bind().render(projectionMatrix, viewMatrix).unbind();
    }

    public interface GLRenderable {
        GLRenderable init(Context context);

        GLRenderable bind();

        GLRenderable unbind();

        GLRenderable render(float[] projectionMatrix, float[] viewMatrix);
    }

    public void start() {
        ((SpriteRenderer) mRenderLine.get(0)).getActionInterval()

                .moveToDelayed(1.0f, 0.0f, 0.0f, 2f, 2f)
                .moveToDelayed(0.0f, 1.0f, 0.0f, 2f, 4f)
                .moveToDelayed(-1.0f, 0.0f, 0.0f, 2f, 6f)
                .moveToDelayed(0.0f, -1.0f, 0.0f, 2f, 8f)

                .scaleByDelayed(0.1f, 0.0f, 0.0f, 2f, 0f)
                .scaleByDelayed(0.0f, 0.1f, 0.0f, 2f, 2f)
                .scaleByDelayed(-0.1f, 0.0f, 0.0f, 2f, 4f)
                .scaleByDelayed(0.0f, -0.1f, 0.0f, 2f, 6f)

                .fadeByDelayed(1.0f, 1f, 2f)
                .fadeByDelayed(-1.0f, 1f, 3f)
                .fadeByDelayed(1.0f, 1f, 4f)
                .fadeByDelayed(-1.0f, 1f, 5f)
                .fadeByDelayed(1.0f, 1f, 6f)
                .fadeByDelayed(-1.0f, 1f, 7f)
                .fadeByDelayed(1.0f, 1f, 8f)

                .rotateToDelayed(45, 0, 0, 1f, 2f, 1f)
                .rotateToDelayed(45, 0, 0, 1f, 2f, 2f)
                .rotateToDelayed(45, 0, 0, 1f, 2f, 3f)
                .rotateToDelayed(45, 0, 0, 1f, 2f, 4f)
                .rotateToDelayed(45, 0, 0, 1f, 2f, 5f)
                .rotateToDelayed(45, 0, 0, 1f, 2f, 6f)

        ;


        ((SpriteRenderer) mRenderLine.get(1)).getActionInterval()

                .moveToDelayed(0.0f, -1.0f, 0.0f, 2f, 2f)
                .moveToDelayed(-1.0f, 0.0f, 0.0f, 2f, 4f)
                .moveToDelayed(0.0f, 1.0f, 0.0f, 2f, 6f)
                .moveToDelayed(1.0f, 0.0f, 0.0f, 2f, 8f)

                .scaleByDelayed(0.1f, 0.0f, 0.0f, 2f, 0f)
                .scaleByDelayed(0.0f, 0.1f, 0.0f, 2f, 2f)
                .scaleByDelayed(-0.1f, 0.0f, 0.0f, 2f, 4f)
                .scaleByDelayed(0.0f, -0.1f, 0.0f, 2f, 6f)

                .fadeByDelayed(1.0f, 1f, 2f)
                .fadeByDelayed(-1.0f, 1f, 3f)
                .fadeByDelayed(1.0f, 1f, 4f)
                .fadeByDelayed(-1.0f, 1f, 5f)
                .fadeByDelayed(1.0f, 1f, 6f)
                .fadeByDelayed(-1.0f, 1f, 7f)
                .fadeByDelayed(1.0f, 1f, 8f)

        ;

    }


}
