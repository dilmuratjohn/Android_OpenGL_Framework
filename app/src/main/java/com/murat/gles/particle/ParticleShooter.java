package com.murat.gles.particle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.opengl.GLES20;
import android.util.Log;

import com.google.gson.Gson;
import com.murat.gles.common.GLRenderable;
import com.murat.gles.common.GLShader;
import com.murat.gles.common.GLTexture;
import com.murat.gles.common.GLTextureHelper;
import com.murat.gles.common.GLVertexArray;

import java.util.Random;

import static com.murat.gles.common.GLConstants.BYTES_PER_FLOAT;

public class ParticleShooter implements GLRenderable {

    private final float Designed_Width = 720.0f;
    private final float Designed_Height = 1208.0f;

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
    private final GLVertexArray mVertexArray;
    private final int maxParticleCount1i;

    private int currentParticleCount;
    private int nextParticle;

    private float[] mPosition4f = new float[4];

    /*
        x -> red
        y -> greed
        z -> blue
        w -> alpha
    */
    private float[] mStartColor4f = new float[4];

    private float[] mEndColor4f = new float[4];

    /*
        x -> speed
        y -> speed variance
        z -> angle
        w -> angle variance
     */
    private float[] mVelocity2f = new float[2];

    /*
        x -> gravity x
        y -> gravity y
    */
    private float[] mForce4f = new float[4];

    /*
        x -> size
        y -> variance
    */
    private float[] mParticleSize1f = new float[1];

    private float[] mParticleSizeVariance = new float[1];

    private float[] mRotation1f = new float[1];

    private ParticleBean mParticleBean;
    private ParticleShader mParticleShader;
    private GLTexture  mParticleTexture;
    private long mStartTime;
    private float mDuration1f;
    private float mLifeTime;
    private int mEmissionRate1i;

    public ParticleShooter(Activity activity, String json) {

        Point windowSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(windowSize);
        Log.e("Murat", "Width: " + windowSize.x + "Height: " + windowSize.y);
        mParticleBean = new Gson().fromJson(json, ParticleBean.class);
        mStartColor4f[0] = mParticleBean.startColorRed;
        mStartColor4f[1] = mParticleBean.startColorGreen;
        mStartColor4f[2] = mParticleBean.startColorBlue;
        mStartColor4f[3] = mParticleBean.startColorAlpha;
        mEndColor4f[0] = mParticleBean.finishColorRed;
        mEndColor4f[1] = mParticleBean.finishColorGreen;
        mEndColor4f[2] = mParticleBean.finishColorBlue;
        mEndColor4f[3] = mParticleBean.finishColorAlpha;
        mVelocity2f[0] = mParticleBean.speed;
        mVelocity2f[1] = mParticleBean.angle;
        mParticleSize1f[0] = mParticleBean.startParticleSize * windowSize.y / Designed_Height;
        mParticleSizeVariance[0] = mParticleBean.startParticleSizeVariance;
        mForce4f[0] = mParticleBean.gravityx;
        mForce4f[1] = mParticleBean.gravityy;
        mForce4f[2] = mParticleBean.tangentialAcceleration;
        mForce4f[3] = mParticleBean.radialAcceleration;
        mRotation1f[0] = nextRandomRotation1f();
        maxParticleCount1i = mParticleBean.maxParticles;
        mDuration1f = mParticleBean.duration;
        mEmissionRate1i = 3;

        particles = new float[mParticleBean.maxParticles * TOTAL_COMPONENT_COUNT];
        mVertexArray = new GLVertexArray(particles);
    }

    public GLRenderable init(Context context){
        mParticleTexture = new GLTexture(context, mParticleBean.textureFileName.split("\\.")[0]);
        mParticleShader = new ParticleShader(context);
        mStartTime = System.currentTimeMillis();
        return this;
    }

    public GLRenderable bind(){
        mParticleShader.bind();
        mParticleTexture.bind();
        bindData(mParticleShader);
        return this;
    }

    public GLRenderable unbind(){
        mParticleShader.unbind();
        mParticleTexture.unbind();
        return this;
    }

    public GLRenderable render(float[] mvp) {

        mLifeTime = (System.currentTimeMillis() - mStartTime) / 1000f;


        if (mLifeTime <= mDuration1f || mDuration1f <= 0) {
            for (int i = 0; i < mEmissionRate1i; i++) {
                update();
                load();
            }
        }

        mParticleShader.setUniformMatrix4fv(mParticleShader.getMatrixLocation(), mvp);
        mParticleShader.setUniform1f(mParticleShader.getTimeLocation(), mLifeTime);
        mParticleShader.setUniform1i(mParticleShader.getTextureLocation(), 0);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(mParticleBean.blendFuncSource, mParticleBean.blendFuncDestination);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount);
        GLES20.glDisable(GLES20.GL_BLEND);
        return this;
    }

    private void update() {
        updatePosition();
        updateColor();
        updateParticleSize();
        updateRotation();
        updateParticleForce();
        updateVelocity();
    }

    private void load() {

        final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;

        int currentOffset = particleOffset;
        nextParticle++;

        if (currentParticleCount < maxParticleCount1i) {
            currentParticleCount++;
        }

        if (nextParticle == maxParticleCount1i) {
            nextParticle = 0;
        }

        particles[currentOffset++] = mPosition4f[0];
        particles[currentOffset++] = mPosition4f[1];
        particles[currentOffset++] = mPosition4f[2];
        particles[currentOffset++] = mStartColor4f[0];
        particles[currentOffset++] = mStartColor4f[1];
        particles[currentOffset++] = mStartColor4f[2];
        particles[currentOffset++] = mStartColor4f[3];
        particles[currentOffset++] = mEndColor4f[0];
        particles[currentOffset++] = mEndColor4f[1];
        particles[currentOffset++] = mEndColor4f[2];
        particles[currentOffset++] = mEndColor4f[3];
        particles[currentOffset++] = mVelocity2f[0];
        particles[currentOffset++] = mVelocity2f[1];
        particles[currentOffset++] = mLifeTime;
        particles[currentOffset++] = mParticleSize1f[0];
        particles[currentOffset++] = mForce4f[0];
        particles[currentOffset++] = mForce4f[1];
        particles[currentOffset++] = mForce4f[2];
        particles[currentOffset++] = mForce4f[3];
        particles[currentOffset++] = mRotation1f[0];

        mVertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);
    }

    private void bindData(ParticleShader program) {
        int dataOffset = 0;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getPositionLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getStartColorLocation(), COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getEndColorLocation(), COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getSpeedLocation(), VECTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getParticleStartTimeLocation(), PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
        dataOffset += PARTICLE_START_TIME_COMPONENT_COUNT;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getParticleSizeLocation(), PARTICLE_SIZE_COMPONENT_COUNT, STRIDE);
        dataOffset += PARTICLE_SIZE_COMPONENT_COUNT;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getForceLocation(), GRAVITY_FACTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += GRAVITY_FACTOR_COMPONENT_COUNT;
        mVertexArray.setVertexAttribPointer(dataOffset, program.getRotationLocation(), ROTATION_COMPONENT_COUNT, STRIDE);
    }


    private void updateParticleSize() {
        mParticleSize1f[0] = nextRandomSize();
    }

    private void updateParticleForce() {
        mForce4f[2] = nextRandomForce1f();
        mForce4f[3] = nextRandomForce1f();
    }

    private void updatePosition() {
        mPosition4f = nextRandomPosition3f();
    }

    private void updateVelocity() {
        mVelocity2f[0] = nextRandomVelocity1f();
    }

    private void updateRotation() {
        mRotation1f[0] = nextRandomRotation1f();
    }

    private void updateColor() {
        float[] color = nextRandomColor4f();
        updateStartColor(color);
        updateEndColor(color);
    }

    private void updateStartColor(float[] color) {
        this.mStartColor4f = color;
    }

    private void updateEndColor(float[] color) {
        this.mEndColor4f = color;
    }

    private float[] nextRandomPosition3f() {
        return new float[]{2f * random.nextFloat() - 1f, 0.3f * random.nextFloat() + 2f, 1.0f};
    }

    private float[] nextRandomColor4f() {
        return new float[]{random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()};
    }

    private float nextRandomSize() {
        return (mParticleBean.startParticleSizeVariance * 2f * random.nextFloat() - mParticleBean.startParticleSizeVariance);
    }

    private float nextRandomRotation1f() {
        return (2f * random.nextFloat() - 1f);
    }

    private float nextRandomForce1f() {
        return (2f * random.nextFloat() - 1f) * mPosition4f[0] > 0 ? 1f : -1f;
    }

    private float nextRandomVelocity1f() {
        return mParticleBean.speed + (mParticleBean.speedVariance * (2.0f * random.nextFloat() - 1.0f));
    }

}
