package com.murat.gles.common;

import android.content.Context;

public interface GLRenderable {
    void bind(Context context);

    void render(float[] mvp);
}
