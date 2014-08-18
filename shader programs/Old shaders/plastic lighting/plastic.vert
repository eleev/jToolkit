uniform vec4 view_position;

varying vec3 vNormal;
varying vec3 vViewVec;

void main(void)
{
   gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;

   // World-space lighting
   vNormal = gl_Normal;
   vViewVec = view_position.xyz - gl_Vertex.xyz;
   
}