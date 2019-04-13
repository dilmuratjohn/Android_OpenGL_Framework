package com.murat.gles.util;

import android.content.Context;

public interface Renderer {
    void bind(Context context);
    void render(float[] mvp);
    void clear();
}
