uniform float     ambientI;
uniform vec4      ambientColor;
uniform float     specPower;
uniform vec4      specColor;
uniform sampler2D diffuseMap;
 
varying vec3 normal;
varying vec3 lightDir;
varying vec3 vh;
varying vec2 tc0;
 
void main(void)
{
   vec3 n = normalize( normal );
 
   vec4 ambient  = ambientI * ambientColor;
   vec4 diffuse  = max( dot( normalize( lightDir ), n ), 0.0 ); 
    //* texture2D( diffuseMap, tc0 );

   vec4 specular = pow( max( dot( n, normalize( vh ) ), 0.0 ), specPower ) * specColor;
   gl_FragColor  = ambient + diffuse + specular;
}