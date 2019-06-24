package com.murat.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;

import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.render.Renderable;


public class GLView extends GLSurfaceView {

    private final com.murat.android.opengl.render.Renderer mRenderer;
    private final Handler mHandler;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            GLView.this.requestRender();
            mHandler.postDelayed(mRunnable, Constants.Delta_Time);
        }
    };

    public interface Listener {
        void onSurfaceCreated();

        void onSurfaceChanged();
    }

    private Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void removeListener() {
        mListener = null;
    }

    public GLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new com.murat.android.opengl.render.Renderer();
        mRenderer.setListener(new com.murat.android.opengl.render.Renderer.Listener() {
            @Override
            public void onSurfaceCreated() {
                if (mListener != null) {
                    mListener.onSurfaceCreated();
                }
            }

            @Override
            public void onSurfaceChanged() {
                if (mListener != null) {
                    mListener.onSurfaceChanged();
                }
            }
        });
        mHandler = new Handler();
        setRenderer(mRenderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void add(Renderable renderer) {
        mRenderer.add(renderer);
    }

    public void addAll(Renderable[] renderableList){
        for(Renderable renderable: renderableList){
            mRenderer.add(renderable);
        }
    }

    public void remove(int index) {
        mRenderer.remove(index);
    }

    public void removeAll() {
        mRenderer.clear();
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
