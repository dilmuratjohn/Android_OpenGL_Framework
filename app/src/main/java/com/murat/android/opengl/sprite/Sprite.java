package com.murat.android.opengl.sprite;

import android.content.Context;
import android.opengl.GLES20;

import com.murat.android.opengl.R;
import com.murat.android.opengl.node.Node;
import com.murat.android.opengl.render.Renderer;
import com.murat.android.opengl.common.buffer.VertexArray;
import com.murat.android.opengl.common.buffer.VertexAttributeArray;
import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.common.data.Vertices;
import com.murat.android.opengl.common.texture.Texture;


public class Sprite extends Node {

    private VertexArray mVertexArray;
    private VertexAttributeArray mVertexAttributeArray;
    private Texture mTexture;
    private SpriteShader mRectShader;
    private int mResourceId;

    private final float[] mVertices;

    public Sprite(Context context, int resourceId, float textureCoordinateOffsetX, float textureCoordinateOffsetY) {
        super(context);
        mResourceId = resourceId;
        float[] vertices = Vertices.Position4f_TexCoord2f;
        vertices[ 4] = .0f + .1f * textureCoordinateOffsetX;
        vertices[ 5] = .0f + .1f * textureCoordinateOffsetY;
        vertices[10] = .1f + .1f * textureCoordinateOffsetX;
        vertices[11] = .0f + .1f * textureCoordinateOffsetY;
        vertices[16] = .1f + .1f * textureCoordinateOffsetX;
        vertices[17] = .1f + .1f * textureCoordinateOffsetY;
        vertices[22] = .1f + .1f * textureCoordinateOffsetX;
        vertices[23] = .1f + .1f * textureCoordinateOffsetY;
        vertices[28] = .0f + .1f * textureCoordinateOffsetX;
        vertices[29] = .1f + .1f * textureCoordinateOffsetY;
        vertices[34] = .0f + .1f * textureCoordinateOffsetX;
        vertices[35] = .0f + .1f * textureCoordinateOffsetY;


        mVertices = vertices;
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
        mRectShader.setUniform1i(mRectShader.getTextureLocation(), 0);
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
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
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

}
