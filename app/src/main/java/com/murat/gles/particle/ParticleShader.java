package com.murat.gles.particle;

import android.content.Context;


import com.murat.gles.util.Shader;
import com.murat.particles.R;

import android.opengl.GLES20;


class ParticleShader extends Shader {

    private final int uMatrixLocation;
    private final int uTimeLocation;
    private final int uTextureUnitLocation;

    private final int aPointSize;
    private final int aPositionLocation;
    private final int aStartColorLocation;
    private final int aEndColorLocation;
    private final int aSpeedLocation;
    private final int aParticleStartTimeLocation;
    private final int aForceLocation;
    private final int aRotationLocation;

    private static final String U_MATRIX = "u_Matrix";
    private static final String U_TEXTURE_UNIT = "u_TextureUnit";
    private static final String U_TIME = "u_Time";

    private static final String A_POSITION = "a_Position";
    private static final String A_START_COLOR = "a_Start_Color";
    private static final String A_END_COLOR = "a_End_Color";
    private static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    private static final String A_SPEED = "a_Speed";
    private static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";
    private static final String A_POINT_SIZE = "a_PointSize";
    private static final String A_FORCE = "a_Force";
    private static final String A_ROTATION = "a_Rotation";


    ParticleShader(Context context) {
        super(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTimeLocation = GLES20.glGetUniformLocation(program, U_TIME);
        uTextureUnitLocation = GLES20.glGetAttribLocation(program, U_TEXTURE_UNIT);

        aPointSize = GLES20.glGetAttribLocation(program, A_POINT_SIZE);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aStartColorLocation = GLES20.glGetAttribLocation(program, A_START_COLOR);
        aEndColorLocation = GLES20.glGetAttribLocation(program, A_END_COLOR);
        aSpeedLocation = GLES20.glGetAttribLocation(program, A_SPEED);
        aParticleStartTimeLocation = GLES20.glGetAttribLocation(program, A_PARTICLE_START_TIME);
        aForceLocation = GLES20.glGetAttribLocation(program, A_FORCE);
        aRotationLocation = GLES20.glGetAttribLocation(program, A_ROTATION);
    }

    void setUniforms(float[] matrix, float elapsedTime, int textureId) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        GLES20.glUniform1f(uTimeLocation, elapsedTime);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    int getPositionLocation() {
        return aPositionLocation;
    }

    int getStartColorLocation() {
        return aStartColorLocation;
    }

    int getEndColorLocation() {
        return aEndColorLocation;
    }

    int getSpeedLocation() {
        return aSpeedLocation;
    }

    int getParticleStartTimeLocation() {
        return aParticleStartTimeLocation;
    }

    int getParticleSizeLocation() {
        return aPointSize;
    }

    int getForceLocation() {
        return aForceLocation;
    }

    int getRotationLocation(){
        return aRotationLocation;
    }
}
