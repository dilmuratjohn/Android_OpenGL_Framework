attribute vec4 aPosition;
attribute vec2 aTexCoord;
attribute float aScale;

uniform mat4 uMVPMatrix;
uniform float scaleThreshold;

varying vec2 vFragTexCoord;
varying float vScale;

void main()
{
    vScale = aScale;
    gl_Position = uMVPMatrix * aPosition;
    vFragTexCoord = aTexCoord;
}