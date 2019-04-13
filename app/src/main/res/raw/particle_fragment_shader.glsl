precision mediump float;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;
varying float v_Rotation;

uniform sampler2D u_TextureUnit;

void main() {


    float xDistance = 0.5 - gl_PointCoord.x;
    float yDistance = 0.5 - gl_PointCoord.y;
    float distanceFromCenter = sqrt(xDistance * xDistance + yDistance * yDistance);
    if (distanceFromCenter > 0.5) {
        discard;
    }

    // Set a center position.
    highp vec2 center = vec2(0.5, 0.5);

    // Translate the center of the point the origin.
    highp vec2 centeredPoint = gl_PointCoord - center;

    // Create a rotation matrix using the provided angle
    highp mat2 rotation = mat2(cos(v_Rotation), sin(v_Rotation), -sin(v_Rotation), cos(v_Rotation));

    // Perform the rotation.
    centeredPoint = rotation * centeredPoint;

    // Translate the point back to its original position and use that point
    // to get your texture color.
    vec4 color = vec4(v_StartColor + (v_End_Color - v_StartColor) / (v_ElapsedTime * 3.5f)) * texture2D(u_TextureUnit, centeredPoint + center);
    gl_FragColor =color;
}
