package com.murat.android.opengl.particle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.google.gson.Gson;
import com.murat.android.opengl.GLRenderer;
import com.murat.android.opengl.Utils;
import com.murat.android.opengl.actions.Action;
import com.murat.android.opengl.actions.ActionInterval;
import com.murat.android.opengl.common.buffer.VertexArray;
import com.murat.android.opengl.common.buffer.VertexBufferLayout;
import com.murat.android.opengl.common.data.Constants;
import com.murat.android.opengl.common.texture.Texture;


public class ParticleRenderer implements GLRenderer.GLRenderable, Action {

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
    private static final int Degree_Per_Second_Component_Count = 1;
    private static final int Radius_Component_Count = 2;
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
                    + Particle_Life_Time_Component_Count
                    + Degree_Per_Second_Component_Count
                    + Radius_Component_Count;
    private boolean mColorSetEnable;
    private int mCurrentParticleCount1i;
    private int mNextParticle1i;
    private int mEmitCounter1i;
    private int mEmitCount1i;
    private long mStartTime1l;
    private float mSpeed1f;
    private float mParticleLifeTime1f;
    private float mStartSize1f;
    private float mEndSize1f;
    private float mRotate1f;
    private float mTimePassed1f;
    private float mAngle1f;
    private float[] mParticleData;
    private float[] mPosition4f = new float[4];
    private float[] mStartColor4f = new float[4];
    private float[] mEndColor4f = new float[4];
    private float[] mRotation2f = new float[2];
    private float[] mForce2f = new float[2];
    private float[] mRadius2f = new float[2];
    private float[][] mColorSet;

    private VertexArray mVertexArray;
    private VertexBufferLayout mVertexBufferLayout;
    private ParticleBean mParticleBean;
    private ParticleShader mParticleShader;
    private Texture mParticleTexture;
    private GLRenderer mRenderer;
    private Context mContext;
    private ActionInterval mActionInterval;

    public ParticleRenderer(Context context, int sourceId) {
        mContext = context;
        mActionInterval = new ActionInterval(this);
        mParticleBean = new Gson().fromJson(Utils.getJSONStringFromResource(context, sourceId), ParticleBean.class);
        mParticleData = new float[mParticleBean.maxParticles * Total_Component_Count];
        mVertexArray = new VertexArray(mParticleData);
        mVertexBufferLayout = new VertexBufferLayout();
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

    public GLRenderer.GLRenderable init(GLRenderer renderer) {
        mRenderer = renderer;
        mParticleTexture = new Texture(mContext, mParticleBean.textureFileName.split("\\.")[0]);
        mParticleShader = new ParticleShader(mContext);
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.setIdentityM(mModelViewMatrix, 0);
        Matrix.setIdentityM(mModelViewProjectionMatrix, 0);
        mVertexBufferLayout.push(mParticleShader.getPositionLocation(), Position_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getStartColorLocation(), Color_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getEndColorLocation(), Color_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getSpeedLocation(), Speed_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getAngleLocation(), Angle_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getStartTimeLocation(), Particle_Start_Time_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getStartSizeLocation(), Start_Size_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getEndSizeLocation(), End_Size_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getForceLocation(), Gravity_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getRotationLocation(), Rotation_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getLifeTimeLocation(), Particle_Life_Time_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getDegreePerSecondLocation(), Degree_Per_Second_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        mVertexBufferLayout.push(mParticleShader.getRadiusLocation(), Radius_Component_Count, GLES20.GL_FLOAT, Constants.Bytes_Per_Float, false);
        return this;
    }

    public GLRenderer.GLRenderable bind() {
        if (!mRender) return this;
        mParticleShader.bind();
        mParticleTexture.bind();
        mVertexArray.setVertexAttributePointer(mVertexBufferLayout);
        mParticleShader.setUniform1i(mParticleShader.getTextureLocation(), 0);
        mParticleShader.setUniform1i(mParticleShader.getModeLocation(), mParticleBean.emitterType);
        return this;
    }

    public GLRenderer.GLRenderable unbind() {
        if (!mRender) return this;
        mParticleShader.unbind();
        mParticleTexture.unbind();
        return this;
    }

    private final float[] mModelMatrix = new float[16];
    private final float[] mModelViewMatrix = new float[16];
    private final float[] mModelViewProjectionMatrix = new float[16];

    public GLRenderer.GLRenderable render() {
        if (!mRender) return this;
        Matrix.multiplyMM(mModelViewMatrix, 0, mRenderer.getViewMatrix(), 0, mModelMatrix, 0);
        Matrix.multiplyMM(mModelViewProjectionMatrix, 0, mRenderer.getProjectionMatrix(), 0, mModelViewMatrix, 0);
        mTimePassed1f = (System.currentTimeMillis() - mStartTime1l) / 1000000f;

        if (mTimePassed1f <= mParticleBean.duration / 1000 || mParticleBean.duration <= 0) {
            for (int i = 0; i <= mEmitCount1i; i++) {
                update();
                add();
            }
        }

        mParticleShader.setUniformMatrix4fv(mParticleShader.getMatrixLocation(), mModelViewProjectionMatrix);
        mParticleShader.setUniform1f(mParticleShader.getTimeLocation(), mTimePassed1f);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mCurrentParticleCount1i);
        GLES20.glDisable(GLES20.GL_BLEND);

        return this;
    }

    @Override
    public GLRenderer.GLRenderable delete() {
        mParticleShader.delete();
        mParticleTexture.delete();
        return this;
    }

    private void update() {
        updatePosition();
        updateColor();
        updateParticleSize();
        updateRotation();
        updateRotate();
        updateRadius();
        updateSpeed();
        updateAngle();
        updateForce();
        updateParticleLifeTime();
        updateEmitCount();
    }

    private void add() {
        final int particleOffset = mNextParticle1i * Total_Component_Count;
        int currentOffset = particleOffset;
        mNextParticle1i++;
        if (mCurrentParticleCount1i < mParticleBean.maxParticles) {
            mCurrentParticleCount1i++;
        }
        if (mNextParticle1i == mParticleBean.maxParticles) {
            mNextParticle1i = 0;
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
        mParticleData[currentOffset++] = mTimePassed1f;
        mParticleData[currentOffset++] = mStartSize1f;
        mParticleData[currentOffset++] = mEndSize1f;
        mParticleData[currentOffset++] = mForce2f[0];
        mParticleData[currentOffset++] = mForce2f[1];
        mParticleData[currentOffset++] = mRotation2f[0];
        mParticleData[currentOffset++] = mRotation2f[1];
        mParticleData[currentOffset++] = mParticleLifeTime1f;
        mParticleData[currentOffset++] = mRotate1f;
        mParticleData[currentOffset++] = mRadius2f[0];
        mParticleData[currentOffset++] = mRadius2f[1];

        mVertexArray.updateVertex(mParticleData, particleOffset, Total_Component_Count);
    }

    private void updateParticleSize() {
        mStartSize1f = nextRandomStartSize1f();
        mEndSize1f = nextRandomEndSize1f();
    }

    private void updatePosition() {
        mPosition4f = nextRandomPosition4f();
    }

    private void updateRotate() {
        mRotate1f = nextRandomRotate1f();
    }

    private void updateRadius() {
        mRadius2f = nextRandomRadius2f();
    }

    private void updateSpeed() {
        mSpeed1f = nextRandomSpeed1f();
    }

    private void updateAngle() {
        mAngle1f = nextRandomAngle1f();
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

    private void updateStartColor(float[] color4f) {
        mStartColor4f = color4f;
    }

    private void updateEndColor(float[] color4f) {
        mEndColor4f = color4f;
    }

    private void updateStartColor() {
        mStartColor4f = nextRandomStartColor4f();
    }

    private void updateEndColor() {
        mEndColor4f = nextRandomEndColor4f();
    }

    private void updateForce() {
        mForce2f = nextRandomForce2f();
    }

    private void updateParticleLifeTime() {
        mParticleLifeTime1f = nextParticleLifeTime1f();
    }

    private void updateEmitCount() {
        float rate = 1.0f / (mParticleBean.particleLifespan / mParticleBean.maxParticles);
        if (mCurrentParticleCount1i < mParticleBean.maxParticles) {
            mEmitCounter1i += 16;
            mEmitCount1i = (int) (mEmitCounter1i / rate);
        }
    }

    private float[] nextRandomPosition4f() {
        return new float[]{
                0.5f - (mParticleBean.sourcePositionx + Utils.nextRandomInRange(1.0f, -1.0f) * mParticleBean.sourcePositionVariancex) / (Constants.Designed_Width + 0.f),
                0.5f - (mParticleBean.sourcePositiony + Utils.nextRandomInRange(1.0f, -1.0f) * mParticleBean.sourcePositionVariancey) / (Constants.Designed_Height + 0.f),
                0.0f,
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

    private float nextRandomStartSize1f() {
        return mParticleBean.startParticleSize + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.startParticleSizeVariance;
    }

    private float nextRandomEndSize1f() {
        return mParticleBean.finishParticleSize + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.finishParticleSizeVariance;
    }

    private float[] nextRandomRotation2f() {
        return new float[]{
                mParticleBean.rotationStart + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.rotationStartVariance,
                mParticleBean.rotationEnd + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.rotationEndVariance,
        };
    }

    private float nextRandomRotate1f() {
        return mParticleBean.rotatePerSecond + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.rotatePerSecondVariance;
    }

    private float[] nextRandomRadius2f() {
        return new float[]{
                (mParticleBean.maxRadius + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.maxRadiusVariance) / Constants.Designed_Width * 2,
                (mParticleBean.minRadius + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.minRadiusVariance) / Constants.Designed_Width * 2
        };
    }

    private float nextRandomSpeed1f() {
        return mParticleBean.speed + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.speedVariance;
    }

    private float nextParticleLifeTime1f() {
        return mParticleBean.particleLifespan + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.particleLifespanVariance;
    }

    private float nextRandomAngle1f() {
        return mParticleBean.angle + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.angleVariance;
    }

    private float[] nextRandomForce2f() {
        return new float[]{
                mParticleBean.radialAcceleration + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.radialAccelVariance
                        + mParticleBean.tangentialAcceleration + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.tangentialAccelVariance
                        + mParticleBean.gravityx,
                mParticleBean.radialAcceleration + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.radialAccelVariance
                        - (mParticleBean.tangentialAcceleration + Utils.nextRandomInRange(-1.0f, 1.0f) * mParticleBean.tangentialAccelVariance)
                        + mParticleBean.gravityy
        };
    }

    @Override
    public Action translate(float x, float y, float z) {
        float[] translation = new float[16];
        Matrix.setIdentityM(translation, 0);
        Matrix.translateM(translation, 0, translation, 0, x, y, z);
        Matrix.multiplyMM(mModelMatrix, 0, translation, 0, mModelMatrix, 0);
        return this;
    }

    @Override
    public Action rotate(float a, float x, float y, float z) {
        float[] rotation = new float[16];
        Matrix.setIdentityM(rotation, 0);
        Matrix.rotateM(rotation, 0, rotation, 0, a, x, y, z);
        Matrix.multiplyMM(mModelMatrix, 0, rotation, 0, mModelMatrix, 0);
        return this;
    }

    @Override
    public Action scale(float x, float y, float z) {
        float[] scale = new float[16];
        Matrix.setIdentityM(scale, 0);
        Matrix.scaleM(scale, 0, scale, 0, x + 1f, y + 1f, z + 1f);
        Matrix.multiplyMM(mModelMatrix, 0, scale, 0, mModelMatrix, 0);
        return this;
    }

    @Override
    public Action fade(float a) {
        return this;
    }

    @Override
    public Action tint(float r, float g, float b) {
        return this;
    }

    public ActionInterval getActionInterval() {
        return mActionInterval;
    }

    public void show(float x, float y, float scale) {
        stop();
        float sizeX = mRenderer.getSurfaceSize().x;
        float sizeY = mRenderer.getSurfaceSize().y;
        x = (x - sizeX / 2.0f) / (sizeX / 2.0f);
        y = -(y - sizeY / 2.0f) / (sizeY / 2.0f) / sizeX * sizeY;
        scale(scale, scale, 0);
        translate(x, y, 0);
        start();
    }

    private void stop() {
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.setIdentityM(mModelViewMatrix, 0);
        Matrix.setIdentityM(mModelViewProjectionMatrix, 0);
        mRender = false;
    }

    private boolean mRender;

    private void start() {
        mRender = true;
        mParticleData = new float[mParticleBean.maxParticles * Total_Component_Count];
        mVertexArray = new VertexArray(mParticleData);
        mStartTime1l = System.currentTimeMillis();
    }

}
