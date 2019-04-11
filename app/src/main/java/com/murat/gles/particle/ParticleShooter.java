package com.murat.gles.particle;

import com.murat.gles.util.MathUtils;

import java.util.Random;

import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setRotateEulerM;
import static com.murat.gles.particle.ParticleConfig.Bean;


class ParticleShooter {

    private final Random random = new Random();

    private MathUtils.Vec3 mPosition;

    /*
        x -> red
        y -> greed
        z -> blue
        w -> alpha
    */
    private MathUtils.Vec4 mStartColor;

    private MathUtils.Vec4 mEndColor;

    /*
        x -> speed
        y -> speed variance
        z -> angle
        w -> angle variance
     */
    private MathUtils.Vec4 mVelocity;

    /*
        x -> gravity x
        y -> gravity y
    */
    private MathUtils.Vec2 mGravityFactor;

    /*
        x -> size
        y -> variance
    */
    private MathUtils.Vec2 mParticleSize;

    private float[] rotationMatrix = new float[16];
    private float[] directionVector = new float[4];
    private float[] resultVector = new float[4];

    ParticleShooter() {
        init();
    }

    private void init() {
        mStartColor = new MathUtils.Vec4(Bean.startColorRed, Bean.startColorGreen, Bean.startColorBlue, Bean.startColorAlpha);
        mEndColor = new MathUtils.Vec4(Bean.finishColorRed, Bean.finishColorGreen, Bean.finishColorBlue, Bean.finishColorAlpha);
        mVelocity = new MathUtils.Vec4(Bean.speed / 10, Bean.speedVariance / 10, Bean.angle, Bean.angleVariance);
        mParticleSize = new MathUtils.Vec2(Bean.startParticleSize, Bean.startParticleSizeVariance);
        mGravityFactor = new MathUtils.Vec2(Bean.gravityx / 500, Bean.gravityy / 500);
    }

    void updateParticleSize() {
        mParticleSize.x = nextRandomSize();
    }

    void updatePosition() {
        mPosition = nextRandomPosition(RandomPosition.MidHorizontal);
    }

    void updateColor() {
        MathUtils.Vec4 color = nextRandomColor();
        updateStartColor(color);
        updateEndColor(color);
    }

    void updateStartColor(MathUtils.Vec4 color) {
        this.mStartColor = color;
    }

    void updateEndColor(MathUtils.Vec4 color) {
        this.mEndColor = color;
    }

    void addParticles(ParticleSystem particleSystem, float currentTime) {

        // create angle
        setRotateEulerM(
                rotationMatrix,
                0,
                (random.nextFloat() - 0.5f) * mVelocity.w + mVelocity.z,
                (random.nextFloat() - 0.5f) * mVelocity.w + mVelocity.z,
                (random.nextFloat() - 0.5f) * mVelocity.w + mVelocity.z
        );

        // use angle to create particle heading different directions
        multiplyMV(resultVector, 0, rotationMatrix, 0, directionVector, 0);

        // use random to differentiate speed
        float speedAdjustment = mVelocity.x + random.nextFloat() * mVelocity.y;

        MathUtils.Vec3 thisDirection = new MathUtils.Vec3(
                resultVector[0] * speedAdjustment,
                resultVector[1] * speedAdjustment,
                resultVector[2] * speedAdjustment
        );

        particleSystem.addParticle(mPosition, mStartColor, mEndColor, thisDirection, currentTime, mParticleSize.x, mGravityFactor);

    }

    private enum RandomPosition {
        Top, Bottom, Lift, Right, MidHorizontal, MidVertical, Default
    }

    private MathUtils.Vec3 nextRandomPosition(RandomPosition position) {
        switch (position) {
            case Top:
                return new MathUtils.Vec3(4.0f * random.nextFloat() - 2.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case Lift:
                return new MathUtils.Vec3(4.0f * random.nextFloat() - 2.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case Right:
                return new MathUtils.Vec3(4.0f * random.nextFloat() - 2.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case Bottom:
                return new MathUtils.Vec3(4.0f * random.nextFloat() - 2.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case MidVertical:
                return new MathUtils.Vec3(4.0f * random.nextFloat() - 2.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case MidHorizontal:
                return new MathUtils.Vec3(2.0f * random.nextFloat() - 1.0f, 0.0f, 1.0f);
            case Default:
                return new MathUtils.Vec3(0.0f, 0.0f, 0.0f);
        }
        return new MathUtils.Vec3(0.0f, 0.0f, 0.0f);
    }

    private MathUtils.Vec4 nextRandomColor() {
        return new MathUtils.Vec4(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    private float nextRandomSize() {
        return (1.5f * random.nextFloat() - 0.5f) * Bean.startParticleSize;
    }

}
