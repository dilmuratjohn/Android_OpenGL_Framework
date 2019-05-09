package com.murat.android.opengl.sprite;

import android.content.Context;
import android.opengl.GLES20;

import com.murat.android.opengl.R;
import com.murat.android.opengl.common.shader.Shader;


class SpriteShader extends Shader {

    private final int mMVPMatrixLocation;
    private final int mPositionLocation;
    private final int mTexCoordLocation;
    private static final String mMVPMatrix = "uMVPMatrix";
    private static final String mPosition = "aPosition";
    private static final String mTexCoord = "aTexCoord";

    private final int mTextureLocation;
    private static final String mTexture = "uTexture";
    private final int mColorLocation;
    private static final String mColor = "uColor";

    SpriteShader(Context context) {
        super(context, R.raw.basic_vertex, R.raw.basic_fragment);
        mMVPMatrixLocation = GLES20.glGetUniformLocation(mProgram, mMVPMatrix);
        mTextureLocation = GLES20.glGetUniformLocation(mProgram, mTexture);
        mColorLocation = GLES20.glGetUniformLocation(mProgram, mColor);
        mPositionLocation = GLES20.glGetAttribLocation(mProgram, mPosition);
        mTexCoordLocation = GLES20.glGetAttribLocation(mProgram, mTexCoord);
    }

    int getMVPMatrixLocation() {
        return mMVPMatrixLocation;
    }

    int getTextureLocation() {
        return mTextureLocation;
    }

    int getColorLocation() {
        return mColorLocation;
    }

    int getPositionLocation() {
        return mPositionLocation;
    }

    int getTexCoordLocation() {
        return mTexCoordLocation;
    }

}
