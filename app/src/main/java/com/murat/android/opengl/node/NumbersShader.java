package com.murat.android.opengl.node;

import android.content.Context;

import com.murat.android.opengl.R;
import com.murat.android.opengl.common.shader.Shader;


class NumbersShader extends Shader {


    final int uTexture;
    final int uView;
    final int uProjection;
    final int uScale;
    final int uTranslation;
    final int uScaleThreshold;
    final int aPosition;
    final int aTexCoord;
    final int aScale;

    NumbersShader(Context context) {
        super(context, R.raw.basic_vertex, R.raw.basic_fragment);
        uTexture = getUniformLocation("uTexture");
        uView = getUniformLocation("uView");
        uProjection = getUniformLocation("uProjection");
        uScale = getUniformLocation("uScale");
        uTranslation = getUniformLocation("uTranslation");
        uScaleThreshold = getUniformLocation("uScaleThreshold");
        aPosition = getAttributeLocation("aPosition");
        aTexCoord = getAttributeLocation("aTexCoord");
        aScale = getAttributeLocation("aScale");
    }


}
