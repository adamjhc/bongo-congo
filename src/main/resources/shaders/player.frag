#version 120

uniform sampler2D sampler;
uniform vec3 colour;

varying vec2 tex_coords;

vec4 toGrayscale(in vec4 colour)
{
  float avg = (colour.r + colour.g + colour.b) / 3;
  return vec4(avg, avg, avg, 1);
}

vec4 colourise(in vec4 grayscale, in vec4 colour)
{
    return (grayscale * colour);
}

void main() {
  vec4 texture = texture2D(sampler, tex_coords);

  if (texture.a == 0)
  {
    gl_FragColor = texture;
  }
  else
  {
    vec4 grayscale = toGrayscale(texture);
    vec4 colourised = colourise(grayscale, vec4(colour, 1));

    gl_FragColor = colourised;
  }
}