package com.murat.gles.common;

import android.content.Context;
import android.opengl.GLES20;

public class GLTexture {

    private int[] mTexture = new int[1];

    public GLTexture(Context context, String name) {
        mTexture[0] = GLTextureHelper.create(context, name);
    }

    public GLTexture(Context context, int id) {
        mTexture[0] = GLTextureHelper.create(context, id);
    }

    public void bind() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[0]);
    }

    public void unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public void delete() {
        GLES20.glDeleteTextures(1, mTexture, 0);
    }
}
