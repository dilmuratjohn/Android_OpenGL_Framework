package com.murat.android.opengl.actions;

public interface Action {

    void translate(float x, float y, float z);

    void rotate(float a, float x, float y, float z);

    void scale(float x, float y, float z);

    void fade(float a);

    void tint(float r, float g, float b);

}
