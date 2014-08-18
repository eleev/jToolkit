#version 410

layout (location = 0)in vec3 ver;
layout (location = 1)in vec3 col;
layout (location = 2)in vec3 nor;

uniform mat4 uvProjection;
uniform mat4 uvModel;
uniform mat4 uvView;

out vec4 vrxColor;
out vec4 vrx;
out vec4 normal;

vec4 modelViewProjectionTransformation(vec4 vertex) {
    return uvProjection * uvView * uvModel * vertex;
}

void main() {
    vrxColor = vec4(col + 0.5, 1);

    gl_Position = modelViewProjectionTransformation(vec4(ver, 1));

}