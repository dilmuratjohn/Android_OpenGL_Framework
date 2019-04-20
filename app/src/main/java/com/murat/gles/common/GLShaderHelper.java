package com.murat.gles.common;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class GLShaderHelper {

    private static final String TAG_ERROR = "[OpenGL Error] -> ";

    private static String parse(Context context, int id) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(id);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                content.append(nextLine);
                content.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (Resources.NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        return content.toString();
    }

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
            Log.e(TAG_ERROR, "error compiling shader." + "\n" + GLES20.glGetShaderInfoLog(id));
            GLES20.glDeleteShader(id);
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
            Log.w(TAG_ERROR, "error linking program.");
            GLES20.glDeleteProgram(program);
        }
    }

    private static void validate(int program) {
        GLES20.glValidateProgram(program);
        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        if (validateStatus[0] == GLES20.GL_FALSE) {
            Log.w(TAG_ERROR, "error validating program." + "\n" + GLES20.glGetProgramInfoLog(program));
        }
    }

    static int create(Context context, int vertexShaderSourceId, int fragmentShaderSourceId) {

        final int program = GLES20.glCreateProgram();
        if (program == 0) {
            Log.e(TAG_ERROR, "error creating program.");
        }

        String vertexShaderCode = parse(context, vertexShaderSourceId);
        String fragmentShaderCode = parse(context, fragmentShaderSourceId);
        int vertexShader = compile(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = compile(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        link(program, vertexShader, fragmentShader);
        validate(program);
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader);
        return program;
    }

}
