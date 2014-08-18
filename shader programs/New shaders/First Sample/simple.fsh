#version 410

/**
 *  Used shader naming convention:
 *  u - uniform
 *  v - vertex shader stage
 *  g - geometry shader stage
 *  f - fragment shader stage
 *  @author Astemir Eleev
 */

uniform vec3 ufLightPosition;

const vec3 lightColor = vec3(0.5, 0.5, 0.5);
const float lightIntensity = 2.0;
const float ambientCoefficient = 0.5;
const float shininess = 0.0;

in vec4 vrx;
in vec4 vrxColor;
in vec4 normal;

out vec4 color;


vec4 toonShading(vec4 diffuse) {
    vec4 diffColor = vec4(0.4, 0.4, 0.4, 1);
	vec3 n2 = normalize(normal.xyz);
	vec3 l2 = normalize(ufLightPosition);
	float diff = 0.2 + max(dot(n2, l2), 0.0);
	vec4 clr;

	if ( diff < 0.4 )
		clr = diffuse* 0.3;
	else
	if ( diff < 0.7 )
		clr = diffColor ;
	else
		clr = diffuse * 1.3;

    return clr;
}

void main(void) {
    vec3 surfaceToLight = normalize(ufLightPosition - vrx.xyz);
    vec3 ambient = ambientCoefficient * vrxColor.rgb * lightIntensity;

    float diffuseCoefficient = max(0.0, dot(normal.xyz, surfaceToLight));
    vec3 diffuse = diffuseCoefficient * vrxColor.rgb * lightIntensity;

    float specularCoefficient = 0.0;
    if(diffuseCoefficient > 0.0) {
        specularCoefficient = pow(max(0.0, dot(surfaceToLight, reflect(-surfaceToLight, normal.xyz))), shininess);
    }
    vec3 specular = specularCoefficient * vec3(1.0, 1.0, 1.0) * lightIntensity;


    //color = vrxColor + vec4(ambient, 1.0) + vec4(diffuse, 1.0) * vec4(lightColor, 1.0) + vec4(specular, 1.0);
    color = vrxColor + vec4(ambient + lightColor * (diffuse  + specular), 0.0);
    //color = vrxColor+ toonShading(vec4(diffuse, 1));


    //color = vrxColor;
}


