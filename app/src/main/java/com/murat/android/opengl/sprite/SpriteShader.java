package com.murat.android.opengl.sprite;

import android.content.Context;
import android.util.Log;

import com.murat.android.opengl.R;
import com.murat.android.opengl.common.shader.Shader;


class SpriteShader extends Shader {

    private final int mMVPMatrixLocation;
    private final int mPositionLocation;
    private final int mTexCoordLocation;
    private final int mScaleLocation;
    private final int  mScaleThresholdLocation;
    private static final String mMVPMatrix = "uMVPMatrix";
    private static final String mScaleThreshold = "uScaleThreshold";
    private static final String mPosition = "aPosition";
    private static final String mTexCoord = "aTexCoord";
    private static final String mScale = "aScale";

    private final int mTextureLocation;
    private static final String mTexture = "uTexture";
    private final int mColorLocation;
    private static final String mColor = "uColor";

    SpriteShader(Context context) {
        super(context, R.raw.basic_vertex, R.raw.basic_fragment);
        mMVPMatrixLocation = getUniformLocation(mMVPMatrix);
        mTextureLocation = getUniformLocation(mTexture);
        mColorLocation = getUniformLocation(mColor);
        mScaleThresholdLocation = getUniformLocation(mScaleThreshold);
        mPositionLocation = getAttributeLocation(mPosition);
        mTexCoordLocation = getAttributeLocation(mTexCoord);
        mScaleLocation = getAttributeLocation(mScale);
        Log.d("Murat", "Texture Location: " + mTextureLocation);
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

    int getScaleThresholdLocation(){
        return mScaleThresholdLocation;
    }

    int getPositionLocation() {
        return mPositionLocation;
    }

    int getTexCoordLocation() {
        return mTexCoordLocation;
    }

    int getScaleLocation(){
        return mScaleLocation;
    }

}
