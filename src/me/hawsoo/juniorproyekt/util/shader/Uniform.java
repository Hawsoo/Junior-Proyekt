package me.hawsoo.juniorproyekt.util.shader;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Loads uniforms for the user
 * completely painlessly.
 * @author Administrator
 *
 */
public class Uniform
{
	// Reusable variables
	private static FloatBuffer matrix4fBuffer = BufferUtils.createFloatBuffer(16);		// For 4f (4 x 4) floatbuffers
	
	/**
	 * Loads a float uniform.
	 * @param uniformLocation - the location of the uniform
	 * @param value - the value for the uniform
	 */
	public static void loadFloat(int uniformLocation, float value)
	{
		glUniform1f(uniformLocation, value);
	}
	/**
	 * Loads a mat4 uniform.
	 * @param uniformLocation - the location of the uniform
	 * @param value - the value for the uniform
	 */
	public static void loadMatrix4f(int uniformLocation, Matrix4f value)
	{
		value.store(matrix4fBuffer);
		matrix4fBuffer.flip();
		glUniformMatrix4(uniformLocation, false, matrix4fBuffer);
	}
}
