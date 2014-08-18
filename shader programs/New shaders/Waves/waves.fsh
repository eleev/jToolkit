#version 410

uniform sampler2D texture;
/*
 * INs
 */
in vec4 v_color;
in vec4 v_vertex;
in vec3 v_normal;


/*
 * OUTs
 */
out vec4 color;

void main() {
    //color = texelFetch(texelFetch, ivec2(gl_FragCoord.x / 1.5, gl_FragCoord.y / 1.5), 0).rbga;
    color = v_color;
}