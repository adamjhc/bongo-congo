#version 120

attribute vec3 position;
attribute vec2 textures;

varying vec2 tex_coords;

uniform mat4 projModelMatrix;

void main()
{
    tex_coords = textures;
    gl_Position = projModelMatrix * vec4(position, 1.0);
}