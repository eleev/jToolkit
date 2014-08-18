uniform vec3 lightPosition;
uniform vec4 vViewPosition;
 
varying vec3 normal;
varying vec3 lightDir;
varying vec3 vh;
varying vec2 tc0;
 
void main(void)
{
   vec3 pos    = gl_Vertex.xyz;
   lightDir    = normalize( lightPosition - pos );
   vec3 eyeDir = normalize( vViewPosition.xyz - pos );
   vh          = normalize( lightDir + eyeDir );   
   normal      = gl_NormalMatrix * gl_Normal;
 
   gl_Position = ftransform();
   tc0         = gl_MultiTexCoord0.xy;
}