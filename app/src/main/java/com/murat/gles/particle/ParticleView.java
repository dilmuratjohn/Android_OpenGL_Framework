package com.murat.gles.particle;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ParticleView extends GLSurfaceView {

    private final ParticleRenderer mRenderer;

    public ParticleView(Context context){
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new ParticleRenderer(getContext());
        setRenderer(mRenderer);
    }

}
