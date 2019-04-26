package com.murat.gles.particle;

import android.content.Context;
import android.opengl.GLES20;

import com.google.gson.Gson;
import com.murat.gles.Utils;
import com.murat.gles.common.GLConstants;
import com.murat.gles.common.GLRenderable;
import com.murat.gles.common.GLTexture;
import com.murat.gles.common.GLVertexArray;


public class ParticleShooter implements GLRenderable {

    private static final int Position_Component_Count = 4;
    private static final int Color_Component_Count = 4;
    private static final int Speed_Component_Count = 1;
    private static final int Particle_Start_Time_Component_Count = 1;
    private static final int Particle_Size_Component_Count = 1;
    private static final int Gravity_Component_Count = 2;
    private static final int Rotation_Component_Count = 2;
    private static final int Particle_Life_Time_Component_Count = 1;
    private static final int Total_Component_Count =
            Position_Component_Count
                    + Color_Component_Count
                    + Color_Component_Count
                    + Speed_Component_Count
                    + Particle_Start_Time_Component_Count
                    + Particle_Size_Component_Count
                    + Gravity_Component_Count
                    + Rotation_Component_Count
                    + Particle_Life_Time_Component_Count;
    private static final int Stride = Total_Component_Count * GLConstants.BYTES_PER_FLOAT;
    private final float[] particles;
    private final GLVertexArray vertexArray;
    private ParticleBean mParticleBean;
    private ParticleShader mParticleShader;
    private GLTexture mParticleTexture;
    private int currentParticleCount;
    private int nextParticle;
    private float[] mPosition4f = new float[4];
    private float[] mStartColor4f = new float[4];
    private float[] mEndColor4f = new float[4];
    private float mSpeed1f;
    private float mParticleLifeTime1f;
    private float mParticleSize1f;
    private float[] mRotation2f = new float[2];
    private float[][] mColorSet;
    private boolean mColorSetEnable;
    private long mStartTime1f;
    private float mDeltaTime1f;
    private int mEmissionRate1i;

    public ParticleShooter(String json) {
        mParticleBean = new Gson().fromJson(json, ParticleBean.class);
        mEmissionRate1i = 3;
        initColorSet();
        particles = new float[mParticleBean.maxParticles * Total_Component_Count];
        vertexArray = new GLVertexArray(particles);
    }

    private void initColorSet() {
        if (mParticleBean.colorSet != null && mParticleBean.colorSet.size() > 0) {
            try {
                float[][] colors = new float[mParticleBean.colorSet.size()][4];
                for (int i = 0; i < mParticleBean.colorSet.size(); i++) {
                    float[] rgba = new float[4];
                    rgba[0] = mParticleBean.colorSet.get(i).get("r") / 255.0f;
                    rgba[1] = mParticleBean.colorSet.get(i).get("g") / 255.0f;
                    rgba[2] = mParticleBean.colorSet.get(i).get("b") / 255.0f;
                    rgba[3] = mParticleBean.colorSet.get(i).get("a") / 255.0f;
                    colors[i] = rgba;
                }
                setColorSetEnable(true);
                mColorSet = colors;
            } catch (Exception e) {
                setColorSetEnable(false);
            }

        }
    }

    private void setColorSetEnable(boolean enable) {
        mColorSetEnable = enable;
    }

    private boolean colorSetEnable() {
        return mColorSetEnable;
    }

    public GLRenderable init(Context context) {
        mParticleTexture = new GLTexture(context, mParticleBean.textureFileName.split("\\.")[0]);
        mParticleShader = new ParticleShader(context);
        mStartTime1f = System.currentTimeMillis();
        return this;
    }

    public GLRenderable bind() {
        mParticleShader.bind();
        mParticleTexture.bind();
        bindData();
        return this;
    }

    public GLRenderable unbind() {
        mParticleShader.unbind();
        mParticleTexture.unbind();
        return this;
    }

    public GLRenderable render(float[] mvp) {
        mDeltaTime1f = (System.currentTimeMillis() - mStartTime1f) / 1000f;

        if (mDeltaTime1f <= mParticleBean.duration || mParticleBean.duration <= 0) {
            for (int i = 0; i < mEmissionRate1i; i++) {
                update();
                load();
            }
        }

        mParticleShader.setUniformMatrix4fv(mParticleShader.getMatrixLocation(), mvp);
        mParticleShader.setUniform1f(mParticleShader.getTimeLocation(), mDeltaTime1f / 1000.0f);
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
        updateVelocity();
        updateParticleLifeTime();
    }

    private void load() {

        final int particleOffset = nextParticle * Total_Component_Count;

        int currentOffset = particleOffset;
        nextParticle++;

        if (currentParticleCount < mParticleBean.maxParticles) {
            currentParticleCount++;
        }

        if (nextParticle == mParticleBean.maxParticles) {
            nextParticle = 0;
        }

        particles[currentOffset++] = mPosition4f[0];
        particles[currentOffset++] = mPosition4f[1];
        particles[currentOffset++] = mPosition4f[2];
        particles[currentOffset++] = mPosition4f[3];
        particles[currentOffset++] = mStartColor4f[0];
        particles[currentOffset++] = mStartColor4f[1];
        particles[currentOffset++] = mStartColor4f[2];
        particles[currentOffset++] = mStartColor4f[3];
        particles[currentOffset++] = mEndColor4f[0];
        particles[currentOffset++] = mEndColor4f[1];
        particles[currentOffset++] = mEndColor4f[2];
        particles[currentOffset++] = mEndColor4f[3];
        particles[currentOffset++] = mSpeed1f;
        particles[currentOffset++] = mDeltaTime1f / 1000.0f;
        particles[currentOffset++] = mParticleSize1f;
        particles[currentOffset++] = mParticleBean.gravityx;
        particles[currentOffset++] = mParticleBean.gravityy;
        particles[currentOffset++] = mRotation2f[0];
        particles[currentOffset++] = mRotation2f[1];
        particles[currentOffset++] = mParticleLifeTime1f;

        vertexArray.updateBuffer(particles, particleOffset, Total_Component_Count);
    }

    private void bindData() {
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getPositionLocation(), Position_Component_Count, Stride);
        dataOffset += Position_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getStartColorLocation(), Color_Component_Count, Stride);
        dataOffset += Color_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getEndColorLocation(), Color_Component_Count, Stride);
        dataOffset += Color_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getSpeedLocation(), Speed_Component_Count, Stride);
        dataOffset += Speed_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getParticleStartTimeLocation(), Particle_Start_Time_Component_Count, Stride);
        dataOffset += Particle_Start_Time_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getParticleSizeLocation(), Particle_Size_Component_Count, Stride);
        dataOffset += Particle_Size_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getForceLocation(), Gravity_Component_Count, Stride);
        dataOffset += Gravity_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getRotationLocation(), Rotation_Component_Count, Stride);
        dataOffset += Rotation_Component_Count;
        vertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getParticleLifeTimeLocation(), Particle_Life_Time_Component_Count, Stride);
    }


    private void updateParticleSize() {
        mParticleSize1f = nextRandomSize();
    }

    private void updatePosition() {
        mPosition4f = nextRandomPosition4f();
    }

    private void updateVelocity() {
        mSpeed1f = nextRandomSpeed1f();
    }

    private void updateRotation() {
        mRotation2f = nextRandomRotation2f();
    }

    private void updateColor() {
        if (colorSetEnable()) {
            float[] color = nextRandomColorFormSet4f();
            updateStartColor(color);
            updateEndColor(color);
        } else {
            updateStartColor();
            updateEndColor();
        }
    }

    private void updateStartColor(float[] color) {
        mStartColor4f = color;
    }

    private void updateEndColor(float[] color) {
        mEndColor4f = color;
    }

    private void updateStartColor() {
        mStartColor4f = nextRandomStartColor4f();
    }

    private void updateEndColor() {
        mEndColor4f = nextRandomEndColor4f();
    }

    private void updateParticleLifeTime(){
        mParticleLifeTime1f = nextParticleLifeTime1f();
    }


    private float[] nextRandomPosition4f() {
        return new float[]{
                Utils.nextRandomInRange(1.0f, -1.0f),
                Utils.nextRandomInRange(0.7f, 1.0f),
                1.0f,
                1.0f
        };
    }

    private float[] nextRandomColorFormSet4f() {
        if (mColorSet != null && mColorSet.length > 0) {
            return mColorSet[(int) (Utils.nextFloat() * (mColorSet.length - 1))];
        }
        return nextRandomStartColor4f();
    }

    private float[] nextRandomStartColor4f() {
        return new float[]{
                mParticleBean.startColorRed + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.startColorVarianceRed,
                mParticleBean.startColorGreen + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.startColorVarianceGreen,
                mParticleBean.startColorBlue + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.startColorVarianceBlue,
                mParticleBean.startColorAlpha + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.startColorVarianceAlpha
        };
    }

    private float[] nextRandomEndColor4f() {
        return new float[]{
                mParticleBean.finishColorRed + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.finishColorVarianceRed,
                mParticleBean.finishColorGreen + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.finishColorVarianceGreen,
                mParticleBean.finishColorBlue + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.finishColorVarianceBlue,
                mParticleBean.finishColorAlpha + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.finishColorVarianceAlpha
        };
    }

    private float nextRandomSize() {
        return mParticleBean.startParticleSize + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.startParticleSizeVariance;
    }

    private float[] nextRandomRotation2f() {
        return new float[]{
                mParticleBean.rotationStart + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.rotationStartVariance,
                mParticleBean.rotationEnd + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.rotationEndVariance,
        };
    }

    private float nextRandomSpeed1f() {
        return mParticleBean.speed + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.speedVariance;
    }

    private float nextParticleLifeTime1f(){
        return mParticleBean.particleLifespan + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.particleLifespanVariance;
    }


}
