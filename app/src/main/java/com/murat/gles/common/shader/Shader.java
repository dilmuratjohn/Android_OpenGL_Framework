package com.murat.gles.common.shader;

import android.content.Context;

import android.opengl.GLES20;

public class Shader {

    protected final int mProgram;

    public Shader(Context context, int vertexShaderId, int fragmentShaderId) {
        mProgram = ShaderHelper.create(context, vertexShaderId, fragmentShaderId);
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

    public void setUniform4f(final int location, float[] vector) {
        GLES20.glUniform4f(location, vector[0], vector[1], vector[2], vector[3]);
    }

    public void setUniform1f(final int location, float x) {
        GLES20.glUniform1f(location, x);
    }

    public void setUniform1i(final int location, int x) {
        GLES20.glUniform1i(location, x);
    }
}
