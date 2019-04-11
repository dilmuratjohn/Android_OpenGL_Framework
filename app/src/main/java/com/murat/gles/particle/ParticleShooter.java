package com.murat.gles.particle;

import com.murat.gles.util.MathUtils;

import java.util.Random;

import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setRotateEulerM;


class ParticleShooter {

    private final Random random = new Random();

    private final MathUtils.Vec3 position;
    private final MathUtils.Vec4 mStartColor;
    private final MathUtils.Vec4 mEndColor;
    private final MathUtils.Vec2 speed;
    private final MathUtils.Vec2 angle;

    private float[] rotationMatrix = new float[16];
    private float[] directionVector = new float[4];
    private float[] resultVector = new float[4];

    ParticleShooter(MathUtils.Vec3 position,
                    MathUtils.Vec3 direction,
                    MathUtils.Vec4 startColor,
                    MathUtils.Vec4 endColor,
                    MathUtils.Vec2 speed,
                    MathUtils.Vec2 angle
                   ) {

        this.position = position;
        this.mStartColor = startColor;
        this.mEndColor = endColor;
        this.speed = speed;
        this.angle = angle;

        directionVector[0] = direction.x;
        directionVector[1] = direction.y;
        directionVector[2] = direction.z;

    }

    void addParticles(ParticleSystem particleSystem, float currentTime, int count, float particleSize, MathUtils.Vec2 gravityFactor,boolean isTrack) {
        for (int i = 0; i < count; i++) {
            // create angle
            setRotateEulerM(
                    rotationMatrix,
                    0,
                    (random.nextFloat() - 0.5f) * angle.y + angle.x,
                    (random.nextFloat() - 0.5f) * angle.y + angle.x,
                    (random.nextFloat() - 0.5f) * angle.y + angle.x
            );

            // use angle to create particle heading different directions
            multiplyMV(resultVector, 0, rotationMatrix, 0, directionVector, 0);

            // use random to differentiate speed
            float speedAdjustment = speed.x + random.nextFloat() * speed.y;

            MathUtils.Vec3 thisDirection = new MathUtils.Vec3(
                    resultVector[0] * speedAdjustment,
                    resultVector[1] * speedAdjustment,
                    resultVector[2] * speedAdjustment
            );

            if (isTrack) {
                for (int j = 0; j < 5; j++) {
                    particleSystem.addParticle(position, mStartColor, mEndColor,thisDirection, currentTime - (0.03f * j), particleSize, gravityFactor);
                }
            } else {
                particleSystem.addParticle(position, mStartColor, mEndColor, thisDirection, currentTime, particleSize, gravityFactor);
            }

        }
    }

}
