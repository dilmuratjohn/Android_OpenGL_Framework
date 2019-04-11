precision mediump float;

varying vec4 v_StartColor;
varying vec4 v_End_Color;
varying float v_ElapsedTime;

uniform sampler2D u_TextureUnit;

void main() {
    float xDistance = 0.5 - gl_PointCoord.x;
    float yDistance = 0.5 - gl_PointCoord.y;
    float distanceFromCenter = sqrt(xDistance * xDistance + yDistance * yDistance);
    if (distanceFromCenter > 0.7) {
        discard;
    } else {
        gl_FragColor = vec4(v_StartColor + (v_End_Color - v_StartColor) / (v_ElapsedTime * 5.0f)) * texture2D(u_TextureUnit, gl_PointCoord);
    }
}