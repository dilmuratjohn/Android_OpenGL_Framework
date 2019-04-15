package com.murat.gles.common;

import android.content.Context;

import android.opengl.GLES20;

public class GLShader {

    protected final int program;

    public GLShader(Context context, int vertex, int fragment) {
        program = GLShaderHelper.createProgram(
                GLShaderCodeReader.readTextFileFromResource(context, vertex),
                GLShaderCodeReader.readTextFileFromResource(context, fragment)
        );
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}
