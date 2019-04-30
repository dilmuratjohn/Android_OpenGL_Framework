#version 300 es

layout (location = 0) in  vec4 position;
layout (location = 1) in vec2 texCoord;

uniform mat4 uMVPMatrix;

out vec2 fragTexCoord;

void main()
{
    gl_Position = uMVPMatrix * position;
    fragTexCoord = texCoord;
}