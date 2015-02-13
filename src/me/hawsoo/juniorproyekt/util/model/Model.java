package me.hawsoo.juniorproyekt.util.model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import me.hawsoo.juniorproyekt.util.convenience.HandyUtils;
import me.hawsoo.juniorproyekt.util.shader.Shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

/**
 * Holds properties of a model.
 * @author Administrator
 *
 */
public class Model
{
	public List<Vector3f> vertices = new ArrayList<Vector3f>();
	public List<Vector3f> normals = new ArrayList<Vector3f>();
	public List<Face> faces = new ArrayList<Face>();
	
	public int vboVertexHandle;
	public int vboNormalHandle;
	
	public boolean includeVerts = false;
	public boolean includeNorms = false;
	
	// Shader components
	public Shader shader;
	
	/**
	 * Prepares the model, adding the components
	 * into VBO's.
	 */
	public void prepare()
	{
		// Generate handle ID's
		vboVertexHandle = glGenBuffers();
		vboNormalHandle = glGenBuffers();
		
		// Reserve buffers for storing values					(1 triangle that has 3 vertices with 3 floats in each vertex, so 3 x 3 = 9)
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(faces.size() * 9);
		FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(faces.size() * 9);
		
		// Store values into buffers
		for (Face face : faces)
		{
			// Load a vertex
			verticesBuffer.put(asFloats(vertices.get((int)face.vertex.x - 1)));
			verticesBuffer.put(asFloats(vertices.get((int)face.vertex.y - 1)));
			verticesBuffer.put(asFloats(vertices.get((int)face.vertex.z - 1)));
			
			// Load a normal
			normalsBuffer.put(asFloats(normals.get((int)face.normal.x - 1)));
			normalsBuffer.put(asFloats(normals.get((int)face.normal.y - 1)));
			normalsBuffer.put(asFloats(normals.get((int)face.normal.z - 1)));
		}
		
		// Finish VBO's
		verticesBuffer.flip();
		normalsBuffer.flip();
		
		// Bind VBO's
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
		glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
		
		// Unbind
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	
	/**
	 * Destroys the VBO's, thus tearing down the
	 * model.
	 */
	public void destroy()
	{
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboNormalHandle);
	}
	
	/**
	 * Converts a 3-dimensional vector into a float array.
	 * @param vector - the 3D vector
	 * @return a float array version of the vector
	 */
	private static float[] asFloats(Vector3f vector)
	{
		return new float[] {vector.x, vector.y, vector.z};
	}
	
	/**
	 * Renders the model.
	 */
	public void render()
	{
		// Setup shader
		shader.useShader();
		
		// Setup VBO's
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glVertexPointer(3, GL_FLOAT, 0, 0l);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
		glNormalPointer(GL_FLOAT, 0, 0l);
		
		// Setup VBO drawing
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		{
			// Setup drawing
			//glColor3f(0.17f, 0.58f, 0.12f);		// This is choco color
			//HandyUtils.setColorRGB(162, 162, 163);
			HandyUtils.setColorRGB(204, 37, 79);
//			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
//			glMaterialf(GL_FRONT, GL_SHININESS, 5);
			glMaterialf(GL_FRONT, GL_SHININESS, 10);
//			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			
			// Draw vertices
			glDrawArrays(GL_TRIANGLES, 0, faces.size() * 3);
			
			// Reset shader
			glUseProgram(0);
			
			// Setup drawing (outline)
			glColor3f(0, 0, 0);
			glPolygonMode(GL_BACK, GL_LINE);
			glLineWidth(4);
			glCullFace(GL_FRONT);
			glDepthFunc(GL_LEQUAL);
			{
				// Draw vertices
//				glDrawArrays(GL_TRIANGLES, 0, faces.size() * 3);
			}
			// Reset drawing
			glPolygonMode(GL_BACK, GL_FILL);
			glLineWidth(1);
			glCullFace(GL_BACK);
			glDepthFunc(GL_LESS);
		}
		// Tear down VBO drawing
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		
		// Unbind
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
}
