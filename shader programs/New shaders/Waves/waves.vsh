#version 410
#pragma debug(off)

/*
 *  INs
 */
layout (location = 0)in vec3 ver;
layout (location = 1)in vec3 col;
layout (location = 2)in vec3 nor;


/*
 *  UNIFORMs
 */
uniform mat4 uvProjection;
uniform mat4 uvModel;
uniform mat4 uvView;
uniform float ufTime = 0.2;

uniform int numWaves = 9;
uniform float amplitude[9] = float[](.1, .175, .25, .325, .4, .475, .55, .625, 1.7);
uniform float wavelength[9] = float[](2, 4, 8, 16, 32, 64, 128, 256, 512);
uniform float speed[9] = float[](0.25, 1, 1.75, 2.25, 3, 3.75, 4.5, 5.25, 6);
uniform vec2 direction[9] = vec2[](vec2(1,0),vec2(1,1),vec2(0,1),vec2(1,0),vec2(1,1),vec2(0,1),vec2(1,0),vec2(1,1), vec2(1, 1));
uniform float stepness[9] = float[](.1, .2, .3, .4, .5, .6, .7, .8, .9);

/*
 *  OUTs
 */
out vec4 v_color;
out vec4 v_vertex;
out vec3 v_normal;


/*
 *  CONSTs
 */
const float pi = 3.14159;
const float G = 9.81;


/*
 *  TEMPs
 */
vec3 t_vertex = vec3(0, 0, 0);
vec3 t_normal;
vec3 t_tangent;
vec3 t_bitangent;


// MVC transform
vec4 modelViewProjectionTransformation(vec4 vertex) {
    return uvProjection * uvView * uvModel * vertex;
}

// MV transform
vec4 modelViewTransformation(vec4 vertex) {
    return uvView * uvModel * vertex;
}

// Normal transform
vec3 normalmatrix(vec3 normal) {
    return mat3(uvView) * mat3(uvModel) * normal;
}

void main() {
    vec4 finalVec = vec4(ver.x, ver.y, ver.z, 1);
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

          //  My experimental modification. Wavelength corrected by relationships between spaeed and phase.
          //  It makes waves faster if their sizes heigher than smaller ones.

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



    t_vertex = finalVec.xyz / finalVec.w;
    gl_Position = modelViewProjectionTransformation(vec4(t_vertex, 1));

    v_vertex = vec4(t_vertex, 1);
    v_normal = t_normal;
    v_color = vec4(0, 0, 1, 1);
}