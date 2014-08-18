#version 410

layout (location = 0)in vec3 ver;
layout (location = 1)in vec3 col;
layout (location = 2)in vec3 nor;

uniform mat4 uvProjection;
uniform mat4 uvModel;
uniform mat4 uvView;

out vec4 var_normal;
out vec4 var_color;
out vec4 var_vertex;

void main() {

    var_normal = vec4(nor, 0);
    var_color = vec4(col, 1);
    var_vertex = vec4(ver, 1);
    gl_Position = uvProjection * uvView * uvModel * vec4(ver, 1);
}