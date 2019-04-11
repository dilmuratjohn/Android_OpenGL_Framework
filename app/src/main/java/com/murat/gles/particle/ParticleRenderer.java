package com.murat.gles.particle;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.murat.gles.util.MathUtils;
import com.murat.gles.util.TextureHelper;

import java.util.Map;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import android.opengl.Matrix;

public class ParticleRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private final float[] modelMatrix = new float[16];
    private final float[] modelViewMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private ParticleShader particleShader;
    private ParticleSystem particleSystem;
    private ParticleShooter particleShooter;

    private int mMaxParticleCount;
    private int particleTexture;
    private int mBlendFuncSource;
    private int mBlendFuncDestination;
    private int mFileId;


    private MathUtils.Vec2 mGravityFactor;
    private MathUtils.Vec4 mStartColor;
    private MathUtils.Vec4 mEndColor;
    private MathUtils.Vec3 mPosition;
    private MathUtils.Vec3 mDirection;
    private MathUtils.Vec2 mSpeed;
    private MathUtils.Vec2 mAngle;
    private MathUtils.Vec2 mParticleSize;

    private final Random random = new Random();

    ParticleRenderer(Context context) {
        this.context = context;
    }

    private long mStartTime;

    private Map<String, Object> mConfig;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        readConfig();
        createParticle();
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        MathUtils.Mat4.perspectiveProjection(projectionMatrix, 45, (float) width / (float) height, 1f, 100f);
        Matrix.setIdentityM(viewMatrix, 0);
        updateViewMatrices();
        GLES20.glEnable(GLES20.GL_CULL_FACE);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        drawParticles();
    }

    private void readConfig() {
        mConfig = new ParticleConfig("").getConfigMap();
        mBlendFuncDestination = (int) mConfig.get("blendFuncDestination");
        mBlendFuncSource = (int) mConfig.get("blendFuncSource");
        mStartColor = new MathUtils.Vec4((float) mConfig.get("startColorRed"), (float) mConfig.get("startColorGreen"), (float) mConfig.get("startColorBlue"), (float) mConfig.get("startColorAlpha"));
        mEndColor = new MathUtils.Vec4((float) mConfig.get("finishColorRed"), (float) mConfig.get("finishColorGreen"), (float) mConfig.get("finishColorBlue"), (float) mConfig.get("finishColorAlpha"));
        mSpeed = new MathUtils.Vec2((float) mConfig.get("speed") / 10, (float) mConfig.get("speedVariance") / 10);
        mAngle = new MathUtils.Vec2((float) mConfig.get("angle"), (float) mConfig.get("angleVariance"));
        mFileId = (int) mConfig.get("textureFileName");
        mMaxParticleCount = (int) mConfig.get("maxParticles");
        mParticleSize = new MathUtils.Vec2((float) mConfig.get("startParticleSize"), (float) mConfig.get("startParticleSizeVariance"));
        mGravityFactor = new MathUtils.Vec2((float) mConfig.get("gravityx") / 500, (float) mConfig.get("gravityy") / 500);

        float startColorVarianceAlpha = (float) mConfig.get("startColorVarianceAlpha");
        float startColorVarianceBlue = (float) mConfig.get("startColorVarianceBlue");
        float startColorVarianceGreen = (float) mConfig.get("startColorVarianceGreen");
        float startColorVarianceRed = (float) mConfig.get("startColorVarianceRed");

        float endColorVarianceAlpha = (float) mConfig.get("finishColorVarianceAlpha");
        float endColorVarianceBlue = (float) mConfig.get("finishColorVarianceBlue");
        float endColorVarianceGreen = (float) mConfig.get("finishColorVarianceGreen");
        float endColorVarianceRed = (float) mConfig.get("finishColorVarianceRed");
    }

    private void createParticle() {
        particleTexture = TextureHelper.loadTexture(context, mFileId);
        particleShader = new ParticleShader(context);
        particleSystem = new ParticleSystem(mMaxParticleCount);
        mStartTime = System.currentTimeMillis();
        particleShooter = new ParticleShooter(
                new MathUtils.Vec3(0f, 0f, 0f),
                new MathUtils.Vec3(0f, 0.5f, 0f),
                mStartColor,
                mEndColor,
                mSpeed,
                mAngle
        );
    }

    private void drawParticles() {

        float lifeTime = (System.currentTimeMillis() - mStartTime) / 1000f;
        float duration = (int) mConfig.get("duration");
        if (lifeTime <= duration || duration <= 0) {
            particleShooter.addParticles(particleSystem, lifeTime, 1, mParticleSize.x, mGravityFactor, false);
        }

        Matrix.setIdentityM(modelMatrix, 0);

        updateModelViewMatrices();
        updateModelViewProjectionMatrix();

        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(mBlendFuncSource, mBlendFuncDestination);

        particleShader.useProgram();
        particleShader.setUniforms(modelViewProjectionMatrix, lifeTime, particleTexture);
        particleSystem.bindData(particleShader);
        particleSystem.draw();

        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDepthMask(true);
    }

    private void updateViewMatrices() {
        Matrix.translateM(viewMatrix, 0, 0f, 0f, -5f);
    }

    private void updateModelViewMatrices() {
        Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
    }

    private void updateModelViewProjectionMatrix() {
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);
    }

}
