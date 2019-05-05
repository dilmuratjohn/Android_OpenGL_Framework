precision mediump float;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;
varying vec2 v_Rotation;
varying float v_ParticleLifeTime;

uniform sampler2D u_TextureUnit;

void main() {
    float xDistance = 0.5 - gl_PointCoord.x;
    float yDistance = 0.5 - gl_PointCoord.y;
    float distanceFromCenter = sqrt(xDistance * xDistance + yDistance * yDistance);
    if (distanceFromCenter > 0.5) {
        discard;
    }
    if(v_ElapsedTime * 1000.0 > v_ParticleLifeTime ){
        discard;
    }

    vec2 center = vec2(0.5, 0.5);
    vec2 centeredPoint = gl_PointCoord - center;
    float r = v_Rotation.x + v_ElapsedTime * (v_Rotation.y - v_Rotation.x) / v_ParticleLifeTime;
    mat2 rotation = mat2(cos(r), sin(r), -sin(r), cos(r));
    centeredPoint = rotation * centeredPoint;
    vec4 color = vec4(v_StartColor + v_ElapsedTime * (v_End_Color - v_StartColor) / v_ParticleLifeTime) * texture2D(u_TextureUnit, centeredPoint + center);
    gl_FragColor = color;
}
