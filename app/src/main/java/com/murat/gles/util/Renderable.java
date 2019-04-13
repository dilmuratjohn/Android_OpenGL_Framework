package com.murat.gles.util;

import android.content.Context;

public interface Renderable {
    void bind(Context context);
    void render(float[] mvp);
}
