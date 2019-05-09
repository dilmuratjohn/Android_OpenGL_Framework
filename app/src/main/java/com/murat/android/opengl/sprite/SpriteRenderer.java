package com.murat.android.opengl.sprite;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.murat.android.opengl.GLRenderer;
import com.murat.android.opengl.Utils;
import com.murat.android.opengl.actions.Action;
import com.murat.android.opengl.actions.ActionInterval;
import com.murat.android.opengl.common.buffer.VertexArray;
import com.murat.android.opengl.common.buffer.VertexBufferLayout;
import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.common.data.Vertices;
import com.murat.android.opengl.common.texture.Texture;


public class SpriteRenderer implements GLRenderer.GLRenderable, Action {

    private Context mContext;
    private GLRenderer mRenderer;
    private VertexArray mVertexArray;
    private VertexBufferLayout mVertexBufferLayout;
    private Texture mTexture;
    private SpriteShader mRectShader;
    private ActionInterval mActionInterval;
    private int mResourceId;

    private final float[] mColor = new float[]{1f, 1f, 1f, 1f};
    private final float[] mModelM = new float[16];
    private final float[] mModelViewM = new float[16];
    private final float[] mModelViewProjectionM = new float[16];
    private final float[] mTranslateM = new float[16];
    private final float[] mRotateM = new float[16];
    private final float[] mScaleM = new float[16];
    private final float[] mVertices;

    public SpriteRenderer(Context context, int resourceId) {
        mContext = context;
        mActionInterval = new ActionInterval(this);
        mResourceId = resourceId;
        mVertices = Vertices.Position4f_TexCoord2f;
    }

    @Override
    public GLRenderer.GLRenderable init(GLRenderer renderer) {
        mRenderer = renderer;
        mRectShader = new SpriteShader(mContext);
        mTexture = new Texture(mContext, mResourceId);
        mVertexBufferLayout = new VertexBufferLayout();
        mVertexArray = new VertexArray(mVertices);

        mRectShader.setUniform1i(mRectShader.getTextureLocation(), 0);
        mVertexBufferLayout.push(mRectShader.getPositionLocation(), 4, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mRectShader.getTexCoordLocation(), 2, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);

        Matrix.setIdentityM(mModelM, 0);
        Matrix.setIdentityM(mModelViewM, 0);
        Matrix.setIdentityM(mModelViewProjectionM, 0);

        Matrix.setIdentityM(mTranslateM, 0);
        Matrix.setIdentityM(mRotateM, 0);
        Matrix.setIdentityM(mScaleM, 0);

        rotate(180f, 1f, 0, 0);
        return this;
    }

    @Override
    public GLRenderer.GLRenderable bind() {
        mRectShader.bind();
        mTexture.bind();
        mVertexArray.setVertexAttributePointer(mVertexBufferLayout);
        return this;
    }

    @Override
    public GLRenderer.GLRenderable unbind() {
        mRectShader.unbind();
        mTexture.unbind();
        return this;
    }

    @Override
    public GLRenderer.GLRenderable render() {
        mRectShader.setUniformMatrix4fv(mRectShader.getMVPMatrixLocation(), mModelViewProjectionM);
        mRectShader.setUniform4f(mRectShader.getColorLocation(), mColor);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        GLES20.glDisable(GLES20.GL_BLEND);
        return this;
    }


    @Override
    public Action translate(float x, float y, float z) {
        Matrix.translateM(mTranslateM, 0, mTranslateM, 0, x, y, z);
        updateMatrix();
        return this;
    }

    @Override
    public Action rotate(float a, float x, float y, float z) {
        Matrix.rotateM(mRotateM, 0, mRotateM, 0, a, x, y, z);
        updateMatrix();
        return this;
    }

    @Override
    public Action scale(float x, float y, float z) {
        Matrix.scaleM(mScaleM, 0, mScaleM, 0, x + 1f, y + 1f, z + 1f);
        updateMatrix();
        return this;
    }

    private void updateMatrix() {
        if (mRenderer == null) return;
        Matrix.setIdentityM(mModelM, 0);
        Matrix.multiplyMM(mModelM, 0, mScaleM, 0, mModelM, 0);
        Matrix.multiplyMM(mModelM, 0, mRotateM, 0, mModelM, 0);
        Matrix.multiplyMM(mModelM, 0, mTranslateM, 0, mModelM, 0);
        Matrix.multiplyMM(mModelViewM, 0, mRenderer.getViewMatrix(), 0, mModelM, 0);
        Matrix.multiplyMM(mModelViewProjectionM, 0, mRenderer.getProjectionMatrix(), 0, mModelViewM, 0);
    }

    @Override
    public Action tint(float r, float g, float b) {
        mColor[0] += r;
        mColor[1] += g;
        mColor[2] += b;
        mColor[0] = Utils.clamp(mColor[0], 0.0f, 1.0f);
        mColor[1] = Utils.clamp(mColor[1], 0.0f, 1.0f);
        mColor[2] = Utils.clamp(mColor[2], 0.0f, 1.0f);
        return this;
    }

    @Override
    public Action fade(float a) {
        mColor[3] += a;
        mColor[3] = Utils.clamp(mColor[3], 0.0f, 1.0f);
        return this;
    }

    public ActionInterval getActionInterval() {
        return mActionInterval;
    }

}
