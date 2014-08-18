varying vec4 diffuse,ambientGlobal, ambient;
varying vec3 normal,lightDir,halfVector;
varying float dist;
	
	
	void main()
	{
		vec3 n,halfV,viewV,ldir;
		float NdotL,NdotHV;
		//vec4 color = ambientGlobal;
                vec4 color;

		float att;
                float spotEffect;
		
		/* a fragment shader can't write a varying variable, hence we need
		a new variable to store the normalized interpolated normal */
		n = normalize(normal);
		
		/* compute the dot product between normal and normalized lightdir */
		NdotL = max(dot(n,normalize(lightDir)),0.0);
	
		if (NdotL > 0.0) {
	
                    spotEffect = dot(normalize(gl_LightSource[0].spotDirection), 
				normalize(-lightDir));
                    if (spotEffect > gl_LightSource[0].spotCosCutoff) {
		
		
                            /* compute the illumination in here */
                                spotEffect = pow(spotEffect, gl_LightSource[0].spotExponent);
                                att = spotEffect / (gl_LightSource[0].constantAttenuation +
                                                    gl_LightSource[0].linearAttenuation * dist +
                                                    gl_LightSource[0].quadraticAttenuation * dist * dist);
			
		color += att * (diffuse * NdotL + ambient);
                
		halfV = normalize(halfVector);
		NdotHV = max(dot(n,halfV),0.0);
		color += att * gl_FrontMaterial.specular * gl_LightSource[0].specular * pow(NdotHV,gl_FrontMaterial.shininess);

                    }
                }
	
		gl_FragColor = color;
                //gl_FragColor = vec3(color);
	}