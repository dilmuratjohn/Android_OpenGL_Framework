uniform mat4 u_Matrix;
uniform float u_Time;

attribute vec3 a_Position;
attribute vec4 a_Start_Color;
attribute vec4 a_End_Color;
attribute vec2 a_Speed;
attribute float a_ParticleStartTime;
attribute float a_PointSize;
attribute vec4 a_Force;
attribute float a_Rotation;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;
varying float v_Rotation;

void main() {
    v_StartColor = a_Start_Color;
    v_End_Color = a_End_Color;
    v_ElapsedTime = u_Time - a_ParticleStartTime;
    v_Rotation = v_ElapsedTime * a_Rotation;

    vec4 force = vec4(0.0f, 0.0f, 0.0f, 0.0f);
    vec3 currentPosition = a_Position;

    if (a_Force.x != 0.0f) force.x = ( v_ElapsedTime * v_ElapsedTime * a_Force.x) * 1.f / 100.0f;
    if (a_Force.y != 0.0f) force.y = (  v_ElapsedTime * v_ElapsedTime * a_Force.y) * 1.f / 100.0f;
    if (a_Force.z != 0.0f) force.z = (v_ElapsedTime * a_Force.z )* 1.f / 10.0f;
    if (a_Force.w != 0.0f) force.w = (v_ElapsedTime * a_Force.w )* 1.f / 10.0f;

    // Origin Speed
//    currentPosition.x += v_ElapsedTime * 0.5f;
    // Gravity Verticle
    currentPosition.x += force.x;
    // Gravity Horizontal
    currentPosition.y += force.y;
    //  Accel Tangential
    currentPosition.x += force.z;
    //  Accel Radial
    currentPosition.y += force.w;

    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    gl_PointSize = a_PointSize;
}
