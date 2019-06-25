attribute vec4 aPosition;
attribute vec2 aTexCoord;
attribute float aScale;

uniform float uScale;
uniform vec2 uTranslation;
uniform mat4 uView;
uniform mat4 uProjection;

varying vec2 vFragTexCoord;
varying float vScale;

void main()
{
    vScale = aScale * uScale;

    mat4 uniScale = mat4(
    uScale, 0.0, 0.0, 0.0,
    0.0, uScale, 0.0, 0.0,
    0.0, 0.0, uScale, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    mat4 selfScale = mat4(
    aScale, 0.0, 0.0, 0.0,
    0.0, aScale, 0.0, 0.0,
    0.0, 0.0, aScale, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    // flip image
    mat4 rotate = mat4(
    1.0, 0.0, 0.0, 0.0,
    0.0, -1, 0.0, 0.0,
    0.0, 0.0, -1, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    vec4 position = aPosition;
    position = uProjection * uView * selfScale * uniScale * rotate * position;
    position.x += uTranslation.x;
    position.y -= uTranslation.y;

    gl_Position = position;
    vFragTexCoord = aTexCoord;
    gl_PointSize = 20.0;

}