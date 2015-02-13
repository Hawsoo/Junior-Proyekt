#version 120

varying vec3 vertexPosition;
varying vec3 surfaceNormal;

uniform float lightIntensity;
uniform float shineDamper;

uniform float celHighBound;
uniform float celLowBound;
uniform float celHighMultiplier;
uniform float celLowMultiplier;

vec4 applyCelShading(vec4 original)
{
	// Find the zone of the lighting
	float redZone = original.r;
	float greenZone = original.g;
	float blueZone = original.b;
	
	// Get average among
	float average = (redZone + greenZone + blueZone) / 3;
	
	// Calculate standing of average
	vec3 additiveAmount = vec3(1, 1, 1);
	if (average > celHighBound)
	{
	    // Add a specular standing
	    additiveAmount = vec3(celHighMultiplier, celHighMultiplier, celHighMultiplier);
	}
	else if (average < celLowBound)
	{
	    // Add a diffuse standing
	    additiveAmount = vec3(celLowMultiplier, celLowMultiplier, celLowMultiplier);
	}
	
	// Rejoin
	return gl_FrontMaterial.diffuse * vec4(additiveAmount, original.a);
}

void main()
{
	// Calculate directions
	vec3 lightDirection = normalize(gl_LightSource[0].position.xyz - vertexPosition);		// Direction of vertex to light
	vec3 cameraDirection = normalize(-vertexPosition);										// Direction of vertex to eye (EyePos is (0,0,0))
	vec3 reflectedLightDirection = normalize(reflect(lightDirection, surfaceNormal));		// Direction of reflected light off vertex
	
	// Ambient light
	vec4 ambience = gl_LightModel.ambient;
	
	// Diffuse light
	float diffuseIntensity = max(0.0, dot(surfaceNormal, lightDirection));
	vec4 diffuse = gl_FrontMaterial.diffuse * lightIntensity * diffuseIntensity;
	diffuse = clamp(diffuse, 0.0, 1.0);
	
	// Specular light
	vec4 specular = vec4(0, 0, 0, 1);
	if (diffuseIntensity > 0)
	{
	    specular = gl_FrontMaterial.shininess * gl_FrontMaterial.diffuse * pow(max(0.0, dot(reflectedLightDirection, cameraDirection)), shineDamper);
	}
	specular = clamp(specular, 0.0, 1.0);
	
	// Calculate outline
	
	// Set total color
	gl_FragColor = vec4(gl_FrontLightModelProduct.sceneColor.xyz + ambience.xyz + /*applyCelShading(*/diffuse.xyz + specular /*)*/.xyz, 1.0);
}