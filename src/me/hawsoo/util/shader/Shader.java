package me.hawsoo.util.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class imports shaders.
 * @author Administrator
 *
 */
public abstract class Shader
{
	protected int shaderID;
	
	/**
	 * Returns the shader ID.
	 * @return the shader ID
	 */
	public int getShaderID()
	{
		return shaderID;
	}
	
	/**
	 * This sets up the shader requested.
	 */
	public abstract void useShader();
	
	/**
	 * This loads a vertex and fragment
	 * shader together and spits out the ID.
	 * (The shaders are all in the same package,
	 * so only the name of each should be required)
	 * @param vertShader - the name of the vertex shader
	 * @param fragShader - the name of the fragment shader
	 * @return the ID of the shaders' program
	 */
	public static int loadShaders(InputStream vertShader, InputStream fragShader)
	{
		int id = glCreateProgram();
		
		// Init them shaders
		int vShader = glCreateShader(GL_VERTEX_SHADER);
		int fShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		// Load vertex shader
		String vertSource = "";
		try
		{
			BufferedReader bReader = new BufferedReader(new InputStreamReader(vertShader));
			{
				// Cycle thru to get source
				String currentline;
				while ((currentline = bReader.readLine()) != null)
				{
					vertSource += currentline + "\n";
				}
			}
			bReader.close();
		} catch (IOException e) {}
		
		// Compile
		glShaderSource(vShader, vertSource);
		glCompileShader(vShader);
		if (glGetShaderi(vShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			// Throw error
			System.out.println(
					"ERROR: vertex shader miscompiled.\n\n"
					+ glGetShaderInfoLog(vShader, 1024));
			return -1;
		}
		
		// Load fragment shader
		String fragSource = "";
		try
		{
			BufferedReader bReader = new BufferedReader(new InputStreamReader(fragShader));
			{
				// Cycle thru to get source
				String currentline;
				while ((currentline = bReader.readLine()) != null)
				{
					fragSource += currentline + "\n";
				}
			}
			bReader.close();
		} catch (IOException e) {}
		
		// Compile
		glShaderSource(fShader, fragSource);
		glCompileShader(fShader);
		if (glGetShaderi(fShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			// Throw error
			System.out.println(
					"ERROR: fragment shader miscompiled.\n\n"
							+ glGetShaderInfoLog(fShader, 1024));
			return -1;
		}
		
		// Link shaders
		glAttachShader(id, vShader);
		glAttachShader(id, fShader);
		glLinkProgram(id);
		if (glGetShaderi(fShader, GL_LINK_STATUS) == GL_FALSE)
		{
			// Throw error
			System.out.println(
					"ERROR: linking was a FAILURE.\n\n"
							+ glGetShaderInfoLog(id, 1024));
			return -1;
		}
		
		// Clean up
		glDeleteShader(vShader);
		glDeleteShader(fShader);
		
		// Return program ID
		return id;
	}
}
