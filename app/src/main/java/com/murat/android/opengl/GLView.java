package com.murat.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLView extends GLSurfaceView {

    GLRenderer mRenderer;

    public GLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new GLRenderer();
        setRenderer(mRenderer);
    }

    public GLView add(GLRenderer.GLRenderable renderer) {
        mRenderer.add(renderer);
        return this;
    }

    public GLView remove(int index) {
        mRenderer.remove(index);
        return this;
    }

    public GLView removeAll() {
        mRenderer.clear();
        return this;
    }
}
