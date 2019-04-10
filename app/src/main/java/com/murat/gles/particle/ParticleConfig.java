package com.murat.gles.particle;

import com.murat.particles.R;

import java.util.HashMap;
import java.util.Map;

public class ParticleConfig {
    private Map<String, Object> config;

    public ParticleConfig(String path) {
        config = new HashMap<>();
        config.put("angle", 45f);
        config.put("angleVariance", 315f);
        config.put("blendFuncDestination", 1);
        config.put("blendFuncSource", 770);
        config.put("maxParticles", 322);
        config.put("speed", 540f);
        config.put("speedVariance", 20f);
        config.put("duration", 15);

        config.put("startColorAlpha", 1f);
        config.put("startColorBlue", 1f);
        config.put("startColorGreen", 0f);
        config.put("startColorRed", 0.5f);
        config.put("startColorVarianceAlpha", 0f);
        config.put("startColorVarianceBlue", 0f);
        config.put("startColorVarianceGreen", 0f);
        config.put("startColorVarianceRed", 0f);

        config.put("finishColorAlpha", 1f);
        config.put("finishColorBlue", 0.6f);
        config.put("finishColorGreen", 0.5f);
        config.put("finishColorRed", 0.5f);
        config.put("finishColorVarianceAlpha", 0f);
        config.put("finishColorVarianceBlue", 0f);
        config.put("finishColorVarianceGreen", 0f);
        config.put("finishColorVarianceRed", 0f);


        config.put("emitterType", 0);

        config.put("finishParticleSize", -1f);
        config.put("finishParticleSizeVariance", 0f);
        config.put("gravityx", 0f);
        config.put("gravityy", -440f);
        config.put("maxRadius", 0f);
        config.put("maxRadiusVariance", 0f);
        config.put("minRadius", 0f);
        config.put("particleLifespan", 1.8f);
        config.put("particleLifespanVariance", 0.25f);
        config.put("radialAccelVariance", 0f);
        config.put("radialAcceleration", 0f);
        config.put("rotatePerSecond", 0f);
        config.put("rotatePerSecondVariance", 0f);
        config.put("rotationEnd", 0f);
        config.put("rotationEndVariance", 0f);
        config.put("rotationStart", 0f);
        config.put("rotationStartVariance", 0f);
        config.put("sourcePositionVariancex", -5f);
        config.put("sourcePositionVariancey", -10f);
        config.put("sourcePositionx", 316f);
        config.put("sourcePositiony", 374f);
        config.put("startParticleSize", 54f);
        config.put("startParticleSizeVariance", 10f);
        config.put("tangentialAccelVariance", 0f);
        config.put("tangentialAcceleration", 7f);
        config.put("textureFileName",R.drawable.particle_texture);
    }

    public Map<String, Object> getConfigMap() {
        return config;
    }
}
