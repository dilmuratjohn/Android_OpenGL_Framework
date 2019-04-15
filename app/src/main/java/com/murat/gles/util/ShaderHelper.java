package com.murat.gles.util;

import android.opengl.GLES20;
import android.util.Log;

class ShaderHelper {

    private static final String TAG_ERROR = "[Error] -> ";

    private static int compile(int type, String shaderCode) {
        final int id = GLES20.glCreateShader(type);
        if (id == 0) {
            Log.e(TAG_ERROR, "error creating shader.");
            return 0;
        }
        GLES20.glShaderSource(id, shaderCode);
        GLES20.glCompileShader(id);
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(id, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(id);
            Log.e(TAG_ERROR, "error compiling shader." + "\n" + shaderCode + "\n:" + GLES20.glGetShaderInfoLog(id));
            return 0;
        }
        return id;
    }

    private static void link(int program, int vertex, int fragment) {
        GLES20.glAttachShader(program, vertex);
        GLES20.glAttachShader(program, fragment);
        GLES20.glLinkProgram(program);
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == GLES20.GL_FALSE) {
            GLES20.glDeleteProgram(program);
            Log.w(TAG_ERROR, "error linking program.");
        }
    }

    private static void validate(int program) {
        GLES20.glValidateProgram(program);
        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        if (validateStatus[0] == GLES20.GL_FALSE) {
            Log.w(TAG_ERROR, "error validating program." + "\n:" + GLES20.glGetProgramInfoLog(program));
        }
    }

    static int createProgram(String vertexShaderSource, String fragmentShaderSource) {

        final int program = GLES20.glCreateProgram();
        if (program == 0) {
            Log.e(TAG_ERROR, "error creating program.");
        }
        int vertexShader = compile(GLES20.GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = compile(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource);
        link(program, vertexShader, fragmentShader);
        validate(program);
        return program;
    }

}
