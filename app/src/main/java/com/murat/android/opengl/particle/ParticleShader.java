package com.murat.android.opengl.particle;

import android.content.Context;
import android.opengl.GLES20;

import com.murat.android.opengl.R;
import com.murat.android.opengl.common.shader.Shader;


class ParticleShader extends Shader {

    private final int uMatrixLocation;
    private final int uTimeLocation;
    private final int uTextureUnitLocation;
    private final int uModeLocation;

    private final int aPositionLocation;
    private final int aStartColorLocation;
    private final int aEndColorLocation;
    private final int aSpeedLocation;
    private final int aStartSize;
    private final int aEndSize;
    private final int aStartTimeLocation;
    private final int aLifeTimeLocation;
    private final int aForceLocation;
    private final int aRotationLocation;
    private final int aAngleLocation;
    private final int aDegreePerSecondLocation;
    private final int aRadiusLocation;

    private static final String U_Matrix = "u_Matrix";
    private static final String U_Texture_Unit = "u_TextureUnit";
    private static final String U_Time = "u_Time";
    private static final String U_Mode = "u_Mode";

    private static final String A_Position = "a_Position";
    private static final String A_StartColor = "a_StartColor";
    private static final String A_EndColor = "a_EndColor";
    private static final String A_Speed = "a_Speed";
    private static final String A_Angle = "a_Angle";
    private static final String A_StartTime = "a_StartTime";
    private static final String A_StartSize = "a_StartSize";
    private static final String A_EndSize = "a_EndSize";
    private static final String A_Gravity = "a_Gravity";
    private static final String A_Rotation = "a_Rotation";
    private static final String A_LifeTime = "a_LifeTime";
    private static final String A_DegreePerSecond = "a_DegreePerSecond";
    private static final String A_Radius = "a_Radius";


    ParticleShader(Context context) {
        super(context, R.raw.particle_vertex, R.raw.particle_fragment);

        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_Matrix);
        uTimeLocation = GLES20.glGetUniformLocation(mProgram, U_Time);
        uTextureUnitLocation = GLES20.glGetUniformLocation(mProgram, U_Texture_Unit);
        uModeLocation = GLES20.glGetUniformLocation(mProgram, U_Mode);

        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_Position);
        aStartSize = GLES20.glGetAttribLocation(mProgram, A_StartSize);
        aEndSize = GLES20.glGetAttribLocation(mProgram, A_EndSize);
        aStartColorLocation = GLES20.glGetAttribLocation(mProgram, A_StartColor);
        aEndColorLocation = GLES20.glGetAttribLocation(mProgram, A_EndColor);
        aSpeedLocation = GLES20.glGetAttribLocation(mProgram, A_Speed);
        aAngleLocation = GLES20.glGetAttribLocation(mProgram, A_Angle);
        aStartTimeLocation = GLES20.glGetAttribLocation(mProgram, A_StartTime);
        aForceLocation = GLES20.glGetAttribLocation(mProgram, A_Gravity);
        aRotationLocation = GLES20.glGetAttribLocation(mProgram, A_Rotation);
        aLifeTimeLocation = GLES20.glGetAttribLocation(mProgram, A_LifeTime);
        aDegreePerSecondLocation = GLES20.glGetAttribLocation(mProgram, A_DegreePerSecond);
        aRadiusLocation = GLES20.glGetAttribLocation(mProgram, A_Radius);
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

    int getModeLocation() {
        return uModeLocation;
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

    int getStartTimeLocation() {
        return aStartTimeLocation;
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

    int getLifeTimeLocation() {
        return aLifeTimeLocation;
    }

    int getAngleLocation() {
        return aAngleLocation;
    }

    int getDegreePerSecondLocation() {
        return aDegreePerSecondLocation;
    }

    int getRadiusLocation() {
        return aRadiusLocation;
    }

}
