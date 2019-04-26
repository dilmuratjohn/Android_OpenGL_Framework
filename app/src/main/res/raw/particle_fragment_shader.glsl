precision mediump float;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;
varying vec2 v_Rotation;
varying float v_ParticleLifeTime;


uniform sampler2D u_TextureUnit;

void main() {
    highp float xDistance = 0.5 - gl_PointCoord.x;
    highp float yDistance = 0.5 - gl_PointCoord.y;
    highp float distanceFromCenter = sqrt(xDistance * xDistance + yDistance * yDistance);
    if (distanceFromCenter > 0.5) {
        discard;
    }
    if(v_ElapsedTime > v_ParticleLifeTime){
        discard;
    }


    highp vec2 center = vec2(0.5, 0.5);
    highp vec2 centeredPoint = gl_PointCoord - center;
    highp float r = v_Rotation.x + v_ElapsedTime * (v_Rotation.y - v_Rotation.x) / v_ParticleLifeTime;
    highp mat2 rotation = mat2(cos(r), sin(r), -sin(r), cos(r));
    centeredPoint = rotation * centeredPoint;
    highp vec4 color = vec4(v_StartColor + v_ElapsedTime * (v_End_Color - v_StartColor) / v_ParticleLifeTime) * texture2D(u_TextureUnit, centeredPoint + center);
    gl_FragColor = color;
}
