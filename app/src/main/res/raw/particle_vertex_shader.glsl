uniform mat4 u_Matrix;
uniform float u_Time;

attribute vec3 a_Position;
attribute vec4 a_Start_Color;
attribute vec4 a_End_Color;
attribute vec3 a_DirectionVector;
attribute float a_ParticleStartTime;
attribute float a_PointSize;
attribute vec2 a_GravityFactor;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;


void main() {
    v_StartColor = a_Start_Color;
    v_End_Color = a_End_Color;
    v_ElapsedTime = u_Time - a_ParticleStartTime;

    vec2 gravity = vec2(0.0, 0.0);
    if (a_GravityFactor.x != 0.0){
        gravity.x = v_ElapsedTime * v_ElapsedTime / a_GravityFactor.x;
    }
    if (a_GravityFactor.y != 0.0) {
        gravity.y = v_ElapsedTime * v_ElapsedTime / a_GravityFactor.y;
    }

    vec3 currentPosition = a_Position + (a_DirectionVector * v_ElapsedTime);
    currentPosition.xy -= gravity;

    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    gl_PointSize = a_PointSize;
}
