package com.murat.android.opengl.render;

public interface Renderable {
    void init(Renderer renderer);

    void update();

    void bind();

    void unbind();

    void render();

    void delete();
}
