package com.murat.gles.common;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import android.opengl.Matrix;

import java.util.ArrayList;


public class GLRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] modelViewMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    GLRenderer(Context context) {
        this.context = context;
        mRenderLine = new ArrayList<>();
    }

    private ArrayList<GLRenderable> mRenderLine;

    void add(GLRenderable renderer) {
        mRenderLine.add(renderer);
    }

    void remove(int index) {
        mRenderLine.remove(index);
    }

    void removeAll() {
        mRenderLine.clear();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        for (GLRenderable renderer : mRenderLine)
            renderer.init(this.context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.perspectiveM(projectionMatrix, 0, 45, (float) width / (float) height, 1f, 100f);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.translateM(viewMatrix, 0, 0f, 0f, -5f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);
        for (GLRenderable renderer : mRenderLine)
            renderer.bind().render(modelViewProjectionMatrix).unbind();
    }
}
