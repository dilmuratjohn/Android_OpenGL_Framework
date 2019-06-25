package app;

import android.content.Context;

import com.murat.android.opengl.R;
import com.murat.android.opengl.common.shader.Shader;


class NumberShader extends Shader {


    final int uTexture;
    final int uView;
    final int uProjection;
    final int uScale;
    final int uTranslation;
    final int uScaleThreshold;
    final int aPosition;
    final int aTexCoord;
    final int aScale;

    NumberShader(Context context) {
        super(context, R.raw.number_vertex, R.raw.number_fragment);
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
