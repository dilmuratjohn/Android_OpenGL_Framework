package com.murat.gles.particle;

import com.murat.gles.util.MathUtils;

import java.util.Random;

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
    private MathUtils.Vec2 mVelocity;

    /*
        x -> gravity x
        y -> gravity y
    */
    private MathUtils.Vec4 mForce;

    /*
        x -> size
        y -> variance
    */
    private MathUtils.Vec2 mParticleSize;

    private MathUtils.Vec2 mRotation;

    ParticleShooter() {
        init();
    }

    private void init() {
        mStartColor = new MathUtils.Vec4(Bean.startColorRed, Bean.startColorGreen, Bean.startColorBlue, Bean.startColorAlpha);
        mEndColor = new MathUtils.Vec4(Bean.finishColorRed, Bean.finishColorGreen, Bean.finishColorBlue, Bean.finishColorAlpha);
        mVelocity = new MathUtils.Vec2(Bean.speed, Bean.speedVariance);
        mParticleSize = new MathUtils.Vec2(Bean.startParticleSize, Bean.startParticleSizeVariance);
        mForce = new MathUtils.Vec4(Bean.gravityx, Bean.gravityy, Bean.tangentialAcceleration, Bean.radialAcceleration);
        mRotation = new MathUtils.Vec2(nextRandomRotation(), 0f);
    }

    void update() {
        updatePosition();
        updateColor();
        updateParticleSize();
        updateRotation();
        updateParticleForce();
        updateVelocity();
    }

    void addParticles(ParticleSystem particleSystem, float lifeTime) {
        particleSystem.addParticle(mPosition, mStartColor, mEndColor, mVelocity, lifeTime, mParticleSize.x, mForce, mRotation);
    }

    private void updateParticleSize() {
        mParticleSize.x = nextRandomSize();
    }

    private void updateParticleForce() {
        mForce.z = nextRandomForce();
        mForce.w = nextRandomForce();
    }

    private void updatePosition() {
        mPosition = nextRandomPosition(RandomPosition.Top);
    }

    private void updateVelocity() {
        mVelocity.x = nextRandomVelocity();
    }

    private void updateRotation() {
        mRotation.x = nextRandomRotation();
    }

    private void updateColor() {
        MathUtils.Vec4 color = nextRandomColor();
        updateStartColor(color);
        updateEndColor(color);
    }

    private void updateStartColor(MathUtils.Vec4 color) {
        this.mStartColor = color;
    }

    private void updateEndColor(MathUtils.Vec4 color) {
        this.mEndColor = color;
    }


    private enum RandomPosition {
        Top, Bottom, Lift, Right, MidHorizontal, MidVertical, Default
    }

    private MathUtils.Vec3 nextRandomPosition(RandomPosition position) {
        switch (position) {
            case Top:
                return new MathUtils.Vec3(2f * random.nextFloat() - 1f, 2.0f, 1.0f);
            case Lift:
                return new MathUtils.Vec3(-1.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case Right:
                return new MathUtils.Vec3(1.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case Bottom:
                return new MathUtils.Vec3(4.0f * random.nextFloat() - 2.0f, -1.5f, 1.0f);
            case MidVertical:
                return new MathUtils.Vec3(0.0f, 2.0f * random.nextFloat() - 1.0f, 1.0f);
            case MidHorizontal:
                return new MathUtils.Vec3(4.0f * random.nextFloat() - 2.0f, 0.0f, 1.0f);
            case Default:
                return new MathUtils.Vec3(0.0f, 0.0f, 0.0f);
        }
        return new MathUtils.Vec3(0.0f, 0.0f, 0.0f);
    }

    private MathUtils.Vec4 nextRandomColor() {
        return new MathUtils.Vec4(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    private float nextRandomSize() {
        return random.nextFloat() * Bean.startParticleSize * 5f;
    }

    private float nextRandomRotation() {
        return 2f * random.nextFloat() - 1f;
    }

    private float nextRandomForce() {
        return (2f * random.nextFloat() - 1f) * mPosition.x > 0 ? 1f : -1f;
    }

    private float nextRandomVelocity() {
        return Bean.speed + (Bean.speedVariance * (2.0f * random.nextFloat() - 1.0f));
    }

}
