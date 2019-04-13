package com.murat.gles;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ParticleView extends GLSurfaceView {

    public ParticleView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        ParticleRenderer renderer = new ParticleRenderer(getContext());
        setRenderer(renderer);
    }

}
