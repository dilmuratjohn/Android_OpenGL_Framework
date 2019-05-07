package com.murat.android.opengl.particle;

import java.util.ArrayList;
import java.util.Map;

class ParticleBean {

    String textureFileName;

    int blendFuncDestination;
    int blendFuncSource;

    int maxParticles;

    float duration;

    float startParticleSize;
    float startParticleSizeVariance;
    float finishParticleSize;
    float finishParticleSizeVariance;

    float gravityx;
    float gravityy;

    float startColorRed;
    float startColorVarianceRed;
    float finishColorRed;
    float finishColorVarianceRed;
    float startColorGreen;
    float startColorVarianceGreen;
    float finishColorGreen;
    float finishColorVarianceGreen;
    float startColorBlue;
    float startColorVarianceBlue;
    float finishColorBlue;
    float finishColorVarianceBlue;
    float startColorAlpha;
    float startColorVarianceAlpha;
    float finishColorAlpha;
    float finishColorVarianceAlpha;

    float particleLifespan;
    float particleLifespanVariance;

    float rotationStart;
    float rotationStartVariance;
    float rotationEnd;
    float rotationEndVariance;

    float speed;
    float speedVariance;

    float angle;
    float angleVariance;

    int emitterType;

    float sourcePositionx;
    float sourcePositiony;
    float sourcePositionVariancey;
    float sourcePositionVariancex;

    float rotatePerSecond;
    float rotatePerSecondVariance;

    float maxRadius;
    float maxRadiusVariance;
    float minRadius;
    float minRadiusVariance;
    float radialAccelVariance;

    float radialAcceleration;
    float tangentialAccelVariance;
    float tangentialAcceleration;

    ArrayList<Map<String, Float>> colorSet;

}
