package com.murat.gles.particle;

import com.murat.particles.R;

import java.util.HashMap;
import java.util.Map;

class ParticleConfig {
    private Map<String, Object> config;

    ParticleConfig(String path) {
        config = new HashMap<>();
        config.put("angle", 90f);
        config.put("angleVariance", 10f);
        config.put("speed", 54f);
        config.put("speedVariance", 3f);
        config.put("blendFuncDestination", 1);
        config.put("blendFuncSource", 770);
        config.put("maxParticles", 322);
        config.put("duration", -15);
        config.put("startParticleSize", 120f);
        config.put("startColorAlpha", 1f);
        config.put("startColorBlue", 0f);
        config.put("startColorGreen", 1f);
        config.put("startColorRed", 0f);
        config.put("finishColorAlpha", 1f);
        config.put("finishColorBlue", 1f);
        config.put("finishColorGreen", 0f);
        config.put("finishColorRed", 1f);
        config.put("gravityx", 0f);
        config.put("gravityy", -440f);
        //TODO finish below...
        config.put("startColorVarianceAlpha", 0f);
        config.put("startColorVarianceBlue", 0f);
        config.put("startColorVarianceGreen", 0f);
        config.put("startColorVarianceRed", 0f);
        config.put("finishColorVarianceAlpha", 0f);
        config.put("finishColorVarianceBlue", 0f);
        config.put("finishColorVarianceGreen", 0f);
        config.put("finishColorVarianceRed", 0f);
        config.put("startParticleSizeVariance", 15f);
        config.put("emitterType", 0);
        config.put("finishParticleSize", -1f);
        config.put("finishParticleSizeVariance", 0f);
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
        config.put("tangentialAccelVariance", 0f);
        config.put("tangentialAcceleration", 7f);
        config.put("textureFileName", R.drawable.particle_texture);
    }

    Map<String, Object> getConfigMap() {
        return config;
    }
}
