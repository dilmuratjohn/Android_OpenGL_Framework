precision mediump float;

uniform sampler2D uTexture;
uniform vec4 uColor;
uniform float uScaleThreshold;

varying vec2 vFragTexCoord;
varying float vScale;

void main()
{
    if(uScaleThreshold >= vScale){
        discard;
    }
    gl_FragColor = texture2D(uTexture, vFragTexCoord) * uColor;
}