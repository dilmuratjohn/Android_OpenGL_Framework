attribute vec4 aPosition;
attribute vec2 aTexCoord;

uniform mat4 uMVPMatrix;

varying vec2 vFragTexCoord;

void main()
{
    gl_Position =  uMVPMatrix *aPosition;
    vFragTexCoord = aTexCoord;
}