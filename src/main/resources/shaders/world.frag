#version 120

uniform sampler2D sampler;
uniform int highlight;

varying vec2 tex_coords;

void main() {
    vec4 texture = texture2D(sampler, tex_coords);

    if (texture.a == 0)
    {
        gl_FragColor = texture;
    }
    else
    {
        gl_FragColor = highlight * vec4(0.1, 0.1, 0.1, 1.0) + texture2D(sampler, tex_coords);
    }
}