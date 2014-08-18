uniform vec3 lightDir = vec3(0.0, 0.0, 0.1);
	
	varying float intensity;
	
	void main()
	{
		vec3 ld;
		
		intensity = dot(lightDir,gl_Normal);
		
		gl_Position = ftransform();
	} 