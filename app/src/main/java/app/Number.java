package app;

import android.content.Context;
import android.opengl.GLES20;

import com.murat.android.opengl.Utils;
import com.murat.android.opengl.common.buffer.VertexArray;
import com.murat.android.opengl.common.buffer.VertexAttributeArray;
import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.common.data.Vertices;
import com.murat.android.opengl.common.texture.Texture;
import com.murat.android.opengl.render.Renderable;
import com.murat.android.opengl.render.Renderer;


public class Number implements Renderable {

    private Context mContext;
    private Renderer mRenderer;

    private float mScale;
    private float[] mTranslation;

    private VertexArray mVertexArray;
    private VertexAttributeArray mVertexAttributeArray;
    private Texture mTexture;
    private NumberShader mRectShader;
    private int mResourceId;
    private float[] mVertices;

    private int[][] mNumbers;
    private int[][] mPositions;
    private float[] mOriginScale;
    private float mScaleThreshold;

    public Number(Context context, int resourceId, int[][] numbers, int[][] positions, float[] originScale, float scaleThreshold) {
        mContext = context;
        mResourceId = resourceId;
        mNumbers = numbers;
        mPositions = positions;
        mOriginScale = originScale;
        mScaleThreshold = scaleThreshold;
        mTranslation = new float[]{0f, 0f};
        mScale = 1f;
        mVertices = new float[numbers.length * Vertices.Position4f_TexCoord2f_OriginSize1f.length];
    }


    @Override
    public void init(Renderer renderer) {
        mRenderer = renderer;
        mRectShader = new NumberShader(mContext);
        mTexture = new Texture(mContext, mResourceId);
        initVertices();
        mVertexArray = new VertexArray(mVertices);
        mVertexAttributeArray = new VertexAttributeArray();
        mVertexAttributeArray.push(mRectShader.aPosition, 4, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexAttributeArray.push(mRectShader.aTexCoord, 2, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexAttributeArray.push(mRectShader.aScale, 1, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mRectShader.setUniform1i(mRectShader.uTexture, 0);
        mRectShader.setUniform1f(mRectShader.uScaleThreshold, mScaleThreshold);
    }

    private void initVertices(){
        int offsetX;
        int offsetY;
        int length = Vertices.Position4f_TexCoord2f_OriginSize1f.length;
        for (int i = 0; i < this.mNumbers.length; i++) {
            offsetY = this.mNumbers[i][0];
            offsetX = this.mNumbers[i][1];
            //offset
            mVertices[i * length +  4] = .0f + .1f * offsetX;
            mVertices[i * length +  5] = .0f + .1f * offsetY;
            mVertices[i * length + 11] = .1f + .1f * offsetX;
            mVertices[i * length + 12] = .0f + .1f * offsetY;
            mVertices[i * length + 18] = .1f + .1f * offsetX;
            mVertices[i * length + 19] = .1f + .1f * offsetY;
            mVertices[i * length + 25] = .1f + .1f * offsetX;
            mVertices[i * length + 26] = .1f + .1f * offsetY;
            mVertices[i * length + 32] = .0f + .1f * offsetX;
            mVertices[i * length + 33] = .1f + .1f * offsetY;
            mVertices[i * length + 39] = .0f + .1f * offsetX;
            mVertices[i * length + 40] = .0f + .1f * offsetY;
            //position
            float ran = Utils.nextRandomInRange(100f,-100f);
            mVertices[i * length +  0] = -0.1f + 0.2f * ran;
            mVertices[i * length +  1] = -0.1f + 0.2f * ran;
            mVertices[i * length +  7] =  0.1f + 0.2f * ran;
            mVertices[i * length +  8] = -0.1f + 0.2f * ran;
            mVertices[i * length + 14] =  0.1f + 0.2f * ran;
            mVertices[i * length + 15] =  0.1f + 0.2f * ran;
            mVertices[i * length + 22] =  0.1f + 0.2f * ran;
            mVertices[i * length + 21] =  0.1f + 0.2f * ran;
            mVertices[i * length + 28] = -0.1f + 0.2f * ran;
            mVertices[i * length + 29] =  0.1f + 0.2f * ran;
            mVertices[i * length + 35] = -0.1f + 0.2f * ran;
            mVertices[i * length + 36] = -0.1f + 0.2f * ran;

            mVertices[i * length +  2] = 0.0f;
            mVertices[i * length +  3] = 1.0f;
            mVertices[i * length +  9] = 0.0f;
            mVertices[i * length + 10] = 1.0f;
            mVertices[i * length + 16] = 0.0f;
            mVertices[i * length + 17] = 1.0f;
            mVertices[i * length + 23] = 0.0f;
            mVertices[i * length + 24] = 1.0f;
            mVertices[i * length + 30] = 0.0f;
            mVertices[i * length + 31] = 1.0f;
            mVertices[i * length + 37] = 0.0f;
            mVertices[i * length + 38] = 1.0f;
            // scale
            ran = Utils.nextRandomInRange(1.5f,-0.5f);
            mVertices[i * length + 6] = ran;//this.mOriginScale[i];
            mVertices[i * length + 13] = ran;//this.mOriginScale[i];
            mVertices[i * length + 20] = ran;//this.mOriginScale[i];
            mVertices[i * length + 27] = ran;//this.mOriginScale[i];
            mVertices[i * length + 34] = ran;//this.mOriginScale[i];
            mVertices[i * length + 41] = ran;//this.mOriginScale[i];
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void bind() {
        if (mRectShader != null && mTexture != null) {
            mRectShader.bind();
            mTexture.bind();
            mVertexArray.setVertexAttributePointer(mVertexAttributeArray);
        }
    }

    @Override
    public void unbind() {
        if (mRectShader != null && mTexture != null) {
            mRectShader.unbind();
            mTexture.unbind();
        }
    }

    @Override
    public void render() {
        if (mRectShader != null) {
            mRectShader.setUniform1f(mRectShader.uScaleThreshold, mScaleThreshold);
            mRectShader.setUniform1f(mRectShader.uScale, mScale);
            mRectShader.setUniform2f(mRectShader.uTranslation, mTranslation[0], mTranslation[1]);
            mRectShader.setUniformMatrix4fv(mRectShader.uView, mRenderer.getViewMatrix());
            mRectShader.setUniformMatrix4fv(mRectShader.uProjection, mRenderer.getProjectionMatrix());
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0,this.mNumbers.length * 6);
            GLES20.glDisable(GLES20.GL_BLEND);
        }

    }

    @Override
    public void delete() {
        if (mRectShader != null) {
            mRectShader.delete();
        }
        if (mTexture != null) {
            mTexture.delete();
        }
    }

    public void translate(float x, float y) {
        mTranslation[0] += x;
        mTranslation[1] += y;
    }

    public void scale(float scale) {
        mScale *= 1 + scale;
    }

}

