package com.murat.gles.particle;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;

import com.murat.gles.util.MathUtils;
import com.murat.gles.util.TextureHelper;

import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

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

    private int particleTexture;

    ParticleRenderer(Context context) {
        this.context = context;
    }

    private long mStartTime;

    private Map<String, Object> mConfig;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        createParticle();
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        MathUtils.Mat4.perspectiveProjection(projectionMatrix, 45, (float) width / (float) height, 1f, 100f);
        updateViewMatrices();
        glEnable(GL_CULL_FACE);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        drawParticles();
    }

    private void createParticle() {
        mConfig = new ParticleConfig("").getConfigMap();
        int fileId = (int) mConfig.get("textureFileName");
        int maxParticleCount = (int) mConfig.get("maxParticles");

        float speed = (float) mConfig.get("speed");
        float angle = (float) mConfig.get("angle");
        float speedVariance = (float) mConfig.get("speedVariance");
        float angleVariance = (float) mConfig.get("angleVariance");

        int blendFuncDestination = (int) mConfig.get("blendFuncDestination");
        int blendFuncSource = (int) mConfig.get("blendFuncSource");

        float startColorRed = (float) mConfig.get("startColorRed");
        float startColorGreen = (float) mConfig.get("startColorGreen");
        float startColorBlue = (float) mConfig.get("startColorBlue");
        float startColorAlpha = (float) mConfig.get("startColorAlpha");

        glEnable(GL_BLEND);
        glBlendFunc(blendFuncSource, blendFuncDestination);

        particleTexture = TextureHelper.loadTexture(context, fileId);

        particleShader = new ParticleShader(context);
        particleSystem = new ParticleSystem(maxParticleCount);
        mStartTime = System.currentTimeMillis();

        final MathUtils.Vec3 particleDirection = new MathUtils.Vec3(0f, 0.5f, 0f);

        particleShooter = new ParticleShooter(
                new MathUtils.Vec3(0f, 0f, 0f),
                particleDirection,
                new MathUtils.Vec4(startColorRed, startColorGreen, startColorBlue, startColorAlpha),
                speed,
                angle,
                angleVariance,
                speedVariance
        );
    }

    private void drawParticles() {
        float lifeTime = (System.currentTimeMillis() - mStartTime) / 1000f;
        float duration = (int) mConfig.get("duration");
        if (lifeTime <= duration || duration <= 0) {
            particleShooter.addParticles(particleSystem, lifeTime, 1);
        }

        setIdentityM(modelMatrix, 0);
        updateMVPMatrix();

        particleShader.useProgram();
        particleShader.setUniforms(modelViewProjectionMatrix, lifeTime, particleTexture);
        particleSystem.bindData(particleShader);
        particleSystem.draw();
    }

    private void updateViewMatrices() {
        setIdentityM(viewMatrix, 0);
        translateM(viewMatrix, 0, 0f, 0f, -5f);
    }

    private void updateMVPMatrix() {
        multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);
    }

}
