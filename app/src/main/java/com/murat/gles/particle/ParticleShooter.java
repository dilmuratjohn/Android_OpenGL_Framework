package com.murat.gles.particle;

import com.murat.gles.util.MathUtils;

import java.util.Random;

import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setRotateEulerM;


class ParticleShooter {

    private final MathUtils.Vector position;
    private final MathUtils.Vector direction;
    private final int color;

    private final float angleVariance;
    private final float speedVariance;

    private final Random random = new Random();

    private float[] rotationMatrix = new float[16];
    private float[] directionVector = new float[4];
    private float[] resultVector = new float[4];

    ParticleShooter(MathUtils.Vector position, MathUtils.Vector direction, int color, float angleVarianceInDegrees, float speedVariance) {
        this.position = position;
        this.direction = direction;
        this.color = color;

        this.angleVariance = angleVarianceInDegrees;
        this.speedVariance = speedVariance;

        directionVector[0] = direction.x;
        directionVector[1] = direction.y;
        directionVector[2] = direction.z;

    }

    void addParticles(ParticleSystem particleSystem, float currentTime, int count) {
        for (int i = 0; i < count; i++) {
            // create angle
            setRotateEulerM(
                    rotationMatrix,
                    0,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance
            );

            // use angle to create particle heading different directions
            multiplyMV(resultVector, 0, rotationMatrix, 0, directionVector, 0);

            // use random to differentiate speed
            float speedAdjustment = 1f + random.nextFloat() * speedVariance;

            MathUtils.Vector thisDirection = new MathUtils.Vector(
                    resultVector[0] * speedAdjustment,
                    resultVector[1] * speedAdjustment,
                    resultVector[2] * speedAdjustment
            );

            particleSystem.addParticle(position, color, thisDirection, currentTime);
        }
    }

    void addTrack(ParticleSystem particleSystem, float currentTime, int count) {
        for (int i = 0; i < count; i++) {
            // create angle
            setRotateEulerM(
                    rotationMatrix,
                    0,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance
            );

            // use angle to create particle heading different directions
            multiplyMV(resultVector, 0, rotationMatrix, 0, directionVector, 0);

            // use random to differentiate speed
            float speedAdjustment = 1f + random.nextFloat() * speedVariance;

            MathUtils.Vector thisDirection = new MathUtils.Vector(
                    resultVector[0] * speedAdjustment,
                    resultVector[1] * speedAdjustment,
                    resultVector[2] * speedAdjustment
            );

            particleSystem.addParticle(position, color, thisDirection, currentTime - 0.08f);
            particleSystem.addParticle(position, color, thisDirection, currentTime - 0.06f);
            particleSystem.addParticle(position, color, thisDirection, currentTime - 0.04f);
            particleSystem.addParticle(position, color, thisDirection, currentTime - 0.02f);
            particleSystem.addParticle(position, color, thisDirection, currentTime);
        }
    }
}
