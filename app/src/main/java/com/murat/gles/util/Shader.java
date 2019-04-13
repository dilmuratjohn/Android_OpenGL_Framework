package com.murat.gles.util;

import android.content.Context;

import android.opengl.GLES20;

public class Shader {

    protected final int program;

    public Shader(Context context, int vertex, int fragment) {
        program = ShaderHelper.createProgram(
                ShaderCodeReader.readTextFileFromResource(context, vertex),
                ShaderCodeReader.readTextFileFromResource(context, fragment)
        );
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}
