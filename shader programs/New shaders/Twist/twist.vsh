#version 410

layout (location = 0)in vec3 ver;
layout (location = 1)in vec3 col;
layout (location = 2)in vec3 nor;

uniform mat4 uvProjection;
uniform mat4 uvModel;
uniform mat4 uvView;
uniform float ufTime = 0.2;
uniform vec3 ufLightPosition;
uniform mat4 uvNormal;

out vec4 v_color;
out vec3 v_vertex;
out vec3 v_normal;
out vec3 v_light;

vec3 blinnPhongLightning() {
    vec3 p = modelViewTransformation(vec4(ver, 1)).xyz;
    v_normal = mat3(uvView) * mat3(uvModel) * nor;
    v_light = ufLightPosition - p.xyz;
    v_vertex = -p.xyz;
    return p;
}

vec4 doTwist(vec3 vertex, float time) {
    float st = sin(time);
    float ct = cos(time);

    vec4 newVertex;

    newVertex.x = vertex.x * ct - vertex.z * st;
    newVertex.y = vertex.y;
    newVertex.z = vertex.x * st + vertex.z * ct;
    newVertex.w = 1.0;

    return newVertex;
}

vec4 twistMain(float time) {
    float angDeg = 360 * sin(time) * cos(time);
    float angRad = (angDeg * 3.14159) / 180.0;

    float finalAngle = ver.y / angRad;

    vec4 twistedVertex = doTwist(ver, finalAngle);
    vec4 twistedNormal = doTwist(v_normal, finalAngle);

    v_normal = vec3(twistedNormal);
    return twistedVertex;
}

void main() {
    blinnPhongLightning();
    gl_Position = modelViewProjectionTransformation(twistMain(ufTime));
}