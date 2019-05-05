uniform mat4 u_Matrix;
uniform float u_Time;
uniform int u_Mode;

attribute vec3 a_Position;
attribute vec4 a_StartColor;
attribute vec4 a_EndColor;
attribute float a_Speed;
attribute float a_Angle;
attribute float a_StartTime;
attribute float a_StartSize;
attribute float a_EndSize;
attribute vec2 a_Gravity;
attribute vec2 a_Rotation;
attribute float a_LifeTime;
attribute float a_DegreePerSecond;
attribute vec2 a_Radius;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;
varying vec2 v_Rotation;
varying float v_LifeTime;

void main() {
    v_StartColor = a_StartColor;
    v_End_Color = a_EndColor;
    v_ElapsedTime = u_Time - a_StartTime;
    v_LifeTime = a_LifeTime;
    v_Rotation = a_Rotation;
    vec3 currentPosition = a_Position;

    if (u_Mode == 0){
        currentPosition.x += a_Speed * cos(radians(a_Angle)) * v_ElapsedTime;
        currentPosition.y += a_Speed * sin(radians(a_Angle)) * v_ElapsedTime;
        currentPosition.x += v_ElapsedTime * v_ElapsedTime * 1000.0 * a_Gravity.x;
        currentPosition.y += v_ElapsedTime * v_ElapsedTime * 1000.0 * a_Gravity.y;
    } else {
        float angle = a_Angle + a_DegreePerSecond * v_ElapsedTime * 1000.0;
        float radius = a_Radius.x + v_ElapsedTime * 1000.0 * (a_Radius.y - a_Radius.x) / a_LifeTime;
        currentPosition.x = -cos(radians(angle)) * radius;
        currentPosition.y = -sin(radians(angle)) * radius;
    }
    gl_PointSize = a_StartSize + v_ElapsedTime* 1000.0 * (a_EndSize - a_StartSize) / a_LifeTime;
    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
}
