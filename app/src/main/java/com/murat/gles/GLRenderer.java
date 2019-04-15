package com.murat.gles;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.murat.gles.util.MathUtils;
import com.murat.gles.util.Renderable;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import android.opengl.Matrix;

import java.util.HashMap;
import java.util.Map;


public class GLRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] modelViewMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    GLRenderer(Context context) {
        this.context = context;
        mRenderMap = new HashMap<>();
    }

    private Map<String, Renderable> mRenderMap;

    public void add(Renderable renderer, String name) {
        mRenderMap.put(name, renderer);
    }

    public void remove(String name) {
        mRenderMap.remove(name);
    }

    public void removeAll(){
        mRenderMap.clear();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        for (Map.Entry<String, Renderable> renderer : mRenderMap.entrySet())
            renderer.getValue().bind(this.context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        MathUtils.Mat4.perspectiveProjection(projectionMatrix, 45, (float) width / (float) height, 1f, 100f);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.translateM(viewMatrix, 0, 0f, 0f, -5f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);
        for (Map.Entry<String, Renderable> renderer : mRenderMap.entrySet())
            renderer.getValue().render(modelViewProjectionMatrix);
    }
}
