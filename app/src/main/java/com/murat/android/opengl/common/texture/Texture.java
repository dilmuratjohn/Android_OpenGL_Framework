package com.murat.android.opengl.common.texture;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class Texture {

    private static int mIndex = 0;
    private static int mTextureIndex = 0;
    private int[] mTexture = new int[32];

    public int getLocation(){
        return mTextureIndex;
    }

    public Texture(Context context, String name) {
        create(context, name);
    }

    public Texture(Context context, int id) {
        create(context, id);
    }

    private void create(Context context, int id){
        mTextureIndex = mIndex;
        Log.d("Murat", "location: " + mTextureIndex);
        if(mIndex >= 32){
            Log.d("OpenGL-Error", "Too much texture");
            return;
        }
        mTexture[mTextureIndex] = TextureHelper.create(context, id);
        mIndex++;
    }

    private void create(Context context, String name){
        mTexture[mTextureIndex] = TextureHelper.create(context, name);
    }

    public void bind() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + mTextureIndex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[mTextureIndex]);
    }

    public void unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureIndex);
    }

    public void delete() {
        GLES20.glDeleteTextures(1, mTexture, 0);
    }
}
