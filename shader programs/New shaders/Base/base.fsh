#version 410

/**
 *  Used shader naming convention:
 *  u - uniform
 *  v - vertex shader stage
 *  g - geometry shader stage
 *  f - fragment shader stage
 *  @author Astemir Eleev
 */

// Material properties
const vec3 diffuse_albedo = vec3(0.54, 0.54, 0.54);
const vec3 specular_albedo = vec3(1.0);
const float specular_power = 128.0;
const vec3 ambient = vec3(0.1, 0.1, 0.1);

in vec3 v_vertex;
in vec4 v_color;
//in vec3 v_normal;
in vec3 v_light;

in vec3 v_bitangent;
in vec3 v_tangent;

out vec4 color;

vec3 rainbow(float zval) {
    vec3 myColor;
    if (zval < 0.) {
        myColor.r = 0.;
        myColor.g = 0.;
        myColor.b = 0.;
    } else if ((zval >= 0.) && (zval < 0.2)) {
        myColor.r = 0.5*(1.0-zval/0.2);
        myColor.g = 0.0;
        myColor.b = 0.5+(0.5*zval/0.2);
    } else if ((zval >= 0.2) && (zval < 0.40)){
        myColor.r = 0.0;
        myColor.g = (zval-0.2)*5.0;
        myColor.b = 1.0;
    } else if ((zval >= 0.40) && (zval < 0.6)) {
        myColor.r = 0.0;
        myColor.g = 1.0;
        myColor.b = (0.6-zval)*5.0;
    } else if ((zval >= 0.6) && (zval < 0.8)) {
        myColor.r = (zval-0.6)*5.0;
        myColor.g = 1.0;
        myColor.b = 0.0;
    } else if (zval >= 0.8) {
        myColor.r = 1.0;
        myColor.g = (1.0-zval)*5.0;
        myColor.b = 0.0;
    } else {
        myColor.r = 1.;
        myColor.g = 1;
        myColor.b = 1.;
    }

    return myColor;
}


void main(void) {
   //* vec3 n = normalize(v_normal);
   //* vec3 l = normalize(v_light);
   //* vec3 v = normalize(v_vertex);
    // this is used in phong lightning model
    //vec3 r = reflect(-l, n);
    // this is used in blinn phong lightning model. this vector also known as half vector
   //* vec3 h = normalize(l + v);
   //* vec3 diffuse = max(dot(n, l), 0.0) * diffuse_albedo;//* vrxColor.xyz;
    // this is used as well in phong lightning model
    //vec3 specular = pow(max(dot(r, v), 0.0), specular_power) * specular_albedo;// * vrxColor.xyz;

    // this kind of specular color is used in blinn phong lightning model
   //* vec3 specular = pow(max(dot(n, h), 0.0), specular_power) * specular_albedo;// * vrxColor.xyz;
    //color = vec4((vrxColor.xyz + ambient + diffuse + specular), 1);// * vrxColor;
    color = v_color;

    //bvec3 toDiscard = greaterThan(fract(vrxColor.xz * 10), vec3(0.2,0.2,0.2));

    //if(all(toDiscard)/* && gl_FrontFacing*/)  {
    //    discard;
    //}
}


