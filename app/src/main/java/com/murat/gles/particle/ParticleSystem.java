package com.murat.gles.particle;

import com.murat.gles.util.VertexArray;
import com.murat.gles.util.MathUtils;

import android.opengl.GLES20;

import static com.murat.gles.Constants.BYTES_PER_FLOAT;

class ParticleSystem {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private static final int VECTOR_COMPONENT_COUNT = 2;
    private static final int PARTICLE_START_TIME_COMPONENT_COUNT = 1;
    private static final int PARTICLE_SIZE_COMPONENT_COUNT = 1;
    private static final int GRAVITY_FACTOR_COMPONENT_COUNT = 4;
    private static final int ROTATION_COMPONENT_COUNT = 1;

    private static final int TOTAL_COMPONENT_COUNT =
            POSITION_COMPONENT_COUNT
                    + COLOR_COMPONENT_COUNT
                    + COLOR_COMPONENT_COUNT
                    + VECTOR_COMPONENT_COUNT
                    + PARTICLE_START_TIME_COMPONENT_COUNT
                    + PARTICLE_SIZE_COMPONENT_COUNT
                    + GRAVITY_FACTOR_COMPONENT_COUNT
                    + ROTATION_COMPONENT_COUNT;

    private static final int STRIDE = TOTAL_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticleCount;

    private int currentParticleCount;
    private int nextParticle;

    ParticleSystem(int maxParticleCount) {
        particles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
        vertexArray = new VertexArray(particles);
        this.maxParticleCount = maxParticleCount;
    }

    void addParticle(
            MathUtils.Vec3 position,
            MathUtils.Vec4 startColor,
            MathUtils.Vec4 endColor,
            MathUtils.Vec2 speed,
            float particleStarTime,
            float particleSize,
            MathUtils.Vec4 force,
            MathUtils.Vec2 rotation
    ) {
        final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;

        int currentOffset = particleOffset;
        nextParticle++;

        if (currentParticleCount < maxParticleCount) {
            currentParticleCount++;
        }

        if (nextParticle == maxParticleCount) {
            nextParticle = 0;
        }

        particles[currentOffset++] = position.x;
        particles[currentOffset++] = position.y;
        particles[currentOffset++] = position.z;
        particles[currentOffset++] = startColor.x;
        particles[currentOffset++] = startColor.y;
        particles[currentOffset++] = startColor.z;
        particles[currentOffset++] = startColor.w;
        particles[currentOffset++] = endColor.x;
        particles[currentOffset++] = endColor.y;
        particles[currentOffset++] = endColor.z;
        particles[currentOffset++] = endColor.w;
        particles[currentOffset++] = speed.x;
        particles[currentOffset++] = speed.y;
        particles[currentOffset++] = particleStarTime;
        particles[currentOffset++] = particleSize;
        particles[currentOffset++] = force.x;
        particles[currentOffset++] = force.y;
        particles[currentOffset++] = force.z;
        particles[currentOffset++] = force.w;
        particles[currentOffset++] = rotation.x;

        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);
    }

    void bindData(ParticleShader program) {
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset, program.getPositionLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, program.getStartColorLocation(), COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, program.getEndColorLocation(), COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, program.getSpeedLocation(), VECTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, program.getParticleStartTimeLocation(), PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
        dataOffset += PARTICLE_START_TIME_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, program.getParticleSizeLocation(), PARTICLE_SIZE_COMPONENT_COUNT, STRIDE);
        dataOffset += PARTICLE_SIZE_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, program.getForceLocation(), GRAVITY_FACTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += GRAVITY_FACTOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, program.getRotationLocation(), ROTATION_COMPONENT_COUNT, STRIDE);
    }

    void draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount);
    }
}
