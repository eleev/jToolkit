#version 410

uniform sampler2D texture;

in vec4 var_normal;
in vec4 var_color;
in vec4 ver_vertex;

out vec4 final_color;

void main() {
    final_color = texelFetch(texture, ivec2(gl_FragCoord.xy), 0);
    //final_color = var_color;
}