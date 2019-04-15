package com.murat.gles.particle;

import android.content.Context;
import android.opengl.GLES20;

import com.google.gson.Gson;
import com.murat.gles.common.GLRenderable;
import com.murat.gles.common.GLMathUtils;
import com.murat.gles.common.GLTextureHelper;
import com.murat.gles.common.GLVertexArray;

import java.util.Random;

import static com.murat.gles.common.GLConstants.BYTES_PER_FLOAT;

public class ParticleShooter implements GLRenderable {

    private final Random random = new Random();
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
    private final GLVertexArray vertexArray;
    private final int maxParticleCount;

    private int currentParticleCount;
    private int nextParticle;

    private GLMathUtils.Vec3 mPosition;

    /*
        x -> red
        y -> greed
        z -> blue
        w -> alpha
    */
    private GLMathUtils.Vec4 mStartColor;

    private GLMathUtils.Vec4 mEndColor;

    /*
        x -> speed
        y -> speed variance
        z -> angle
        w -> angle variance
     */
    private GLMathUtils.Vec2 mVelocity;

    /*
        x -> gravity x
        y -> gravity y
    */
    private GLMathUtils.Vec4 mForce;

    /*
        x -> size
        y -> variance
    */
    private GLMathUtils.Vec2 mParticleSize;

    private GLMathUtils.Vec2 mRotation;

    private ParticleBean mParticleBean;
    private ParticleShader mParticleShader;
    private int mParticleTexture;
    private long mStartTime;
    private float mDuration;
    private float mLifeTime;
    private int mEmissionRate;

    public ParticleShooter(String json) {
        mParticleBean = new Gson().fromJson(json, ParticleBean.class);
        mStartColor = new GLMathUtils.Vec4(mParticleBean.startColorRed, mParticleBean.startColorGreen, mParticleBean.startColorBlue, mParticleBean.startColorAlpha);
        mEndColor = new GLMathUtils.Vec4(mParticleBean.finishColorRed, mParticleBean.finishColorGreen, mParticleBean.finishColorBlue, mParticleBean.finishColorAlpha);
        mVelocity = new GLMathUtils.Vec2(mParticleBean.speed, mParticleBean.speedVariance);
        mParticleSize = new GLMathUtils.Vec2(mParticleBean.startParticleSize, mParticleBean.startParticleSizeVariance);
        mForce = new GLMathUtils.Vec4(mParticleBean.gravityx, mParticleBean.gravityy, mParticleBean.tangentialAcceleration, mParticleBean.radialAcceleration);
        mRotation = new GLMathUtils.Vec2(nextRandomRotation(), 0f);
        mDuration = mParticleBean.duration;
        mEmissionRate = 3;
        particles = new float[mParticleBean.maxParticles * TOTAL_COMPONENT_COUNT];
        vertexArray = new GLVertexArray(particles);
        this.maxParticleCount = mParticleBean.maxParticles;
    }

    public void bind(Context context) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mParticleTexture = GLTextureHelper.loadTexture(context, mParticleBean.textureFileName);
        mParticleShader = new ParticleShader(context);
        mParticleShader.useProgram();
        bindData(mParticleShader);
        mStartTime = System.currentTimeMillis();
    }

    public void render(float[] mvp) {
        mLifeTime = (System.currentTimeMillis() - mStartTime) / 1000f;


        if (mLifeTime <= mDuration || mDuration <= 0) {
            for (int i = 0; i < mEmissionRate; i++) {
                update();
                load();
            }
        }

        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(mParticleBean.blendFuncSource, mParticleBean.blendFuncDestination);

        mParticleShader.setUniforms(mvp, mLifeTime, mParticleTexture);
        draw();

        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDepthMask(true);
    }

    private void update() {
        updatePosition();
        updateColor();
        updateParticleSize();
        updateRotation();
        updateParticleForce();
        updateVelocity();
    }

    private void draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount);
    }

    private void load() {

        final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;

        int currentOffset = particleOffset;
        nextParticle++;

        if (currentParticleCount < maxParticleCount) {
            currentParticleCount++;
        }

        if (nextParticle == maxParticleCount) {
            nextParticle = 0;
        }

        particles[currentOffset++] = mPosition.x;
        particles[currentOffset++] = mPosition.y;
        particles[currentOffset++] = mPosition.z;
        particles[currentOffset++] = mStartColor.x;
        particles[currentOffset++] = mStartColor.y;
        particles[currentOffset++] = mStartColor.z;
        particles[currentOffset++] = mStartColor.w;
        particles[currentOffset++] = mEndColor.x;
        particles[currentOffset++] = mEndColor.y;
        particles[currentOffset++] = mEndColor.z;
        particles[currentOffset++] = mEndColor.w;
        particles[currentOffset++] = mVelocity.x;
        particles[currentOffset++] = mVelocity.y;
        particles[currentOffset++] = mLifeTime;
        particles[currentOffset++] = mParticleSize.x;
        particles[currentOffset++] = mForce.x;
        particles[currentOffset++] = mForce.y;
        particles[currentOffset++] = mForce.z;
        particles[currentOffset++] = mForce.w;
        particles[currentOffset++] = mRotation.x;

        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);
    }

    private void bindData(ParticleShader program) {
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


    private void updateParticleSize() {
        mParticleSize.x = nextRandomSize();
    }

    private void updateParticleForce() {
        mForce.z = nextRandomForce();
        mForce.w = nextRandomForce();
    }

    private void updatePosition() {
        mPosition = nextRandomPosition();
    }

    private void updateVelocity() {
        mVelocity.x = nextRandomVelocity();
    }

    private void updateRotation() {
        mRotation.x = nextRandomRotation();
    }

    private void updateColor() {
        GLMathUtils.Vec4 color = nextRandomColor();
        updateStartColor(color);
        updateEndColor(color);
    }

    private void updateStartColor(GLMathUtils.Vec4 color) {
        this.mStartColor = color;
    }

    private void updateEndColor(GLMathUtils.Vec4 color) {
        this.mEndColor = color;
    }

    private GLMathUtils.Vec3 nextRandomPosition() {
        return new GLMathUtils.Vec3(2f * random.nextFloat() - 1f, 0.3f * random.nextFloat() + 2f, 1.0f);
    }

    private GLMathUtils.Vec4 nextRandomColor() {
        return new GLMathUtils.Vec4(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    private float nextRandomSize() {
        return random.nextFloat() * mParticleBean.startParticleSize * 5f;
    }

    private float nextRandomRotation() {
        return (2f * random.nextFloat() - 1f);
    }

    private float nextRandomForce() {
        return (2f * random.nextFloat() - 1f) * mPosition.x > 0 ? 1f : -1f;
    }

    private float nextRandomVelocity() {
        return mParticleBean.speed + (mParticleBean.speedVariance * (2.0f * random.nextFloat() - 1.0f));
    }

}
