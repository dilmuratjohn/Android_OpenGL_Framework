package com.murat.android.opengl.node;


import android.content.Context;
import android.opengl.Matrix;
import android.util.Log;

import com.murat.android.opengl.render.Renderable;
import com.murat.android.opengl.render.Renderer;
import com.murat.android.opengl.Utils;
import com.murat.android.opengl.actions.Action;
import com.murat.android.opengl.actions.Animator;


public class Node implements Renderable, Action {

    protected Context mContext;
    protected Renderer mRenderer;
    private Animator mAnimator;

    protected final float[] mColor = new float[]{1f, 1f, 1f, 1f};
    private final float[] mModelM = new float[16];
    private final float[] mModelViewM = new float[16];
    protected final float[] mModelViewProjectionM = new float[16];
    private final float[] mTranslateM = new float[16];
    private final float[] mRotateM = new float[16];
    private final float[] mScaleM = new float[16];

    public Node(Context context) {
        mContext = context;
    }

    @Override
    public void init(Renderer renderer) {
        mRenderer = renderer;
        Matrix.setIdentityM(mModelM, 0);
        Matrix.setIdentityM(mModelViewM, 0);
        Matrix.setIdentityM(mModelViewProjectionM, 0);
        Matrix.setIdentityM(mTranslateM, 0);
        Matrix.setIdentityM(mRotateM, 0);
        Matrix.setIdentityM(mScaleM, 0);
        rotate(0, 0, 0, 0); //图像翻转
        mMatrixDirty = true;
    }

    @Override
    public void update() {
        if (mAnimator != null) {
            float[] rotation4f = mAnimator.getCurrentRotation4f();
            if (rotation4f != null) {
                rotate(rotation4f[0], rotation4f[1], rotation4f[2], rotation4f[3]);
            }
            float[] scale3f = mAnimator.getCurrentScale3f();
            if (scale3f != null) {
                scale(scale3f[0], scale3f[1], scale3f[2]);
            }
            float[] position3f = mAnimator.getCurrentTranslate3f();
            if (position3f != null) {
                translate(position3f[0], position3f[1], position3f[2]);
            }
            float[] tint3f = mAnimator.getCurrentTint3f();
            if (tint3f != null) {
                tint(tint3f[0], tint3f[1], tint3f[2]);
            }
            float[] alpha1f = mAnimator.getCurrentFade1f();
            if (alpha1f != null) {
                fade(alpha1f[0]);
            }
        }
        if (mMatrixDirty) {
            updateMatrix();
        }

    }

    @Override
    public void bind() {
    }

    @Override
    public void unbind() {
    }

    @Override
    public void render() {
    }

    @Override
    public void delete() {
    }

    @Override
    public void translate(float x, float y, float z) {
        Matrix.setIdentityM(mTranslateM, 0);
        Matrix.translateM(mTranslateM, 0, mTranslateM, 0, x, y, 0);
        mMatrixDirty = true;
    }

    @Override
    public void rotate(float a, float x, float y, float z) {
        Matrix.setIdentityM(mRotateM, 0);
        Matrix.rotateM(mRotateM, 0, mRotateM, 0, 180f, 1f, 0, 0);
        Matrix.rotateM(mRotateM, 0, mRotateM, 0, a, 0, 0, 1f);
        mMatrixDirty = true;
    }

    @Override
    public void scale(float x, float y, float z) {
        Matrix.setIdentityM(mScaleM, 0);
        Matrix.scaleM(mScaleM, 0, mScaleM, 0, x, y, 1f);
        mMatrixDirty = true;
    }

    private boolean mMatrixDirty;

    private void updateMatrix() {
        Matrix.setIdentityM(mModelM, 0);
        Matrix.multiplyMM(mModelM, 0, mScaleM, 0, mModelM, 0);
        Matrix.multiplyMM(mModelM, 0, mRotateM, 0, mModelM, 0);
        Matrix.multiplyMM(mModelM, 0, mTranslateM, 0, mModelM, 0);
        Matrix.multiplyMM(mModelViewM, 0, mRenderer.getViewMatrix(), 0, mModelM, 0);
        Matrix.multiplyMM(mModelViewProjectionM, 0, mRenderer.getProjectionMatrix(), 0, mModelViewM, 0);
        mMatrixDirty = false;
    }

    @Override
    public void tint(float r, float g, float b) {
        mColor[0] = r;
        mColor[1] = g;
        mColor[2] = b;
        mColor[0] = Utils.clamp(mColor[0], 0.0f, 1.0f);
        mColor[1] = Utils.clamp(mColor[1], 0.0f, 1.0f);
        mColor[2] = Utils.clamp(mColor[2], 0.0f, 1.0f);
    }

    @Override
    public void fade(float a) {
        mColor[3] = a;
        mColor[3] = Utils.clamp(mColor[3], 0.0f, 1.0f);
    }

    public void setAnim(int resource) {
        mAnimator = new Animator(mContext, resource);
    }

    public void startAnim() {
        if (mAnimator != null) {
            mAnimator.start();
        } else {
            Log.i("[Error]", "Animator is null.");
        }
    }

    public void reset() {
        Matrix.setIdentityM(mModelM, 0);
    }

}

