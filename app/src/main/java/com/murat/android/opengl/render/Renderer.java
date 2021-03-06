package com.murat.android.opengl.render;

import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class Renderer implements GLSurfaceView.Renderer {

    private Listener mListener;

    public interface Listener {
        void onSurfaceCreated();

        void onSurfaceChanged();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void removeListener() {
        mListener = null;
    }

    private int mWidth;
    private int mHeight;

    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private ArrayList<Renderable> mRenderQueue = new ArrayList<>();

    public void add(Renderable renderer) {
        if (renderer != null) {
            mRenderQueue.add(renderer);
        } else {
            Log.w("[OpenGL-Waring]", "should not add null object to render queue.");
        }
    }

    public void remove(int index) {
        if (index >= 0 && index < mRenderQueue.size()) {
            Renderable renderable = mRenderQueue.get(index);
            if (renderable != null) {
                renderable.delete();
            }
            mRenderQueue.remove(index);
        }
    }

    public void clear() {
        for (Renderable renderable : mRenderQueue) {
            if (renderable != null) {
                renderable.delete();
            }
        }
        mRenderQueue.clear();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        for (Renderable renderer : mRenderQueue) {
            if (renderer != null) {
                renderer.init(this);
            }
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mWidth = width;
        mHeight = height;
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setIdentityM(mProjectionMatrix, 0);

        float aspectRatio;
        if (width > height) {
            aspectRatio = (float) width / (float) height;
            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            aspectRatio = (float) height / (float) width;
            Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        for (Renderable renderer : mRenderQueue) {
            if (renderer != null) {
                renderer.bind();
                renderer.update();
                renderer.render();
                renderer.unbind();
            }
        }
    }

    public Point getSurfaceSize() {
        return new Point(mWidth, mHeight);
    }

    public float[] getProjectionMatrix() {
        return mProjectionMatrix;
    }

    public float[] getViewMatrix() {
        return mViewMatrix;
    }

}
