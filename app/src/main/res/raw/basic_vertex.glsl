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
    vScale = aScale;
    mat4 matrix = mat4(
    1.0, 0.0, 0.0, 0.0,
    0.0, 1.0, 0.0, 0.0,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    matrix[0][0] = matrix[0][0] * aScale;
    matrix[0][1] = matrix[0][1] * aScale;
    matrix[0][2] = matrix[0][2] * aScale;

    matrix[0][0] = matrix[0][0] * uScale;
    matrix[0][1] = matrix[0][1] * uScale;
    matrix[0][2] = matrix[0][2] * uScale;

    vec4 position = aPosition;
    position.x += uTranslation.x;
    position.y += uTranslation.y;
    gl_Position = uProjection * uView * matrix * position;
    vFragTexCoord = aTexCoord;
    gl_PointSize = 20.0;


}