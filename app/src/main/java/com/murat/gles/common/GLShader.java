package com.murat.gles.common;

import android.content.Context;

import android.opengl.GLES20;

public class GLShader {

    protected final int mProgram;

    public GLShader(Context context, int vertexShaderId, int fragmentShaderId) {
        mProgram = GLShaderHelper.create(context, vertexShaderId, fragmentShaderId);
    }

    public void bind() {
        GLES20.glUseProgram(mProgram);
    }

    public void unbind() {
        GLES20.glUseProgram(0);
    }

    public void delete() {
        GLES20.glDeleteProgram(mProgram);
    }

    public void setUniformMatrix4fv(final int location, float[] matrix) {
        GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0);
    }

    public void setUniform1f(final int location, float x) {
        GLES20.glUniform1f(location, x);
    }

    public void setUniform1i(final int location, int x) {
        GLES20.glUniform1i(location, x);
    }
}
