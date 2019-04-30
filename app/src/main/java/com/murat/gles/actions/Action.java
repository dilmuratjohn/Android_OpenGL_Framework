package com.murat.gles.actions;

public interface Action {

    Action translate(float x, float y, float z);

    Action rotate(float a, float x, float y, float z);

    Action scale(float x, float y, float z);

    Action fade(float a);

    Action tint(float r, float g, float b);

}
