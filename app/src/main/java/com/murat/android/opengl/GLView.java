package com.murat.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;

import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.render.Renderable;


public class GLView extends GLSurfaceView {

    private final com.murat.android.opengl.render.Renderer mRenderer;
    private final Handler mHandler;

    public GLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new com.murat.android.opengl.render.Renderer();
        mHandler = new Handler();
        setRenderer(mRenderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public GLView add(Renderable renderer) {
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

    public void start() {
        render();
    }

    private void render() {
        this.requestRender();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                render();
            }
        }, Constants.Delta_Time);
    }

    public void stop() {
        removeAll();
        mHandler.removeCallbacksAndMessages(null);
    }
}
