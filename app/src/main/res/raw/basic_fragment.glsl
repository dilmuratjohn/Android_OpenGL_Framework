#version 300 es
precision mediump float;

uniform sampler2D u_Texture;
uniform vec4 uColor;
out vec4 FragColor;

in vec2 fragTexCoord;

void main()
{
    FragColor  = texture(u_Texture, fragTexCoord) * uColor;
}