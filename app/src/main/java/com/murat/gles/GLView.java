package com.murat.gles;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.murat.gles.util.Renderable;

public class GLView extends GLSurfaceView {

    GLRenderer mRenderer;
    public GLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new GLRenderer(getContext());
        setRenderer(mRenderer);
    }

    public void add(Renderable renderer, String name){
        mRenderer.add(renderer, name);
    }

    public void remove(String name){
        mRenderer.remove(name);
    }

    public void removeAll(){
        mRenderer.removeAll();
    }

}
