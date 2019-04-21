package com.murat.gles.common;

import android.content.Context;

public interface GLRenderable {
    GLRenderable init(Context context);
    GLRenderable bind();
    GLRenderable unbind();
    GLRenderable render(float[] mvp);
}
