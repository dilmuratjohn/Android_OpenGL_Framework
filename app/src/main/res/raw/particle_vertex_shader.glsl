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

float Pi = 3.1415926535897932384626433832795;
float Pi180 = float(Pi / 180.0);

void main() {
    v_StartColor = a_Start_Color;
    v_End_Color = a_End_Color;
    v_ElapsedTime = u_Time - a_ParticleStartTime;
    v_Rotation = v_ElapsedTime * a_Rotation * 7.0;

    vec4 force = vec4(0.0, 0.0, 0.0, 0.0);
    vec3 currentPosition = a_Position;
    float timeSquare = v_ElapsedTime * v_ElapsedTime;
    float correction1 = 0.0015;
    float correction2 = 0.35;
    //    float speedCorrection = 0.1;
    if (a_Force.x != 0.0) force.x = (a_Speed.x * cos(a_Speed.y * Pi180) * v_ElapsedTime + timeSquare * a_Force.x);
    if (a_Force.y != 0.0) force.y = (a_Speed.x * sin(a_Speed.y * Pi180) * v_ElapsedTime + timeSquare * a_Force.y);
    if (a_Force.z != 0.0 && force.y != 0.0) force.z = v_ElapsedTime * (cos(atan(force.x/force.y)) * a_Force.z + sin(atan(force.x/force.y)) * a_Force.w);
    if (a_Force.w != 0.0 && force.x != 0.0) force.w = v_ElapsedTime * (cos(atan(force.y/force.x)) * a_Force.w + sin(atan(force.y/force.x)) * a_Force.z);

    // Gravity Verticle
    currentPosition.x += force.x* correction1;
    // Gravity Horizontal
    currentPosition.y += force.y* correction1;
    //  Accel Tangential
    currentPosition.x -= force.z* correction2;
    //  Accel Radial
    currentPosition.y -= force.w* correction2;

    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    gl_PointSize = a_PointSize;
}
