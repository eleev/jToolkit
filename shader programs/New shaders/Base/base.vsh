#version 410


/*
 *  Used shader naming convention:
 *  u - uniform
 *  v - vertex shader stage
 *  g - geometry shader stage
 *  f - fragment shader stage
 *  @author Astemir Eleev
 */

/*
    IN section
 */

layout (location = 0)in vec3 ver;
layout (location = 1)in vec3 col;
layout (location = 2)in vec3 nor;

uniform mat4 uvProjection;
uniform mat4 uvModel;
uniform mat4 uvView;
uniform float ufTime = 0.2;

// it's not actual position of light source
uniform vec3 ufLightPosition;
// normal matrix
uniform mat4 uvNormal;


const float pi = 3.14159;
const float G = 9.81;
uniform float waterHeight = .1;
uniform int numWaves = 9;
uniform float amplitude[9] = float[](.1, .175, .25, .325, .4, .475, .55, .625, 1.7);
uniform float wavelength[9] = float[](2, 4, 8, 16, 32, 64, 128, 256, 512);
uniform float speed[9] = float[](0.25, 1, 1.75, 2.25, 3, 3.75, 4.5, 5.25, 6);
uniform vec2 direction[9] = vec2[](vec2(1,0),vec2(1,1),vec2(0,1),vec2(1,0),vec2(1,1),vec2(0,1),vec2(1,0),vec2(1,1), vec2(1, 1));
uniform float stepness[9] = float[](.1, .2, .3, .4, .5, .6, .7, .8, .9);


/*
    OUT section
 */

out vec4 v_color;
out vec3 v_vertex;
out vec3 v_normal;
out vec3 v_light;

out vec3 v_bitangent;
out vec3 v_tangent;

/*
    TEMP section
*/
vec4 t_vertex;
vec4 t_color;
vec3 t_normal;
vec3 t_tangent;
vec3 t_bitangent;


vec4 modelViewProjectionTransformation(vec4 vertex) {
    return uvProjection * uvView * uvModel * vertex;
}

vec4 modelViewTransformation(vec4 vertex) {
    return uvView * uvModel * vertex;
}

/*
 *  Preparing some foundation for lightning
 *  @return model - view transformed vertex
 */
vec3 blinnPhongLightning() {
    vec3 p = modelViewTransformation(vec4(ver, 1)).xyz;
    v_normal = mat3(uvView) * mat3(uvModel) * nor;
    v_light = ufLightPosition - p.xyz;
    //light = -p.xyz;
    v_vertex = -p.xyz;
    return p;
}

vec3 blinnPhongLightning(vec4 vertex) {
    vec3 p = modelViewTransformation(vertex).xyz;
    //normal = mat3(uvView) * mat3(uvModel) * nor;
    v_light = ufLightPosition - p.xyz;
    v_vertex = -p.xyz;
    return p;
}


/*
 * Twist simulation part begins
 */
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
/*
 * Twist simulation part ends
 */


/*
 * Water simulation part begins
 */

vec4 waveGenerator(float x, float z) {
    vec4 finalVec = vec4(x, 0, z, 1);
    vec3 finalNor = vec3(0, 0, 0);
    vec3 finalTang = vec3(0, 0, 0);
    vec3 finalBitang = vec3(0, 0, 0);

    for (int i = 0; i < numWaves; i++) {
        vec2 direction = normalize(direction[i]);
        float speed = speed[i];
        float amplitude = amplitude[i];
        float wavelength = wavelength[i];
        float steepness = stepness[i];


        float frequency = sqrt(G * 2.0 * pi / wavelength);
        float phase = speed * frequency;
        /*
            My experimental modification. Wavelength corrected by relationships between spaeed and phase.
            It makes waves faster if their sizes heigher than smaller ones.
         */
        wavelength += speed / phase;
        float alpha = frequency * dot(direction, vec2(finalVec.x, finalVec.z)) + phase * ufTime;

        finalVec.x += steepness * amplitude * direction.x * cos(alpha);
        finalVec.y += amplitude * sin(alpha);
        finalVec.z += steepness * amplitude * direction.y * cos(alpha);

    }

    for (int i = 0; i < numWaves; i++) {
        vec2 direction = normalize(direction[i]);
        float speed = speed[i];
        float amplitude = amplitude[i];
        float wavelength = wavelength[i];
        float steepness = stepness[i];


        float frequency = sqrt(G * 2.0 * pi / wavelength);
        float phase = speed * frequency;
        /*
            My experimental modification. Wavelength corrected by relationships between spaeed and phase.
            It makes waves faster if their sizes heigher than smaller ones.
        */
        wavelength += speed / phase;
        float alpha = frequency * dot(direction, vec2(finalVec.x, finalVec.z)) + phase * ufTime;

        finalNor.x += direction.x * wavelength * amplitude * cos(alpha);
        finalNor.y += steepness * wavelength * amplitude * sin(alpha);
        finalNor.z += direction.y * wavelength * amplitude * cos(alpha);

        finalBitang.x += steepness * direction.x * direction.x * wavelength * amplitude * sin(alpha);
        finalBitang.y += direction.x * wavelength * amplitude * cos(alpha);
        finalBitang.z += steepness * direction.x*direction.y * wavelength * amplitude * sin(alpha);

        finalTang.x += steepness * direction.x*direction.y * wavelength * amplitude * sin(alpha);
        finalTang.y += direction.y * wavelength * amplitude * cos(alpha);
        finalTang.z += steepness * direction.y*direction.y * wavelength * amplitude * sin(alpha);

    }

    t_normal.x = -finalNor.x;
    t_normal.y = 1.0 - finalNor.y;
    t_normal.z = -finalNor.z;
    t_normal = normalize(t_normal);

    t_tangent.x = -finalTang.x;
    t_tangent.y = finalTang.y;
    t_tangent.z = 1.0 -finalTang.z;
    t_tangent = normalize(t_tangent);

    t_bitangent.x = 1.0 -finalBitang.x;
    t_bitangent.y = finalBitang.y;
    t_bitangent.z = -finalBitang.z;
    t_bitangent = normalize(t_bitangent);

    return finalVec;
}

/*
 * Water simulation part ends
 */

void main(void) {
    //vec3 p = blinnPhongLightning();

    vec4 pos = vec4(ver, 1);
    pos = waveGenerator(ver.x, ver.z);
    v_vertex = pos.xyz / pos.w;

    //v_normal = vec3(0, 0, 0);
    //v_vertex  = blinnPhongLightning(vec4(v_vertex, 1));
    //v_normal = modelViewTransformation(vec4(mat3(normalize(t_bitangent),  normalize(t_normal), normalize(t_tangent)) * v_vertex,0)).xyz;
    //v_normal = modelViewTransformation(vec4(waveNormal(t_normal.x, t_normal.z), 0)).xyz;
    //gl_Position = uvProjection * vec4(v_vertex, 1);
    gl_Position = modelViewProjectionTransformation(vec4(v_vertex, 1));

    // transformation for twist
    //gl_Position = modelViewProjectionTransformation(twistMain(ufTime));

    // tranformation for lightning only
    //gl_Position = uvProjection * vec4(p, 1);
    // simple transfolration
    //gl_Position = modelViewProjectionTransformation(vec4(ver, 1));

    //vrxColor = vec4(col.x / 2, col.y / 2, col.z / 2, 1.0);
    v_color = vec4(0, 0, 2, 1);
}