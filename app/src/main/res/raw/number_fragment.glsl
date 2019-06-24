precision mediump float;

uniform sampler2D uTexture;
uniform float uScaleThreshold;

varying vec2 vFragTexCoord;
varying float vScale;

void main()
{
    if (vScale <= uScaleThreshold){
        discard;
    }
    gl_FragColor = texture2D(uTexture, vFragTexCoord);
}