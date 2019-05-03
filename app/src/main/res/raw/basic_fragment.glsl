precision mediump float;

uniform sampler2D uTexture;
uniform vec4 uColor;

varying vec2 vFragTexCoord;

void main()
{
    gl_FragColor = texture2D(uTexture, vFragTexCoord) * uColor;
}