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
    private static final int Angle_Component_Count = 1;
    private static final int Particle_Start_Time_Component_Count = 1;
    private static final int Start_Size_Component_Count = 1;
    private static final int End_Size_Component_Count = 1;
    private static final int Gravity_Component_Count = 2;
    private static final int Rotation_Component_Count = 2;
    private static final int Particle_Life_Time_Component_Count = 1;
    private static final int Total_Component_Count =
            Position_Component_Count
                    + Color_Component_Count
                    + Color_Component_Count
                    + Speed_Component_Count
                    + Angle_Component_Count
                    + Particle_Start_Time_Component_Count
                    + Start_Size_Component_Count
                    + End_Size_Component_Count
                    + Gravity_Component_Count
                    + Rotation_Component_Count
                    + Particle_Life_Time_Component_Count;
    private static final int Stride = Total_Component_Count * GLConstants.BYTES_PER_FLOAT;
    private final float[] mParticleData;
    private final GLVertexArray mVertexArray;
    private ParticleBean mParticleBean;
    private ParticleShader mParticleShader;
    private GLTexture mParticleTexture;
    private int mCurrentParticleCount;
    private int mNextParticle;
    private float[] mPosition4f = new float[4];
    private float[] mStartColor4f = new float[4];
    private float[] mEndColor4f = new float[4];
    private float mSpeed1f;
    private float mParticleLifeTime1f;
    private float mStartSize1f;
    private float mEndSize1f;
    private float[] mRotation2f = new float[2];
    private float[][] mColorSet;
    private boolean mColorSetEnable;
    private long mStartTime1f;
    private float mDeltaTime1f;
    private int mEmitCount1i;
    private float mAngle1f;

    private int mEmitCounter1i;

    public ParticleShooter(String json) {
        mParticleBean = new Gson().fromJson(json, ParticleBean.class);
        mParticleData = new float[mParticleBean.maxParticles * Total_Component_Count];
        mVertexArray = new GLVertexArray(mParticleData);
        initColorSet();
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
            for (int i = 0; i <= mEmitCount1i; i++) {
                add();
            }
        }

        mParticleShader.setUniformMatrix4fv(mParticleShader.getMatrixLocation(), mvp);
        mParticleShader.setUniform1f(mParticleShader.getTimeLocation(), mDeltaTime1f / 1000.0f);
        mParticleShader.setUniform1i(mParticleShader.getTextureLocation(), 0);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(mParticleBean.blendFuncSource, mParticleBean.blendFuncDestination);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mCurrentParticleCount);
        GLES20.glDisable(GLES20.GL_BLEND);

        return this;
    }


    private void add() {
        update();

        final int particleOffset = mNextParticle * Total_Component_Count;

        int currentOffset = particleOffset;
        mNextParticle++;

        if (mCurrentParticleCount < mParticleBean.maxParticles) {
            mCurrentParticleCount++;
        }

        if (mNextParticle == mParticleBean.maxParticles) {
            mNextParticle = 0;
        }

        mParticleData[currentOffset++] = mPosition4f[0];
        mParticleData[currentOffset++] = mPosition4f[1];
        mParticleData[currentOffset++] = mPosition4f[2];
        mParticleData[currentOffset++] = mPosition4f[3];
        mParticleData[currentOffset++] = mStartColor4f[0];
        mParticleData[currentOffset++] = mStartColor4f[1];
        mParticleData[currentOffset++] = mStartColor4f[2];
        mParticleData[currentOffset++] = mStartColor4f[3];
        mParticleData[currentOffset++] = mEndColor4f[0];
        mParticleData[currentOffset++] = mEndColor4f[1];
        mParticleData[currentOffset++] = mEndColor4f[2];
        mParticleData[currentOffset++] = mEndColor4f[3];
        mParticleData[currentOffset++] = mSpeed1f;
        mParticleData[currentOffset++] = mAngle1f;
        mParticleData[currentOffset++] = mDeltaTime1f / 1000.0f;
        mParticleData[currentOffset++] = mStartSize1f;
        mParticleData[currentOffset++] = mEndSize1f;
        mParticleData[currentOffset++] = mParticleBean.gravityx;
        mParticleData[currentOffset++] = mParticleBean.gravityy;
        mParticleData[currentOffset++] = mRotation2f[0];
        mParticleData[currentOffset++] = mRotation2f[1];
        mParticleData[currentOffset++] = mParticleLifeTime1f;

        mVertexArray.updateBuffer(mParticleData, particleOffset, Total_Component_Count);
    }


    private void update() {
        updatePosition();
        updateColor();
        updateParticleSize();
        updateRotation();
        updateSpeed();
        updateAngle();
        updateParticleLifeTime();
        updateEmitCount();
    }

    private void bindData() {
        int dataOffset = 0;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getPositionLocation(), Position_Component_Count, Stride);
        dataOffset += Position_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getStartColorLocation(), Color_Component_Count, Stride);
        dataOffset += Color_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getEndColorLocation(), Color_Component_Count, Stride);
        dataOffset += Color_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getSpeedLocation(), Speed_Component_Count, Stride);
        dataOffset += Speed_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getAngleLocation(), Angle_Component_Count, Stride);
        dataOffset += Angle_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getParticleStartTimeLocation(), Particle_Start_Time_Component_Count, Stride);
        dataOffset += Particle_Start_Time_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getStartSizeLocation(), Start_Size_Component_Count, Stride);
        dataOffset += Start_Size_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getEndSizeLocation(), End_Size_Component_Count, Stride);
        dataOffset += End_Size_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getForceLocation(), Gravity_Component_Count, Stride);
        dataOffset += Gravity_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getRotationLocation(), Rotation_Component_Count, Stride);
        dataOffset += Rotation_Component_Count;
        mVertexArray.setVertexAttribPointer(dataOffset, mParticleShader.getParticleLifeTimeLocation(), Particle_Life_Time_Component_Count, Stride);
    }


    private void updateParticleSize() {
        mStartSize1f = nextRandomStartSize();
        mEndSize1f = nextRandomEndSize();
    }

    private void updatePosition() {
        mPosition4f = nextRandomPosition4f();
    }

    private void updateSpeed() {
        mSpeed1f = nextRandomSpeed1f();
    }

    private void updateAngle() {
        mAngle1f = nextRandomAngle();
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

    private void updateParticleLifeTime() {
        mParticleLifeTime1f = nextParticleLifeTime1f();
    }

    private void updateEmitCount() {
        float rate = 1.0f / (mParticleBean.particleLifespan / mParticleBean.maxParticles);
        if (mCurrentParticleCount < mParticleBean.maxParticles) {
            mEmitCounter1i += 16;
            mEmitCount1i = Utils.min(mParticleBean.maxParticles - mCurrentParticleCount, (int) (mEmitCounter1i / rate));
        }
    }

    private float[] nextRandomPosition4f() {
        return new float[]{
                Utils.nextRandomInRange(1.0f, -1.0f),
                Utils.nextRandomInRange(0.5f, 1.0f),
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

    private float nextRandomStartSize() {
        return mParticleBean.startParticleSize + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.startParticleSizeVariance;
    }

    private float nextRandomEndSize() {
        return mParticleBean.finishParticleSize + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.finishParticleSizeVariance;
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

    private float nextParticleLifeTime1f() {
        return mParticleBean.particleLifespan + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.particleLifespanVariance;
    }

    private float nextRandomAngle() {
        return mParticleBean.angle + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.angleVariance;
    }

}
