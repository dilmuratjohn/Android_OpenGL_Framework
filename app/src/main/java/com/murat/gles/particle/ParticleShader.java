package com.murat.gles.particle;

import android.content.Context;


import com.murat.gles.util.Shader;
import com.murat.particles.R;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;


class ParticleShader extends Shader {

    private final int uMatrixLocation;
    private final int uTimeLocation;
    private final int uTextureUnitLocation;

    private final int aPointSize;
    private final int aPositionLocation;
    private final int aStartColorLocation;
    private final int aEndColorLocation;
    private final int aDirectionVectorLocation;
    private final int aParticleStartTimeLocation;
    private final int aGravityFactorLocation;

    ParticleShader(Context context) {
        super(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader);

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTimeLocation = glGetUniformLocation(program, U_TIME);
        uTextureUnitLocation = glGetAttribLocation(program, U_TEXTURE_UNIT);

        aPointSize = glGetAttribLocation(program, A_POINT_SIZE);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aStartColorLocation = glGetAttribLocation(program, A_START_COLOR);
        aEndColorLocation = glGetAttribLocation(program, A_END_COLOR);
        aDirectionVectorLocation = glGetAttribLocation(program, A_DIRECTION_VECTOR);
        aParticleStartTimeLocation = glGetAttribLocation(program, A_PARTICLE_START_TIME);
        aGravityFactorLocation = glGetAttribLocation(program, A_GRAVITY_FACTOR);
    }

    void setUniforms(float[] matrix, float elapsedTime, int textureId) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform1f(uTimeLocation, elapsedTime);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);
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

    int getDirectionVectorLocation() {
        return aDirectionVectorLocation;
    }

    int getParticleStartTimeLocation() { return aParticleStartTimeLocation; }

    int getParticleSizeLocation() { return aPointSize; }

    int getGravityFactorLocation(){
        return aGravityFactorLocation;
    }
}
