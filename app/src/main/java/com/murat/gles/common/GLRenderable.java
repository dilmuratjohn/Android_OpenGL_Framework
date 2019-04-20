package com.murat.gles.common;

import android.content.Context;

public interface GLRenderable {
    void init(Context context);
    void bind();
    void unbind();
    void render(float[] mvp);
}
