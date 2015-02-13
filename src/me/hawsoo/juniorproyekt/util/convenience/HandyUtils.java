package me.hawsoo.juniorproyekt.util.convenience;

import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColorMaterial;

/**
 * This is a set of convenience methods
 * that help... yeah, because I'm too
 * lazy.
 * @author Administrator
 *
 */
public class HandyUtils
{
	/**
	 * This will set the oclor and material for
	 * shaders automatically. All you have to 
	 * do is add the r, g, and b components.
	 * @param r - red
	 * @param g - green
	 * @param b - bloo!
	 */
	public static void setColorRGB(int r, int g, int b)
	{
		glColor3f(r / 255.0f, g / 255.0f, b / 255.0f);
		glColorMaterial(GL_FRONT, GL_DIFFUSE);
	}
}
