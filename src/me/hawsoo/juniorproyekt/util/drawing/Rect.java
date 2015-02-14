package me.hawsoo.juniorproyekt.util.drawing;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.awt.Rectangle;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * This class prepares and draws rectangles
 * in OpenGL space for shorthand debug drawing.
 * @author Administrator
 *
 */
public class Rect
{
	// VBO data
	private static final int VERTICES = 18;				// (two triangles which have 3 vertices each with 3 floats in each vertex, so 2 * 3 * 3 = 18)
	private int vboVertexHandle;
	
	// Original data
	public int width;
	public int height;
	
	/**
	 * Prepares a rectangle VBO.
	 * @param width
	 * @param height
	 */
	public Rect(int width, int height)
	{
		// Store data
		this.width = width;
		this.height = height;
		
		// Generate handle
		vboVertexHandle = glGenBuffers();
		
		// Reserve buffers for storing values
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(VERTICES);
		
		// Add first triangle
		verticesBuffer.put(new float[] {0, 		0, 		0});
		verticesBuffer.put(new float[] {width, 	0, 		0});
		verticesBuffer.put(new float[] {0, 		height, 0});
		
		// Add second triangle
		verticesBuffer.put(new float[] {width, 	height, 0});
		verticesBuffer.put(new float[] {0, 		height, 0});
		verticesBuffer.put(new float[] {width, 	0, 		0});
		
		// Finish VBO
		verticesBuffer.flip();
		
		// Prepare resource drawing system
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		
		// Unbind
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Draws a rectangle in OpenGL.
	 * @param x
	 * @param y
	 * @param isOutline - if the renctangle is an outline.
	 */
	public void drawRect(int x, int y, boolean isOutline)
	{
		// Disable lighting
		glDisable(GL_LIGHTING);
		
		if (isOutline)
		{
			// Enable wireframe
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		}
		
		// Disable shader
		glUseProgram(0);
		
		// Push on offset
		glTranslatef(x, y, 0);
		{
			// Setup VBO
			glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
			glVertexPointer(3, GL_FLOAT, 0, 0l);
			
			// Setup VBO drawing
			glEnableClientState(GL_VERTEX_ARRAY);
			{
				// Draw the VBO
				glDrawArrays(GL_TRIANGLES, 0, VERTICES);
			}
			// Tear down VBO drawing
			glDisableClientState(GL_VERTEX_ARRAY);
		}
		// Take off offset
		glTranslatef(-x, -y, 0);
		
		// Unbind
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		if (isOutline)
		{
			// Disable wireframe
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}
		
		// Enable lighting
		glEnable(GL_LIGHTING);
	}
	
	/**
	 * Gets an AWT version of a <code>Rect</code>.
	 * @param x
	 * @param y
	 * @return the AWT rectangle
	 */
	public Rectangle getAWTrect(int x, int y)
	{
		return new Rectangle(x, y, width, height);
	}
}
