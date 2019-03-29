#version 120

uniform sampler2D texture_sampler;
uniform vec4 colour;
uniform int hasTexture;

varying vec2 tex_coords;

void main()
{
    if ( hasTexture == 1 )
    {
        gl_FragColor = colour * texture2D(texture_sampler, tex_coords);
    }
    else
    {
        gl_FragColor = colour;
    }
}