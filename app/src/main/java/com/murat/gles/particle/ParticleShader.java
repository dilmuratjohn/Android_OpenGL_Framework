package com.murat.gles.particle;

import android.content.Context;

import com.murat.gles.common.GLShader;
import com.murat.particles.R;

import android.opengl.GLES20;


class ParticleShader extends GLShader {

    private final int uMatrixLocation;
    private final int uTimeLocation;
    private final int uTextureUnitLocation;

    private final int aPositionLocation;
    private final int aStartColorLocation;
    private final int aEndColorLocation;
    private final int aSpeedLocation;
    private final int aStartSize;
    private final int aEndSize;
    private final int aParticleStartTimeLocation;
    private final int aParticleLifeTimeLocation;
    private final int aForceLocation;
    private final int aRotationLocation;
    private final int aAngleLocation;

    private static final String U_Matrix = "u_Matrix";
    private static final String U_Texture_Unit = "u_TextureUnit";
    private static final String U_Time = "u_Time";

    private static final String A_Position = "a_Position";
    private static final String A_Start_Color = "a_StartColor";
    private static final String A_End_Color = "a_EndColor";
    private static final String A_Speed = "a_Speed";
    private static final String A_Angle = "a_Angle";
    private static final String A_Start_Time = "a_ParticleStartTime";
    private static final String A_Start_Size = "a_StartSize";
    private static final String A_End_Size = "a_EndSize";
    private static final String A_Force = "a_Gravity";
    private static final String A_Rotation = "a_Rotation";
    private static final String A_Life_Time = "a_ParticleLifeTime";


    ParticleShader(Context context) {
        super(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_Matrix);
        uTimeLocation = GLES20.glGetUniformLocation(mProgram, U_Time);
        uTextureUnitLocation = GLES20.glGetUniformLocation(mProgram, U_Texture_Unit);

        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_Position);
        aStartSize = GLES20.glGetAttribLocation(mProgram, A_Start_Size);
        aEndSize = GLES20.glGetAttribLocation(mProgram, A_End_Size);
        aStartColorLocation = GLES20.glGetAttribLocation(mProgram, A_Start_Color);
        aEndColorLocation = GLES20.glGetAttribLocation(mProgram, A_End_Color);
        aSpeedLocation = GLES20.glGetAttribLocation(mProgram, A_Speed);
        aAngleLocation = GLES20.glGetAttribLocation(mProgram, A_Angle);
        aParticleStartTimeLocation = GLES20.glGetAttribLocation(mProgram, A_Start_Time);
        aForceLocation = GLES20.glGetAttribLocation(mProgram, A_Force);
        aRotationLocation = GLES20.glGetAttribLocation(mProgram, A_Rotation);
        aParticleLifeTimeLocation = GLES20.glGetAttribLocation(mProgram, A_Life_Time);
    }

    int getMatrixLocation() {
        return uMatrixLocation;
    }

    int getTimeLocation() {
        return uTimeLocation;
    }

    int getTextureLocation() {
        return uTextureUnitLocation;
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

    int getStartSizeLocation() {
        return aStartSize;
    }

    int getEndSizeLocation() {
        return aEndSize;
    }

    int getForceLocation() {
        return aForceLocation;
    }

    int getRotationLocation() {
        return aRotationLocation;
    }

    int getParticleLifeTimeLocation() {
        return aParticleLifeTimeLocation;
    }

    int getAngleLocation() {
        return aAngleLocation;
    }
}
