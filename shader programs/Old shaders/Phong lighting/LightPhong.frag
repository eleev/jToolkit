precision highp float;
#endif
varying vec2 vUv;
varying vec3 vNormal;

uniform vec3 mainColor;
uniform float specularExponent;
uniform vec3 specularColor;
uniform sampler2D mainTexture;
uniform mat3 _dLight;
uniform vec3 _ambient;

void getDirectionalLight(vec3 normal, mat3 dLight, float specularExponent, out vec3 diffuse, out float specular){
    vec3 ecLightDir = dLight[0]; // light direction in eye coordinates
    vec3 colorIntensity = dLight[1];
    vec3 halfVector = dLight[2];
    float diffuseContribution = max(dot(normal, ecLightDir), 0.0);
    float specularContribution = max(dot(normal, halfVector), 0.0);
    specular =  pow(specularContribution, specularExponent);
	diffuse = (colorIntensity * diffuseContribution);
}

void main(void)
{
    vec3 diffuse;
    float specular;
    getDirectionalLight(normalize(vNormal), _dLight, specularExponent, diffuse, specular);
    vec3 color = max(diffuse,_ambient.xyz)*mainColor;
    
    gl_FragColor = texture2D(mainTexture,vUv)*vec4(color, 1.0)+vec4(specular*specularColor,0.0);
}