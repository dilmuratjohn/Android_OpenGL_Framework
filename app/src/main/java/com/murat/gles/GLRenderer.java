package com.murat.gles;

import android.graphics.Point;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import com.murat.gles.actions.Action;
import com.murat.gles.actions.ActionInterval;
import com.murat.gles.particle.ParticleRenderer;
import com.murat.gles.sprite.SpriteRenderer;

import java.util.ArrayList;


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
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        for (GLRenderable renderer : mRenderLine)
            renderer.init(this);

//        start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mWidth = width;
        mHeight = height;
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setIdentityM(mProjectionMatrix, 0);
//        Matrix.translateM(mViewMatrix, 0, 0f, 0f, -5f);
//        Matrix.perspectiveM(mProjectionMatrix, 0, 45, (float) width / height, 1f, 100f);


        float aspectRatio;
        if (width > height){
            aspectRatio = (float) width/ (float) height;
        }
        else{
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

    public void start() {
        ((SpriteRenderer) mRenderLine.get(0)).getActionInterval()
                .rotate(180f, 0, 0, 1f, 0f)
                .move(1.0f, 0.0f, 0.0f, 2f, 2f)
                .move(0.0f, 1.0f, 0.0f, 2f, 4f)
                .move(-1.0f, 0.0f, 0.0f, 2f, 6f)
                .move(0.0f, -1.0f, 0.0f, 2f, 8f)

                .scale(0.1f, 0.0f, 0.0f, 2f, 0f)
                .scale(0.0f, 0.1f, 0.0f, 2f, 2f)
                .scale(-0.1f, 0.0f, 0.0f, 2f, 4f)
                .scale(0.0f, -0.1f, 0.0f, 2f, 6f)

                .fade(1.0f, 1f, 2f)
                .fade(-1.0f, 1f, 3f)
                .fade(1.0f, 1f, 4f)
                .fade(-1.0f, 1f, 5f)
                .fade(1.0f, 1f, 6f)
                .fade(-1.0f, 1f, 7f)
                .fade(1.0f, 1f, 8f)

                .rotate(45, 0, 0, 1f, 2f, 1f)
                .rotate(45, 0, 0, 1f, 2f, 2f)
                .rotate(45, 0, 0, 1f, 2f, 3f)
                .rotate(45, 0, 0, 1f, 2f, 4f)
                .rotate(45, 0, 0, 1f, 2f, 5f)
                .rotate(45, 0, 0, 1f, 2f, 6f)
        ;


        ((SpriteRenderer) mRenderLine.get(1)).getActionInterval()
                .rotate(180f, 0, 0, 1f, 0f)
                .move(0.0f, -1.0f, 0.0f, 2f, 2f)
                .move(-1.0f, 0.0f, 0.0f, 2f, 4f)
                .move(0.0f, 1.0f, 0.0f, 2f, 6f)
                .move(1.0f, 0.0f, 0.0f, 2f, 8f)

//                .scale(0.1f, 0.0f, 0.0f, 2f, 0f)
//                .scale(0.0f, 0.1f, 0.0f, 2f, 2f)
//                .scale(-0.1f, 0.0f, 0.0f, 2f, 4f)
//                .scale(0.0f, -0.1f, 0.0f, 2f, 6f)

                .fade(1.0f, 1f, 2f)
                .fade(-1.0f, 1f, 3f)
                .fade(1.0f, 1f, 4f)
                .fade(-1.0f, 1f, 5f)
                .fade(1.0f, 1f, 6f)
                .fade(-1.0f, 1f, 7f)
                .fade(1.0f, 1f, 8f)
        ;


        ((SpriteRenderer) mRenderLine.get(2)).getActionInterval()
                .rotate(180f, 0, 0, 1f, 0f)
        ;

        ((SpriteRenderer) mRenderLine.get(3)).getActionInterval()
                .move(0.0f, -1.0f, 0.0f, 2f, 2f)
                .move(-1.0f, 0.0f, 0.0f, 2f, 4f)
                .move(0.0f, 1.0f, 0.0f, 2f, 6f)
                .move(1.0f, 0.0f, 0.0f, 2f, 8f)
        ;
    }


}
