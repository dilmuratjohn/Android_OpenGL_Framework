package com.murat.android.opengl;

import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GLRenderer implements GLSurfaceView.Renderer {

    private int mWidth;
    private int mHeight;

    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    GLRenderer() {
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
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        for (GLRenderable renderer : mRenderLine)
            renderer.init(this);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mWidth = width;
        mHeight = height;
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setIdentityM(mProjectionMatrix, 0);
        // Matrix.translateM(mViewMatrix, 0, 0f, 0f, -5f);
        // Matrix.perspectiveM(mProjectionMatrix, 0, 45, (float) width / height, 1f, 100f);

        float aspectRatio;
        if (width > height) {
            aspectRatio = (float) width / (float) height;
        } else {
            aspectRatio = (float) height / (float) width;
        }
        if (width > height) {
            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        for (GLRenderable renderer : mRenderLine)
            renderer.bind().render().unbind();
    }

    public interface GLRenderable {
        GLRenderable init(GLRenderer renderer);

        GLRenderable bind();

        GLRenderable unbind();

        GLRenderable render();
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
