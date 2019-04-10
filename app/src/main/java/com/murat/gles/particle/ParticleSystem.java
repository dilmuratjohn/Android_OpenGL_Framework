package com.murat.gles.particle;

import com.murat.gles.util.VertexArray;
import com.murat.gles.util.MathUtils;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static com.murat.gles.Constants.BYTES_PER_FLOAT;

class ParticleSystem {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private static final int VECTOR_COMPONENT_COUNT = 3;
    private static final int PARTICLE_START_TIME_COMPONENT_COUNT = 1;

    private static final int TOTAL_COMPONENT_COUNT =
            POSITION_COMPONENT_COUNT
                    + COLOR_COMPONENT_COUNT
                    + VECTOR_COMPONENT_COUNT
                    + PARTICLE_START_TIME_COMPONENT_COUNT;

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

    void addParticle(MathUtils.Vec3 position, MathUtils.Vec4 color, MathUtils.Vec3 direction, float particleStarTime) {
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
        particles[currentOffset++] = color.x;
        particles[currentOffset++] = color.y;
        particles[currentOffset++] = color.z;
        particles[currentOffset++] = color.w;
        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.y;
        particles[currentOffset++] = direction.z;
        particles[currentOffset++] = particleStarTime;

        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);

    }

    void bindData(ParticleShader program) {
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset, program.getPositionLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, program.getColorLocation(), COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, program.getDirectionVectorLocation(), VECTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, program.getParticleStartTimeLocation(), PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
    }

    void draw() {
        glDrawArrays(GL_POINTS, 0, currentParticleCount);
    }
}
