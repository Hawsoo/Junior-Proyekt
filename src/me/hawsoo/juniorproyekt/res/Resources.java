package me.hawsoo.juniorproyekt.res;

import java.awt.Point;
import java.io.IOException;
import java.nio.FloatBuffer;

import me.hawsoo.juniorproyekt.util.Camera;
import me.hawsoo.juniorproyekt.util.model.Model;
import me.hawsoo.juniorproyekt.util.model.ObjModel;
import me.hawsoo.juniorproyekt.util.shader.DefaultShader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Huge list of all the required resources.
 * @author Administrator
 *
 */
public class Resources
{
	// Properties
	public static final boolean DEBUG = true;
	
	public static final DisplayMode STANDARD_DM = new DisplayMode(1280, 720);
	public static final DisplayMode FULLSCREEN_DM = Display.getDesktopDisplayMode();
	
	public static Point halfwayPointTransformation = new Point(0, 0);
	
	public static String dirRes = "/me/hawsoo/juniorproyekt/res/";
	
	public static int tileSize = 32;			// 32 by 32
	
	// Objects
	public static Camera mainCamera;
	public static Matrix4f modelViewMatrix;
	public static Matrix4f prevModelViewMatrix;
	
	public static DefaultShader defaultShader;
	
	public static Model contra;
	
	/**
	 * Creates all the resources.
	 */
	public static void init()
	{
		// Setup all resources
		mainCamera = new Camera();
		
		defaultShader = new DefaultShader();
		
		try
		{
			contra = ObjModel.loadModel(Resources.class.getResourceAsStream(dirRes + "model/wt_teapot.obj"));
			contra.shader = defaultShader;
		} catch (IOException e) {}
		
		// Prepare the main model view matrix
		modelViewMatrix = mainCamera.createViewMatrix();
		
		// Trigger initalizing update
		update();
	}
	
	/**
	 * For certain variables, they
	 * must be updated every frame.
	 */
	public static void update()
	{
		// Setup model view matrices
		prevModelViewMatrix = modelViewMatrix;
		modelViewMatrix = mainCamera.createViewMatrix();
	}
	
	/**
	 * Creates a buffer for you.
	 * @param values - buffer's values
	 * @return the created float buffer
	 */
	public static FloatBuffer asFloatBuffer(float[] values)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
}
