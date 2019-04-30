#version 300 es
precision mediump float;

uniform sampler2D u_Texture;
uniform vec4 uColor;
out vec4 FragColor;

in vec2 fragTexCoord;

void main()
{
    vec4 color = texture(u_Texture, fragTexCoord);
    color.w = uColor.w;
    FragColor  = color;
}