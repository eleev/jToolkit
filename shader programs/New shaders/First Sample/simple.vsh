#version 410

/**
 *  Used shader naming convention:
 *  u - uniform
 *  v - vertex shader stage
 *  g - geometry shader stage
 *  f - fragment shader stage
 *  @author Astemir Eleev
 */

layout (location = 0)in vec3 ver;
layout (location = 1)in vec3 col;
layout (location = 2)in vec3 nor;

uniform mat4 uvProjection;
uniform mat4 uvModel;
uniform mat4 uvView;

uniform mat4 uvNormal;

out vec4 vrxColor;
out vec4 vrx;
out vec4 normal;

vec4 modelViewProjectionTransformation(vec4 vertex) {
    return uvProjection * uvView * uvModel * vertex;
}

vec4 modelViewTransformation(vec4 vertex) {
    return uvView * uvModel * vertex;
}

void main(void) {
    vrx = (uvView * uvModel * vec4(ver, 1));
    normal = normalize(uvNormal * vec4(nor, 1));

    gl_Position = modelViewProjectionTransformation(vec4(ver, 1));
    //vrxColor = vec4(col.x - 0.2, col.y- 0.2, col.z - 0.2, 1.0);
    vrxColor = vec4(1, 1, 1, 1);
}


