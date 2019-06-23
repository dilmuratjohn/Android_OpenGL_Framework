package com.murat.android.opengl.sprite;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.murat.android.opengl.node.Node;
import com.murat.android.opengl.render.Renderer;
import com.murat.android.opengl.common.buffer.VertexArray;
import com.murat.android.opengl.common.buffer.VertexAttributeArray;
import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.common.data.Vertices;
import com.murat.android.opengl.common.texture.Texture;


public class Sprite extends Node {

    protected VertexArray mVertexArray;
    protected VertexAttributeArray mVertexAttributeArray;
    protected Texture mTexture;
    protected SpriteShader mRectShader;
    protected int mResourceId;
    protected float[] mVertices;

    public Sprite(Context context, int resourceId) {
        super(context);
        mResourceId = resourceId;
        mVertices = Vertices.Position4f_TexCoord2f_OriginSize1f;
    }

    private int[][] numbers;
    private int[][] positions;
    private int[] originScale;
    private float scaleThreshold;

    public Sprite(Context context, int resourceId, int[][] numbers, int[][] positions, int[] originScale, float scaleThreshold) {
        super(context);
        mVertices = new float[numbers.length * Vertices.Position4f_TexCoord2f_OriginSize1f.length];
        mResourceId = resourceId;
        this.numbers = numbers;
        this.positions = positions;
        this.originScale = originScale;
        this.scaleThreshold = scaleThreshold;
        init();
    }

    public void init() {
        int textureCoordinateOffsetX;
        int textureCoordinateOffsetY;
        int length = Vertices.Position4f_TexCoord2f_OriginSize1f.length;
        for (int i = 0; i < this.numbers.length; i++) {
            Log.d("Murat", "x:" + this.numbers[i][0] + " y: " + this.numbers[i][0]);
            textureCoordinateOffsetX = this.numbers[i][0];
            textureCoordinateOffsetY = this.numbers[i][1];
            //offset
            mVertices[i* + 4] = .0f + .1f * textureCoordinateOffsetX;
            mVertices[i*length + 5] = .0f + .1f * textureCoordinateOffsetY;
            mVertices[i*length + 11] = .1f + .1f * textureCoordinateOffsetX;
            mVertices[i*length + 12] = .0f + .1f * textureCoordinateOffsetY;
            mVertices[i*length + 18] = .1f + .1f * textureCoordinateOffsetX;
            mVertices[i*length + 19] = .1f + .1f * textureCoordinateOffsetY;
            mVertices[i*length + 25] = .1f + .1f * textureCoordinateOffsetX;
            mVertices[i*length + 26] = .1f + .1f * textureCoordinateOffsetY;
            mVertices[i*length + 32] = .0f + .1f * textureCoordinateOffsetX;
            mVertices[i*length + 33] = .1f + .1f * textureCoordinateOffsetY;
            mVertices[i*length + 39] = .0f + .1f * textureCoordinateOffsetX;
            mVertices[i*length + 40] = .0f + .1f * textureCoordinateOffsetY;

            //position

            // -0.1f, -0.1f, 0.0f, 1.0f,
            mVertices[i*length + 0] = -0.1f + 0.3f * i;
            mVertices[i*length + 1] = -0.1f;
            mVertices[i*length + 2] = 0.0f;
            mVertices[i*length + 3] = 1.0f;
            //0.1f, -0.1f, 0.0f, 1.0f,
            mVertices[i*length + 7] = 0.1f + 0.3f * i;
            mVertices[i*length + 8] = -0.1f;
            mVertices[i*length + 9] = 0.0f;
            mVertices[i*length + 10] = 1.0f;
            //0.1f,  0.1f, 0.0f, 1.0f,

            mVertices[i*length + 14] = 0.1f + 0.3f * i;
            mVertices[i*length + 15] = 0.1f;
            mVertices[i*length + 16] = 0.0f;
            mVertices[i*length + 17] = 1.0f;
//0.1f,  0.1f, 0.0f, 1.0f,

            mVertices[i*length + 21] = 0.1f + 0.3f * i;
            mVertices[i*length + 22] = 0.1f;
            mVertices[i*length + 23] = 0.0f;
            mVertices[i*length + 24] = 1.0f;
//                    -0.1f,  0.1f, 0.0f, 1.0f,
            mVertices[i*length + 28] = -0.1f + 0.3f * i;
            mVertices[i*length + 29] = 0.1f;
            mVertices[i*length + 30] = 0.0f;
            mVertices[i*length + 31] = 1.0f;
//                    -0.1f, -0.1f, 0.0f, 1.0f,

            mVertices[i*length + 35] = -0.1f + 0.3f * i;
            mVertices[i*length + 36] = -0.1f;
            mVertices[i*length + 37] = 0.0f;
            mVertices[i*length + 38] = 1.0f;


            // scale
            mVertices[i*length + 6] = this.originScale[i];
            mVertices[i*length + 13] = this.originScale[i];
            mVertices[i*length + 20] = this.originScale[i];
            mVertices[i*length + 27] = this.originScale[i];
            mVertices[i*length + 34] = this.originScale[i];
            mVertices[i*length + 41] = this.originScale[i];


        }
    }

    @Override
    public void init(Renderer renderer) {
        super.init(renderer);
        mRectShader = new SpriteShader(mContext);
        mTexture = new Texture(mContext, mResourceId);
        mVertexAttributeArray = new VertexAttributeArray();
        mVertexArray = new VertexArray(mVertices);
        mVertexAttributeArray.push(mRectShader.getPositionLocation(), 4, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexAttributeArray.push(mRectShader.getTexCoordLocation(), 2, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexAttributeArray.push(mRectShader.getScaleLocation(), 1, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mRectShader.setUniform1i(mRectShader.getTextureLocation(), 0);
        mRectShader.setUniform1f(mRectShader.getScaleThresholdLocation(), 1f);
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
            mRectShader.setUniform4f(mRectShader.getColorLocation(), mColor);
            mRectShader.setUniformMatrix4fv(mRectShader.getMVPMatrixLocation(), mModelViewProjectionM);
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3 * getTriangleCount() );
            GLES20.glDisable(GLES20.GL_BLEND);
        }

    }

    private int getTriangleCount(){
        if(this.numbers == null){
            return 2;
        }
        return this.numbers.length * 2;
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

}
