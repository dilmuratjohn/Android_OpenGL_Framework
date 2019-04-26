uniform mat4 u_Matrix;
uniform float u_Time;

attribute vec3 a_Position;
attribute vec4 a_StartColor;
attribute vec4 a_EndColor;
attribute float a_Speed;
attribute float a_Angle;
attribute float a_ParticleStartTime;
attribute float a_StartSize;
attribute float a_EndSize;
attribute vec2 a_Gravity;
attribute vec2 a_Rotation;
attribute float a_ParticleLifeTime;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;
varying vec2 v_Rotation;
varying float v_ParticleLifeTime;

void main() {
    v_StartColor = a_StartColor;
    v_End_Color = a_EndColor;
    v_ElapsedTime = u_Time - a_ParticleStartTime;
    v_ParticleLifeTime = a_ParticleLifeTime;
    v_Rotation = a_Rotation;
    vec3 currentPosition = a_Position;

    currentPosition.x += a_Speed * cos(radians(a_Angle)) * v_ElapsedTime;
    currentPosition.y += a_Speed * sin(radians(a_Angle)) * v_ElapsedTime;

    float timeSquare = v_ElapsedTime * v_ElapsedTime;
    currentPosition.x -= timeSquare * a_Gravity.x;
    currentPosition.y -= timeSquare * a_Gravity.y;

    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    gl_PointSize = a_StartSize + v_ElapsedTime * (a_EndSize - a_StartSize) / v_ParticleLifeTime;
}
