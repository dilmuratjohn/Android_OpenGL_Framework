package com.murat.gles.common;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLView extends GLSurfaceView {

    GLRenderer mRenderer;

    public GLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new GLRenderer(getContext());
        setRenderer(mRenderer);
    }

    public void add(GLRenderer.GLRenderable renderer) {
        mRenderer.add(renderer);
    }

    public void remove(int index) {
        mRenderer.remove(index);
    }

    public void removeAll() {
        mRenderer.clear();
    }

}
