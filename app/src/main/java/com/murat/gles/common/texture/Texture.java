package com.murat.gles.common.texture;

import android.content.Context;
import android.opengl.GLES20;

public class Texture {

    private int[] mTexture = new int[1];

    public Texture(Context context, String name) {
        create(context, name);
    }

    public Texture(Context context, int id) {
        create(context, id);
    }

    private void create(Context context, int id){
        mTexture[0] = TextureHelper.create(context, id);
    }

    private void create(Context context, String name){
        mTexture[0] = TextureHelper.create(context, name);
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
