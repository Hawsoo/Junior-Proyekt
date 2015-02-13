#version 120

varying vec3 vertexPosition;
varying vec3 surfaceNormal;

void main()
{
	// Calculate current vector and normal
	vertexPosition = vec3(gl_ModelViewMatrix * gl_Vertex);
	surfaceNormal = normalize(gl_NormalMatrix * gl_Normal);
	
	// Set position as original
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}