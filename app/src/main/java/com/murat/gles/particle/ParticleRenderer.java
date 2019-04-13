package com.murat.gles.particle;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.murat.gles.util.MathUtils;
import com.murat.gles.util.TextureHelper;
import com.murat.particles.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import android.opengl.Matrix;

import static com.murat.gles.particle.ParticleConfig.Bean;

public class ParticleRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] modelViewMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private ParticleShader mParticleShader;
    private ParticleSystem mParticleSystem;
    private ParticleShooter mParticleShooter;

    private int mParticleTexture;
    private int mBlendFuncSource;
    private int mBlendFuncDestination;
    private int mFileId = R.drawable.particle_texture;
    private long mStartTime;

    ParticleRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        initParticle();
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        MathUtils.Mat4.perspectiveProjection(projectionMatrix, 45, (float) width / (float) height, 1f, 100f);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.translateM(viewMatrix, 0, 0f, 0f, -5f);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        drawParticle();
    }

    private void initParticle() {
        mBlendFuncDestination = Bean.blendFuncDestination;
        mBlendFuncSource = Bean.blendFuncSource;
        mParticleTexture = TextureHelper.loadTexture(context, mFileId);
        mParticleShader = new ParticleShader(context);
        mParticleSystem = new ParticleSystem(Bean.maxParticles);
        mParticleShooter = new ParticleShooter();
        mParticleShader.useProgram();
        mParticleSystem.bindData(mParticleShader);
    }

    private void drawParticle() {

        float lifeTime = (System.currentTimeMillis() - mStartTime) / 1000f;
        float duration = Bean.duration;

        if (lifeTime <= duration || duration <= 0) {
            mParticleShooter.updatePosition();
            mParticleShooter.updateColor();
            mParticleShooter.updateParticleSize();
            mParticleShooter.updateRotation();
            mParticleShooter.addParticles(mParticleSystem, lifeTime);
        }

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);

        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(mBlendFuncSource, mBlendFuncDestination);

        mParticleShader.setUniforms(modelViewProjectionMatrix, lifeTime, mParticleTexture);
        mParticleSystem.draw();

        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDepthMask(true);
    }
}
