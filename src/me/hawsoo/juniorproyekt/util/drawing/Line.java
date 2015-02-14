package me.hawsoo.juniorproyekt.util.drawing;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.awt.geom.Line2D;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * This class prepares and draws lines
 * in OpenGL space for shorthand debug drawing.
 * @author Administrator
 *
 */
public class Line
{
	// VBO data
	private static final int VERTICES = 6;				// (two points which have 1 vertex each with 3 floats in each vertex, so 2 * 1 * 3 = 6)
	private int vboVertexHandle;
	
	// Original data
	public int width;
	public int height;
	
	/**
	 * Prepares a line VBO.
	 * @param width
	 * @param height
	 */
	public Line(int width, int height)
	{
		// Store data
		this.width = width;
		this.height = height;
		
		// Generate handle
		vboVertexHandle = glGenBuffers();
		
		// Reserve buffers for storing values
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(VERTICES);
		
		// Add two vertices
		verticesBuffer.put(new float[] {0, 	0, 	0});
		verticesBuffer.put(new float[] {width, height, 0});
		
		// Finish VBO
		verticesBuffer.flip();
		
		// Prepare resource drawing system
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		
		// Unbind
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Draws a line in OpenGL.
	 * @param x
	 * @param y
	 * @param isOutline - if the renctangle is an outline.
	 */
	public void drawLine(int x, int y)
	{
		// Disable lighting
		glDisable(GL_LIGHTING);
		
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
				glDrawArrays(GL_LINES, 0, VERTICES);
			}
			// Tear down VBO drawing
			glDisableClientState(GL_VERTEX_ARRAY);
		}
		// Take off offset
		glTranslatef(-x, -y, 0);
		
		// Unbind
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		// Enable lighting
		glEnable(GL_LIGHTING);
	}
	
	/**
	 * Gets a Line2D version of a <code>Line</code>.
	 * @param x
	 * @param y
	 * @return the Line2D
	 */
	public Line2D getLine2D(int x, int y)
	{
		return new Line2D.Float(x, y, x + width, y + height);
	}
}
