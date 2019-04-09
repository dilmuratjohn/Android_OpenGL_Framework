package com.murat.gles.util;

import android.content.Context;

import static android.opengl.GLES20.glUseProgram;

public class Shader {

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_TIME = "u_Time";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";

    protected final int program;

    public Shader(Context context, int vertex, int fragment) {
        program = ShaderHelper.createProgram(
                TextResourceReader.readTextFileFromResource(context, vertex),
                TextResourceReader.readTextFileFromResource(context, fragment)
        );
    }

    public void useProgram() {
        glUseProgram(program);
    }
}
