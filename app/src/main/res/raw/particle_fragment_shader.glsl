precision mediump float;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;
varying float v_Rotation;
varying float v_ParticleLifeTime;


uniform sampler2D u_TextureUnit;

void main() {
    float xDistance = 0.5 - gl_PointCoord.x;
    float yDistance = 0.5 - gl_PointCoord.y;
    float distanceFromCenter = sqrt(xDistance * xDistance + yDistance * yDistance);
    if (distanceFromCenter > 0.5) {
        discard;
    }
    if(v_ElapsedTime > v_ParticleLifeTime){
        discard;
    }
    highp vec2 center = vec2(0.5, 0.5);
    highp vec2 centeredPoint = gl_PointCoord - center;
    highp mat2 rotation = mat2(cos(v_Rotation), sin(v_Rotation), -sin(v_Rotation), cos(v_Rotation));
    centeredPoint = rotation * centeredPoint;
    vec4 color = vec4(v_StartColor + v_ElapsedTime * (v_End_Color - v_StartColor) / v_ParticleLifeTime) * texture2D(u_TextureUnit, centeredPoint + center);
    gl_FragColor = color;
}
