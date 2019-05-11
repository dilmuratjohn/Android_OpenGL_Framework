package com.murat.android.opengl.sprite;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.murat.android.opengl.render.Renderable;
import com.murat.android.opengl.render.Renderer;
import com.murat.android.opengl.Utils;
import com.murat.android.opengl.actions.Action;
import com.murat.android.opengl.actions.Animation;
import com.murat.android.opengl.common.buffer.VertexArray;
import com.murat.android.opengl.common.buffer.VertexAttributeArray;
import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.common.data.Vertices;
import com.murat.android.opengl.common.texture.Texture;


public class Sprite implements Renderable, Action {

    private Context mContext;
    private Renderer mRenderer;
    private VertexArray mVertexArray;
    private VertexAttributeArray mVertexAttributeArray;
    private Texture mTexture;
    private SpriteShader mRectShader;
    private Animation mAnimation;
    private int mResourceId;

    private final float[] mColor = new float[]{1f, 1f, 1f, 1f};
    private final float[] mModelM = new float[16];
    private final float[] mModelViewM = new float[16];
    private final float[] mModelViewProjectionM = new float[16];
    private final float[] mTranslateM = new float[16];
    private final float[] mRotateM = new float[16];
    private final float[] mScaleM = new float[16];
    private final float[] mVertices;

    public Sprite(Context context, int resourceId) {
        mContext = context;
        mResourceId = resourceId;
        mVertices = Vertices.Position4f_TexCoord2f;
    }

    @Override
    public Renderable init(Renderer renderer) {
        mRenderer = renderer;
        mRectShader = new SpriteShader(mContext);
        mTexture = new Texture(mContext, mResourceId);
        mVertexAttributeArray = new VertexAttributeArray();
        mVertexArray = new VertexArray(mVertices);
        Matrix.setIdentityM(mModelM, 0);
        Matrix.setIdentityM(mModelViewM, 0);
        Matrix.setIdentityM(mModelViewProjectionM, 0);

        Matrix.setIdentityM(mTranslateM, 0);
        Matrix.setIdentityM(mRotateM, 0);
        Matrix.setIdentityM(mScaleM, 0);

        mVertexAttributeArray.push(mRectShader.getPositionLocation(), 4, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexAttributeArray.push(mRectShader.getTexCoordLocation(), 2, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);

        rotate(0, 0, 0, 0); //图像翻转
        mMatrixDirty = true;

        mRectShader.setUniform1i(mRectShader.getTextureLocation(), 0);


        return this;
    }

    @Override
    public Renderable update() {
        if (mAnimation != null) {
            float[] rotation4f = mAnimation.getCurrentRotation4f();
            if (rotation4f != null) {
                rotate(rotation4f[0], rotation4f[1], rotation4f[2], rotation4f[3]);
            }
            float[] scale3f = mAnimation.getCurrentScale3f();
            if (scale3f != null) {
                scale(scale3f[0], scale3f[1], scale3f[2]);
            }
            float[] position3f = mAnimation.getCurrentTranslate3f();
            if (position3f != null) {
                translate(position3f[0], position3f[1], position3f[2]);
            }
            float[] tint3f = mAnimation.getCurrentTint3f();
            if (tint3f != null) {
                tint(tint3f[0], tint3f[1], tint3f[2]);
            }
            float[] alpha1f = mAnimation.getCurrentFade1f();
            if (alpha1f != null) {
                fade(alpha1f[0]);
            }
        }
        if (mMatrixDirty) updateMatrix();
        return this;
    }

    @Override
    public Renderable bind() {
        mRectShader.bind();
        mTexture.bind();
        mVertexArray.setVertexAttributePointer(mVertexAttributeArray);
        return this;
    }

    @Override
    public Renderable unbind() {
        mRectShader.unbind();
        mTexture.unbind();
        return this;
    }

    @Override
    public Renderable render() {
        mRectShader.setUniform4f(mRectShader.getColorLocation(), mColor);
        mRectShader.setUniformMatrix4fv(mRectShader.getMVPMatrixLocation(), mModelViewProjectionM);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        GLES20.glDisable(GLES20.GL_BLEND);
        return this;
    }

    @Override
    public Renderable delete() {
        mRectShader.delete();
        mTexture.delete();
        return this;
    }

    @Override
    public Action translate(float x, float y, float z) {
        Matrix.setIdentityM(mTranslateM, 0);
        Matrix.translateM(mTranslateM, 0, mTranslateM, 0, x, y, 0);
        mMatrixDirty = true;
        return this;
    }

    @Override
    public Action rotate(float a, float x, float y, float z) {
        Matrix.setIdentityM(mRotateM, 0);
        Matrix.rotateM(mRotateM, 0, mRotateM, 0, 180, 1f, 0, 0);
        Matrix.rotateM(mRotateM, 0, mRotateM, 0, a, 0, 0, 1);
        mMatrixDirty = true;
        return this;
    }

    @Override
    public Action scale(float x, float y, float z) {
        Matrix.setIdentityM(mScaleM, 0);
        Matrix.scaleM(mScaleM, 0, mScaleM, 0, x, y, 1f);
        mMatrixDirty = true;
        return this;
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
    public Action tint(float r, float g, float b) {
        mColor[0] = r;
        mColor[1] = g;
        mColor[2] = b;
        mColor[0] = Utils.clamp(mColor[0], 0.0f, 1.0f);
        mColor[1] = Utils.clamp(mColor[1], 0.0f, 1.0f);
        mColor[2] = Utils.clamp(mColor[2], 0.0f, 1.0f);
        return this;
    }

    @Override
    public Action fade(float a) {
        mColor[3] = a;
        mColor[3] = Utils.clamp(mColor[3], 0.0f, 1.0f);
        return this;
    }

    public void setAnim(int resource) {
        mAnimation = new Animation(mContext, resource);
    }

    public void startAnim() {
        if (mAnimation != null) {
            mAnimation.start();
        }
    }

}
