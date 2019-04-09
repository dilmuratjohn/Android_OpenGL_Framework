package com.murat.gles.util;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FALSE;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_NO_ERROR;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

class ShaderHelper {

    private static final String TAG_ERROR = "[Error] -> ";

    private static int compile(int type, String shaderCode) {
        final int id = glCreateShader(type);
        if (id == 0) {
            Log.e(TAG_ERROR, "error creating shader.");
            return 0;
        }
        glShaderSource(id, shaderCode);
        glCompileShader(id);
        final int[] compileStatus = new int[1];
        glGetShaderiv(id, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(id);
            Log.e(TAG_ERROR, "error compiling shader." + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(id));
            return 0;
        }
        return id;
    }

    private static void link(int program, int vertex, int fragment) {
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);
        final int[] linkStatus = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == GL_FALSE) {
            glDeleteProgram(program);
            Log.w(TAG_ERROR, "error linking program.");
        }

    }

    private static void validate(int program) {
        glValidateProgram(program);
        final int[] status = new int[1];
        glGetProgramiv(program, GL_VALIDATE_STATUS, status, 0);
        if (status[0] != GL_NO_ERROR) {
            Log.w(TAG_ERROR, "error validating program." + "\n:" + glGetProgramInfoLog(program));
        }
    }

    static int createProgram(String vertexShaderSource, String fragmentShaderSource) {

        final int program = glCreateProgram();
        if (program == 0) {
            Log.e(TAG_ERROR, "error creating program.");
        }
        int vertexShader = compile(GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = compile(GL_FRAGMENT_SHADER, fragmentShaderSource);
        link(program, vertexShader, fragmentShader);
        validate(program);
        return program;
    }

}
