package com.murat.android.opengl.render;

public interface Renderable {
    Renderable init(Renderer renderer);

    Renderable update();

    Renderable bind();

    Renderable unbind();

    Renderable render();

    Renderable delete();
}
