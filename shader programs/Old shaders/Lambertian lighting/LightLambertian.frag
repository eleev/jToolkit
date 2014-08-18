varying vec3 normal;
varying vec4 pos;

void main() {
  vec4 color = gl_FrontMaterial.diffuse;
  vec4 matspec = gl_FrontMaterial.specular;
  float shininess = gl_FrontMaterial.shininess;
  vec4 lightspec = gl_LightSource[0].specular;
  vec4 lpos = gl_LightSource[0].position;
  vec4 s = -normalize(pos-lpos); 

  vec3 light = s.xyz;
  vec3 n = normalize(normal);
  vec3 r = -reflect(light, n);
  r = normalize(r);
  vec3 v = -pos.xyz;
  v = normalize(v);
    
  vec4 diffuse  = color * max(0.0, dot(n, s.xyz)) * gl_LightSource[0].diffuse;
  vec4 specular;

  if (shininess != 0.0) {
    specular = lightspec * matspec * pow(max(0.0, dot(r, v)), shininess);
  } else {
    specular = vec4(0.0, 0.0, 0.0, 0.0);
  }

  gl_FragColor = diffuse + specular;
}