package com.murat.gles.picture;

import android.content.Context;
import android.opengl.GLES20;

import com.murat.gles.common.shader.Shader;
import com.murat.particles.R;


class SpriteShader extends Shader {

    private final int mMVPMatrixLocation;
    private static final String mMVPMatrix = "uMVPMatrix";

    private final int mColorLocation;
    private static final String mColor = "uColor";

    SpriteShader(Context context) {
        super(context, R.raw.baic_vertex, R.raw.basic_fragment);
        mMVPMatrixLocation = GLES20.glGetUniformLocation(mProgram, mMVPMatrix);
        mColorLocation = GLES20.glGetUniformLocation(mProgram, mColor);
    }

    int getMVPMatrixLocation() {
        return mMVPMatrixLocation;
    }

    int getColorLocation() {
        return mColorLocation;
    }

}
