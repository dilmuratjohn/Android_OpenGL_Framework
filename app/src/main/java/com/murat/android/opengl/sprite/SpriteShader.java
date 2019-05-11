package com.murat.android.opengl.sprite;

import android.content.Context;

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
        mMVPMatrixLocation = getUniformLocation(mMVPMatrix);
        mTextureLocation = getUniformLocation(mTexture);
        mColorLocation = getUniformLocation(mColor);
        mPositionLocation = getAttributeLocation(mPosition);
        mTexCoordLocation = getAttributeLocation(mTexCoord);
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
