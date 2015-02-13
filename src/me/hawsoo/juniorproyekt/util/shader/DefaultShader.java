package me.hawsoo.juniorproyekt.util.shader;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import me.hawsoo.juniorproyekt.res.Resources;

/**
 * This shader is the default shader of the program.
 * @author Administrator
 *
 */
public class DefaultShader extends Shader
{
	// Uniforms
	private int u_lightIntensity;			private float lightIntensity = 1;
	private int u_shineDamper;				private float shineDamper = 10;
//	private int u_modelViewMatrix;			//private Matrix4f modelViewMatrix;
//	private int u_prevModelViewMatrix;		//private Matrix4f prevModelViewMatrix;
	private int u_celHighBound;				private float celHighBound = 0.75f;
	private int u_celLowBound;				private float celLowBound = 0.15f;
	private int u_celHighMultiplier;		private float celHighMultiplier = 3;
	private int u_celLowMultiplier;			private float celLowMultiplier = 0.5f;
	
	public DefaultShader()
	{
		// Create the shader
		shaderID =
				Shader.loadShaders(DefaultShader.class.getResourceAsStream(Resources.dirRes + "shader/default_shader.vert"), DefaultShader.class.getResourceAsStream(Resources.dirRes + "shader/default_shader.frag"));
		
		// Get the uniform locations
		u_lightIntensity = glGetUniformLocation(shaderID, "lightIntensity");
		u_shineDamper = glGetUniformLocation(shaderID, "shineDamper");
//		u_modelViewMatrix = glGetUniformLocation(shaderID, "modelViewMatrix");
//		u_prevModelViewMatrix = glGetUniformLocation(shaderID, "prevModelViewMatrix");
		u_celHighBound = glGetUniformLocation(shaderID, "celHighBound");
		u_celLowBound = glGetUniformLocation(shaderID, "celLowBound");
		u_celHighMultiplier = glGetUniformLocation(shaderID, "celHighMultiplier");
		u_celLowMultiplier = glGetUniformLocation(shaderID, "celLowMultiplier");
	}
	
	/**
	 * Sets the light in the shader.
	 * @param light - a light object
	 */
	/*public void setLight(Light light)
	{
		lightIntensity = light.getPosition();
		lightcolor = light.getColor();
	}*/

	/**
	 * Uses the default shader.
	 */
	@Override
	public void useShader()
	{
		// Set the shader
		glUseProgram(shaderID);
		
		// Set the uniforms
		Uniform.loadFloat(u_lightIntensity, lightIntensity);
		Uniform.loadFloat(u_shineDamper, shineDamper);
//		Uniform.loadMatrix4f(u_modelViewMatrix, Resources.modelViewMatrix);
//		Uniform.loadMatrix4f(u_prevModelViewMatrix, Resources.prevModelViewMatrix);
		Uniform.loadFloat(u_celHighBound, celHighBound);
		Uniform.loadFloat(u_celLowBound, celLowBound);
		Uniform.loadFloat(u_celHighMultiplier, celHighMultiplier);
		Uniform.loadFloat(u_celLowMultiplier, celLowMultiplier);
	}
}
