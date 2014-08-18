uniform vec4 color;

varying vec3 vNormal;
varying vec3 vViewVec;

void main(void)
{
   float v = 0.5 * (1.0 + dot(normalize(vViewVec), vNormal));
   gl_FragColor = v * color;

}